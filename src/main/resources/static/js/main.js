/* Main Application JavaScript */

document.addEventListener('DOMContentLoaded', () => {
    initTheme();
    initLiveSearch();
    initWishlistButtons();
    initAddToCartButtons();
    initQuantityAdjusters();
});

// 1. Theme Switcher Logic
function initTheme() {
    const themeToggleBtn = document.getElementById('themeToggleBtn');
    const themeIcon = document.getElementById('themeIcon');
    const storedTheme = localStorage.getItem('theme') || 'dark';

    document.documentElement.setAttribute('data-theme', storedTheme);
    updateThemeIcon(storedTheme);

    if (themeToggleBtn) {
        themeToggleBtn.addEventListener('click', () => {
            const currentTheme = document.documentElement.getAttribute('data-theme');
            const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
            document.documentElement.setAttribute('data-theme', newTheme);
            localStorage.setItem('theme', newTheme);
            updateThemeIcon(newTheme);
        });
    }
}

function updateThemeIcon(theme) {
    const themeIcon = document.getElementById('themeIcon');
    if (themeIcon) {
        themeIcon.className = theme === 'dark' ? 'fas fa-sun text-warning' : 'fas fa-moon text-primary';
    }
}

// 2. Instant Live Search Autocomplete
function initLiveSearch() {
    const searchInput = document.getElementById('searchInput');
    const suggestionsBox = document.getElementById('searchSuggestionsBox');

    if (!searchInput || !suggestionsBox) return;

    let debounceTimer;
    searchInput.addEventListener('input', (e) => {
        const query = e.target.value.trim();
        clearTimeout(debounceTimer);

        if (query.length < 2) {
            suggestionsBox.style.display = 'none';
            suggestionsBox.innerHTML = '';
            return;
        }

        debounceTimer = setTimeout(() => {
            fetch(`/suggestions?q=${encodeURIComponent(query)}`)
                .then(res => res.json())
                .then(data => {
                    if (!data || data.length === 0) {
                        suggestionsBox.innerHTML = '<div class="p-3 text-muted text-center">No products found</div>';
                    } else {
                        suggestionsBox.innerHTML = data.map(item => `
                            <a href="/products/${item.id}" class="search-suggestion-item">
                                <img src="${item.image}" alt="${item.productName}" />
                                <div>
                                    <div class="fw-semibold text-truncate" style="max-width: 250px;">${item.productName}</div>
                                    <small class="text-primary">$${item.price.toFixed(2)}</small>
                                </div>
                            </a>
                        `).join('');
                    }
                    suggestionsBox.style.display = 'block';
                })
                .catch(err => console.error('Error fetching search suggestions:', err));
        }, 250);
    });

    document.addEventListener('click', (e) => {
        if (!searchInput.contains(e.target) && !suggestionsBox.contains(e.target)) {
            suggestionsBox.style.display = 'none';
        }
    });
}

// 3. Wishlist Button Ajax Toggle
function initWishlistButtons() {
    document.querySelectorAll('.btn-wishlist-toggle').forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const productId = btn.getAttribute('data-product-id');
            const token = getCsrfToken();
            const header = getCsrfHeader();

            fetch(`/wishlist/toggle-ajax/${productId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [header]: token
                }
            })
            .then(res => {
                if (res.status === 401) {
                    window.location.href = '/login';
                    return;
                }
                return res.json();
            })
            .then(data => {
                if (data) {
                    const icon = btn.querySelector('i');
                    if (data.added) {
                        icon.className = 'fas fa-heart text-danger';
                        showToast('Wishlist', data.message, 'success');
                    } else {
                        icon.className = 'far fa-heart';
                        showToast('Wishlist', data.message, 'info');
                    }
                }
            })
            .catch(err => console.error('Error toggling wishlist:', err));
        });
    });
}

// 4. Add to Cart Ajax
function initAddToCartButtons() {
    document.querySelectorAll('.btn-add-cart-ajax').forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const productId = btn.getAttribute('data-product-id');
            const qtyInput = document.getElementById(`qty-${productId}`) || { value: 1 };
            const quantity = parseInt(qtyInput.value) || 1;
            const token = getCsrfToken();
            const header = getCsrfHeader();

            fetch(`/cart/add-ajax?productId=${productId}&quantity=${quantity}`, {
                method: 'POST',
                headers: {
                    [header]: token
                }
            })
            .then(res => {
                if (res.status === 401) {
                    window.location.href = '/login';
                    return;
                }
                return res.json();
            })
            .then(data => {
                if (data) {
                    showToast('Cart', data.message, 'success');
                    const cartBadge = document.getElementById('cartBadgeCount');
                    if (cartBadge) {
                        cartBadge.innerText = data.totalItems;
                    }
                }
            })
            .catch(err => console.error('Error adding to cart:', err));
        });
    });
}

// 5. Quantity Plus/Minus Adjusters
function initQuantityAdjusters() {
    document.querySelectorAll('.qty-btn-minus').forEach(btn => {
        btn.addEventListener('click', () => {
            const targetId = btn.getAttribute('data-target');
            const input = document.getElementById(targetId);
            if (input && parseInt(input.value) > 1) {
                input.value = parseInt(input.value) - 1;
            }
        });
    });

    document.querySelectorAll('.qty-btn-plus').forEach(btn => {
        btn.addEventListener('click', () => {
            const targetId = btn.getAttribute('data-target');
            const input = document.getElementById(targetId);
            if (input) {
                input.value = parseInt(input.value) + 1;
            }
        });
    });
}

// Helper: Toast Notifications
function showToast(title, message, type = 'info') {
    let container = document.getElementById('toastContainer');
    if (!container) {
        container = document.createElement('div');
        container.id = 'toastContainer';
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toastId = 'toast-' + Date.now();
    const bgClass = type === 'success' ? 'bg-success' : type === 'danger' ? 'bg-danger' : 'bg-primary';

    const toastHtml = `
        <div id="${toastId}" class="toast align-items-center text-white ${bgClass} border-0 show" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="d-flex">
                <div class="toast-body">
                    <strong>${title}:</strong> ${message}
                </div>
                <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
        </div>
    `;

    container.insertAdjacentHTML('beforeend', toastHtml);
    setTimeout(() => {
        const el = document.getElementById(toastId);
        if (el) el.remove();
    }, 4000);
}

// CSRF Token Helpers
function getCsrfToken() {
    const meta = document.querySelector('meta[name="_csrf"]');
    return meta ? meta.getAttribute('content') : '';
}

function getCsrfHeader() {
    const meta = document.querySelector('meta[name="_csrf_header"]');
    return meta ? meta.getAttribute('content') : 'X-CSRF-TOKEN';
}
