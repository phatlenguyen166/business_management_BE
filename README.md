# 📊 Business Management System – Backend (Spring Boot)

This is the backend source code for a full-featured business management system developed using Java Spring Boot. The application helps companies manage internal business activities including employee records, contracts, product inventory, payroll, import receipts, and invoice generation.

## 🚀 Features

- Authentication & Authorization using Spring Security and JWT
- CRUD APIs for:
  - Employees
  - Contracts
  - Products
  - Payroll
  - Import Receipts
  - Invoices
- Image upload with Cloudinary
- Email service with SendGrid (used in Forgot Password)
- Role-based access control (Admin, HR, Employee)

## 🧰 Technologies Used

- **Backend:** Java Spring Boot
- **Security:** Spring Security, JWT
- **Database:** MySQL
- **Cloud Service:** Cloudinary
- **Email Service:** SendGrid
- **API Style:** RESTful APIs
- **Others:** Maven, Git

## 🏗️ Project Structure

src/main/java/vn/bookstore/app/
├── config/ # Configuration classes
├── controller/ # REST Controllers
├── dto/ # Request/Response objects
├── mapper/ # Object mappers
├── model/ # JPA Entities
├── repository/ # Spring Data JPA Repositories
├── service/ # Business Logic
└── util/ # Utility classes

## 🔌 API Endpoints

### Authentication

- `POST /api/auth/login` - Login and get JWT token
- `POST /api/auth/register` - Register new user
- `POST /api/auth/forgot-password` - Request password reset

### Employees

- `GET /api/employees` - Get all employees
- `GET /api/employees/{id}` - Get employee by ID
- `POST /api/employees` - Create new employee
- `PUT /api/employees/{id}` - Update employee
- `DELETE /api/employees/{id}` - Delete employee

### Contracts

- `GET /api/contracts` - Get all contracts
- `GET /api/contracts/{id}` - Get contract by ID
- `POST /api/contracts` - Create new contract
- `PUT /api/contracts/{id}` - Update contract
- `DELETE /api/contracts/{id}` - Delete contract

### Payroll

- `GET /api/payrolls` - Get all payroll entries
- `GET /api/payrolls/{id}` - Get payroll by ID
- `POST /api/payrolls` - Create payroll entry
- `GET /api/payrolls/export/{id}` - Export payroll as PDF

## ▶️ Getting Started

### Prerequisites

- Java 17+
- Maven
- MySQL
- Cloudinary Account
- SendGrid API Key

### Setup

1. Clone this repository:

```bash
git clone https://github.com/phatlenguyen166/business_management_BE.git
cd business_management_BE
```

2.Configure `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=your_username
spring.datasource.password=your_password

jwt.secretKey=your_secret_key
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret

spring.sendgrid.apiKey=your_sendgrid_api_key
```

3.Run the project:

```bash
./mvnw spring-boot:run
```

## 🧪 Testing

Run the tests:

```bash
./mvnw test
```

## 🤝 How to Contribute

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 👨‍💻 Frontend

Frontend repo (ReactJS + Tailwind CSS):  
➡️ [https://github.com/trghieu9415/bis-g11-fe](https://github.com/trghieu9415/bis-g11-fe)

## 👥 Contributors

- [@phatlenguyen166](https://github.com/phatlenguyen166) – Backend Developer
- [@trghieu9415](https://github.com/trghieu9415) – Frontend Developer

---

📄 This project is for educational and internship purposes only.
