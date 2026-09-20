# NextCart (ApexCart) — Spring Boot 3 Enterprise E-Commerce Platform
 
A full-stack e-commerce web application built with **Java 21** and **Spring Boot 3**, backed by a **MySQL** database. Developed as a second-year university project to gain hands-on experience with Java application development, database management, object-oriented design, and software engineering practices.
 
## 🌟 Key Features
 
### Customer Portal
- **Authentication & Security** — Secure registration/login, session management, BCrypt password hashing, and role-based access control.
- **Product Catalog & Search** — Filter by category, brand, and price range, with instant auto-suggestion search.
- **Product Details & Ratings** — High-resolution images, specifications, stock availability, and user reviews.
- **Shopping Cart** — Dynamic cart management with automatic subtotal, 8% tax, and delivery fee calculation, plus inventory validation.
- **Wishlist** — One-click AJAX wishlist toggling.
- **Checkout & Simulated Payments** — Cash on Delivery (COD), Credit Card simulation, and instant UPI simulation with error-case testing.
- **Order Management & Invoices** — Order history, tracking, cancellation, and printable/saveable PDF-style invoices.
- **Modern UI/UX** — Dark/light theme switcher, glassmorphic design, micro-animations, toast alerts, and mobile responsiveness.
### Admin Panel
- **Analytics Dashboard** — Real-time KPI cards (revenue, orders, products, customers) with Chart.js visualizations.
- **Category & Product Management** — Full CRUD, including image uploads and low-stock badges.
- **Order Processing** — Live order list with status updates (PENDING → PROCESSING → SHIPPED → DELIVERED / CANCELLED).
- **Customer Management** — Enable/disable customer accounts.
- **Data Export** — Export inventory to Excel (.xlsx) via Apache POI.
## 🛠️ Technology Stack
 
| Layer | Technology |
|---|---|
| Backend | Java 21 LTS, Spring Boot 3.2.4, Spring MVC, Spring Security 6 |
| Persistence | Spring Data JPA, Hibernate, **MySQL**, H2 (in-memory, dev) |
| Frontend | Thymeleaf, HTML5, CSS3 (Glassmorphism), JavaScript (ES6+), Bootstrap 5.3, FontAwesome 6 |
| Build & Utilities | Apache Maven, Lombok, Apache POI |
 
## 🔑 Demo Credentials
 
| Role | Email | Password | Access |
|---|---|---|---|
| Customer | john@example.com | user123 | Shopping, wishlist, checkout, orders |
| Admin | admin@ecommerce.com | admin123 | Dashboard, catalog CRUD, reports |
 
## 📁 Project Structure
 
```
NextCart/
├── pom.xml
├── schema.sql
├── README.md
└── src/main/
    ├── java/com/ecommerce/app/
    │   ├── config/       # App configuration & data initializer
    │   ├── controller/   # Web controllers (public & admin)
    │   ├── dto/          # Data transfer objects & forms
    │   ├── entity/       # JPA entities & enums
    │   ├── exception/    # Global exception handling
    │   ├── repository/   # Spring Data JPA repositories
    │   ├── security/     # Spring Security config
    │   ├── service/      # Business logic (+ impl/)
    │   └── util/         # File upload & utility classes
    └── resources/
        ├── application.properties
        ├── static/       # css/, js/
        └── templates/    # admin/, auth/, cart/, checkout/, product/, user/, ...
```
 
## 🚀 Getting Started
 
### Prerequisites
- Java 21 JDK (on system PATH)
- Apache Maven 3.8+
- MySQL 8.0+ (optional — H2 runs out of the box)
### 1. Clone the repository
```bash
git clone https://github.com/Madhan-213/NextCart.git
cd NextCart
```
 
### 2. Run with the default H2 database
No setup required:
```bash
mvn spring-boot:run
```
Then open **http://localhost:8080**
 
### 3. (Optional) Switch to MySQL
Create the database:
```sql
CREATE DATABASE ecommerce_db;
```
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
 
## 🧪 Verification & Testing
- H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:ecommerce_db`, user `sa`, no password)
- Place test orders using COD and the simulated Credit Card / UPI options
- Log in as admin to test Product/Category CRUD and Excel export
## 📄 License
Licensed under the MIT License — free to use for portfolio evaluations, placement showcases, and learning.
 
