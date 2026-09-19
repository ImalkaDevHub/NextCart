# ApexCart - Spring Boot 3 Enterprise E-Commerce Platform

The project was developed to gain practical experience in Java application development, database management, object-oriented programming, and software engineering concepts. **Java 21**, **Spring Boot 3.2**, **Spring Security 6**, **Spring Data JPA**, **Thymeleaf**, **Bootstrap 5**, and **MySQL/H2**.

---

## ðŸŒŸ Key Features

### Customer Portal
- **User Authentication & Security**: Secure registration, login, session management, BCrypt password hashing, and role-based access control.
- **Product Catalog & Advanced Search**: Filter products by Category, Brand, Price Range, and Keyword with instant auto-suggestion search powered by JavaScript.
- **Product Details & Ratings**: View high-resolution imagery, specifications, stock availability, and user reviews.
- **Interactive Shopping Cart**: Dynamic cart management, auto-calculation of subtotal, 8% tax rate, and delivery fees with inventory validation.
- **Wishlist Management**: Save items for later with one-click AJAX wishlist toggling.
- **Checkout & Simulated Payments**: Supports Cash on Delivery (COD), Credit Card Simulation, and instant UPI Payment simulation with error simulation testing.
- **Order Management & Invoices**: Order history, order tracking, order cancellation options, and printable/saveable PDF HTML invoices.
- **Modern UI/UX**: Dark/Light mode theme switcher, glassmorphic design system, smooth micro-animations, toast alerts, and mobile responsiveness.

### Admin Executive Panel
- **Analytics Dashboard**: Real-time KPI summary cards (Total Revenue, Orders, Products, Customers) and interactive **Chart.js** revenue & category distribution charts.
- **Category Management**: Full CRUD operations for categories with image URLs.
- **Product Management**: Full CRUD operations for products with image file upload capability and low-stock threshold badges.
- **Order Processing**: Live order list, order details, and real-time order status updates (`PENDING` -> `PROCESSING` -> `SHIPPED` -> `DELIVERED` / `CANCELLED`).
- **Customer Account Management**: Enable/Disable customer accounts with real-time status toggles.
- **Data Export**: Export complete product inventory to **Apache POI Excel** files (`.xlsx`).

---

## ðŸ› ï¸ Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Backend Framework** | Java 21 LTS, Spring Boot 3.2.4, Spring MVC, Spring Security 6 |
| **Persistence** | Spring Data JPA, Hibernate, MySQL, H2 In-Memory DB |
| **Frontend** | Thymeleaf, HTML5, Vanilla CSS3 (Glassmorphism), JavaScript (ES6+), Bootstrap 5.3, FontAwesome 6 |
| **Build Tool & Utilities** | Apache Maven, Lombok, Apache POI (Excel Export) |

---

## ðŸ”‘ Demo Credentials

| Role | Email | Password | Access Level |
| :--- | :--- | :--- | :--- |
| **Customer** | `john@example.com` | `user123` | Shopping, Wishlist, Checkout, Orders |
| **Admin** | `admin@ecommerce.com` | `admin123` | Executive Dashboard, Catalog CRUD, Reports |

---

## ðŸ“ Project Architecture & Directory Structure

```
d:/180_Days/Projects/E-commers_App/
â”œâ”€â”€ pom.xml
â”œâ”€â”€ schema.sql
â”œâ”€â”€ README.md
â””â”€â”€ src/
    â””â”€â”€ main/
        â”œâ”€â”€ java/com/ecommerce/app/
        â”‚   â”œâ”€â”€ config/              # DataInitializer & App Configurations
        â”‚   â”œâ”€â”€ controller/          # Spring MVC Web Controllers (Public & Admin)
        â”‚   â”œâ”€â”€ dto/                 # Data Transfer Objects & Forms
        â”‚   â”œâ”€â”€ entity/              # JPA Domain Entities & Enums
        â”‚   â”œâ”€â”€ exception/           # Global Exception Handler & Custom Errors
        â”‚   â”œâ”€â”€ repository/          # Spring Data JPA Repositories
        â”‚   â”œâ”€â”€ security/            # Spring Security Filter Chain & Handlers
        â”‚   â”œâ”€â”€ service/             # Business Logic Interfaces
        â”‚   â”‚   â””â”€â”€ impl/            # Business Logic Implementations
        â”‚   â””â”€â”€ util/                # File Upload & Utility Classes
        â””â”€â”€ resources/
            â”œâ”€â”€ application.properties
            â”œâ”€â”€ static/
            â”‚   â”œâ”€â”€ css/style.css    # Glassmorphism & Theme Variables
            â”‚   â””â”€â”€ js/main.js       # Live Search, Theme Switcher & AJAX
            â””â”€â”€ templates/
                â”œâ”€â”€ admin/           # Admin Dashboard & Management Templates
                â”œâ”€â”€ auth/            # Login, Register, Forgot Password
                â”œâ”€â”€ cart/            # Shopping Cart Templates
                â”œâ”€â”€ checkout/        # Checkout & Payment Simulation
                â”œâ”€â”€ error/           # Custom 404, 403, 500 Error Pages
                â”œâ”€â”€ fragments/       # Navbar, Footer, Alerts
                â”œâ”€â”€ pages/           # About, Contact, FAQ, Terms
                â”œâ”€â”€ product/         # Catalog List & Detail Pages
                â””â”€â”€ user/            # User Profile, Orders, Wishlist, Invoice
```

---

## ðŸš€ Installation & Setup Instructions

### Prerequisites
- **Java 21 JDK** installed and configured in system `PATH`.
- **Apache Maven 3.8+** (or use bundled Maven).
- Optional: **MySQL 8.0+** (H2 is configured by default for zero-setup execution).

### Step 1: Clone Repository
```bash
git clone https://github.com/Madhan-213/NextCart.git
cd NextCart
```

### Step 2: Running with Default H2 Database
No database installation is required! Run the application directly:
```bash
mvn spring-boot:run
```
Open your browser and navigate to: `http://localhost:8080`

### Step 3: (Optional) Switching to MySQL Database
To use a production MySQL server:
1. Create a MySQL database:
   ```sql
   CREATE DATABASE ecommerce_db;
   ```
2. Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&serverTimezone=UTC
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
   ```

---

## ðŸ§ª Verification & Testing

- Access H2 Console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:ecommerce_db`, Username: `sa`, Password: leave empty).
- Test placing orders with simulated COD and instant Credit Card/UPI options.
- Access the Admin Panel using `admin@ecommerce.com` / `admin123` to test Product CRUD, Category CRUD, and Excel Export downloads.

---

## ðŸ“„ License
This project is licensed under the MIT License - feel free to use it for portfolio evaluations, placement showcases, and learning!
