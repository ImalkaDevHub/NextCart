package com.ecommerce.app.config;

import com.ecommerce.app.entity.*;
import com.ecommerce.app.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReviewRepository reviewRepository;

    public DataInitializer(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository, PasswordEncoder passwordEncoder, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("Initializing Seed Users...");

            User admin = User.builder()
                    .fullName("System Admin")
                    .email("admin@ecommerce.com")
                    .password(passwordEncoder.encode("admin123"))
                    .phone("+1 800 555 0199")
                    .address("100 Corporate Parkway, Tech City, USA")
                    .role(Role.ROLE_ADMIN)
                    .enabled(true)
                    .build();
            userRepository.save(admin);

            User customer = User.builder()
                    .fullName("John Doe")
                    .email("john@example.com")
                    .password(passwordEncoder.encode("user123"))
                    .phone("+1 555 014 8822")
                    .address("742 Evergreen Terrace, Springfield, USA")
                    .role(Role.ROLE_CUSTOMER)
                    .enabled(true)
                    .build();
            userRepository.save(customer);

            log.info("Default Admin: admin@ecommerce.com / admin123");
            log.info("Default Customer: john@example.com / user123");
        }

        if (categoryRepository.count() == 0) {
            log.info("Initializing Seed Categories & Products...");

            Category electronics = Category.builder()
                    .categoryName("Electronics")
                    .description("Latest gadgets, smartphones, laptops, and audio gear.")
                    .imageUrl("https://images.unsplash.com/photo-1498049860654-af1a5c566876?w=600&auto=format&fit=crop&q=80")
                    .build();
            categoryRepository.save(electronics);

            Category fashion = Category.builder()
                    .categoryName("Fashion & Apparel")
                    .description("Trendy footwear, jackets, watches, and accessories.")
                    .imageUrl("https://images.unsplash.com/photo-1445205170230-053b83016050?w=600&auto=format&fit=crop&q=80")
                    .build();
            categoryRepository.save(fashion);

            Category homeKitchen = Category.builder()
                    .categoryName("Home & Kitchen")
                    .description("Modern home decor, espresso machines, and cookware.")
                    .imageUrl("https://images.unsplash.com/photo-1556911220-e15b29be8c8f?w=600&auto=format&fit=crop&q=80")
                    .build();
            categoryRepository.save(homeKitchen);

            Category sports = Category.builder()
                    .categoryName("Sports & Outdoors")
                    .description("Fitness gear, bicycles, smart watches, and outdoor equipment.")
                    .imageUrl("https://images.unsplash.com/photo-1517649763962-0c623266010b?w=600&auto=format&fit=crop&q=80")
                    .build();
            categoryRepository.save(sports);

            // Products
            Product p1 = Product.builder()
                    .productName("Wireless Noise-Canceling Headphones")
                    .description("Experience studio-quality sound with adaptive active noise cancellation, 40-hour battery life, and ultra-soft memory foam earcups.")
                    .brand("Sony")
                    .price(new BigDecimal("299.99"))
                    .stock(25)
                    .image("https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop&q=80")
                    .specifications("Bluetooth 5.2, Active Noise Cancelling, USB-C Fast Charge, 40hr Battery")
                    .isFeatured(true)
                    .category(electronics)
                    .build();
            productRepository.save(p1);

            Product p2 = Product.builder()
                    .productName("Ultra Slim Flagship Smartphone 5G")
                    .description("Features a breathtaking 120Hz Dynamic AMOLED display, pro-grade 108MP camera array, and all-day intelligent battery.")
                    .brand("Samsung")
                    .price(new BigDecimal("999.00"))
                    .stock(15)
                    .image("https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=600&auto=format&fit=crop&q=80")
                    .specifications("6.7-inch 120Hz AMOLED, 256GB Storage, 12GB RAM, 5G Dual SIM")
                    .isFeatured(true)
                    .category(electronics)
                    .build();
            productRepository.save(p2);

            Product p3 = Product.builder()
                    .productName("Minimalist Automatic Mechanical Watch")
                    .description("Crafted with premium sapphire crystal, genuine leather strap, and Japanese automatic movement visible through an exhibition case back.")
                    .brand("Seiko")
                    .price(new BigDecimal("249.50"))
                    .stock(10)
                    .image("https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop&q=80")
                    .specifications("316L Stainless Steel, 50m Water Resistance, Sapphire Crystal, Leather Strap")
                    .isFeatured(true)
                    .category(fashion)
                    .build();
            productRepository.save(p3);

            Product p4 = Product.builder()
                    .productName("Classic Denim Jacket")
                    .description("Timeless vintage wash denim jacket made from 100% organic cotton. Reinforced stitching and relaxed fit.")
                    .brand("Levi's")
                    .price(new BigDecimal("89.99"))
                    .stock(40)
                    .image("https://images.unsplash.com/photo-1576995853123-5a10305d93c0?w=600&auto=format&fit=crop&q=80")
                    .specifications("100% Organic Cotton, Heavyweight Denim, Dual Chest Pockets")
                    .isFeatured(false)
                    .category(fashion)
                    .build();
            productRepository.save(p4);

            Product p5 = Product.builder()
                    .productName("Barista Express Espresso Machine")
                    .description("Create third wave specialty coffee at home with integrated precision grinder and commercial steam wand.")
                    .brand("Breville")
                    .price(new BigDecimal("699.95"))
                    .stock(8)
                    .image("https://images.unsplash.com/photo-1517668808822-9ebe02f2a6ee?w=600&auto=format&fit=crop&q=80")
                    .specifications("15-Bar Italian Pump, Integrated Conical Burr Grinder, Microfoam Milk Texturing")
                    .isFeatured(true)
                    .category(homeKitchen)
                    .build();
            productRepository.save(p5);

            Product p6 = Product.builder()
                    .productName("Smart Fitness Watch & Tracker")
                    .description("Track your workouts, heart rate, sleep cycles, and SPO2 levels with built-in GPS and 14-day battery power.")
                    .brand("Garmin")
                    .price(new BigDecimal("179.99"))
                    .stock(3)
                    .image("https://images.unsplash.com/photo-1575311373937-040b8e1fd5b6?w=600&auto=format&fit=crop&q=80")
                    .specifications("Heart Rate & SpO2 Monitor, 50m Waterproof, Built-in GPS, 14-Day Battery")
                    .isFeatured(false)
                    .category(sports)
                    .build();
            productRepository.save(p6);

            User customerUser = userRepository.findByEmail("john@example.com").orElse(null);
            if (customerUser != null) {
                Review r1 = Review.builder()
                        .product(p1)
                        .user(customerUser)
                        .rating(5)
                        .comment("Absolutly astounding sound quality! Active noise canceling works like a charm on flights.")
                        .build();
                reviewRepository.save(r1);
            }

            log.info("Successfully populated seed products and categories.");
        }
    }
}
