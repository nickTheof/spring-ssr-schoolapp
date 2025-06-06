# 🏫 School Management App

## 📋 Description

The **School Management App** is a web-based application designed to manage core school resources.  
This version currently focuses on **Teacher and Student Management**.

---

## ⚙️ Tech Stack

- **Java Spring Boot**
- **Java Spring Security**
- **Thymeleaf (Server-Side Rendering)**
- **JPA - Hibernate ORM**
- **Gradle** for dependency management
- **Tailwind CSS** for styling
- **CSRF Protection** via Spring Security
- **Reusable Thymeleaf Fragments**
- **Global Error Controller**
- **Mobile-First Responsive Design**

---

## 🧱 Architecture

The project follows the **Model-View-Controller (MVC)** architecture, enhanced with **Service-Oriented Design** and **Clean Architecture principles**.

- **Controllers:** Java Spring MVC controllers handle routing and business logic.
- **Views:** Server-rendered using Thymeleaf templates.
- **Services:** Encapsulate business logic and interact with repositories.
- **Repositories:** Interface with the database using JPA/Hibernate.

---

## 👤 Roles & Permissions

The system includes **Session-Based Authentication** and **Role-Based Authorization**:

| Role   | Description                                                        |
|--------|--------------------------------------------------------------------|
| `ADMIN`  | Full CRUD on Teachers, Students, Users & Regions (via Admin Panel) |
| `EDITOR` | CRUD on Teachers, Students                                         |
| `READER` | Read-only access to Teachers, Students                             |

---

## 🧩 Features

- ✅ Teacher Management (Insert, View, Update, Delete)
- ✅ Student Management (Insert, View, Update, Delete)
- ✅ User Management (Admin only)
- ✅ Region Management (Admin only)
- ✅ Authentication & Authorization (Session + Role-Based)
- ✅ CSRF Protection for all forms
- ✅ Global Error Handling (404, 500, etc.)
- ✅ Responsive UI built with Tailwind CSS
- ✅ Modular UI with reusable Thymeleaf fragments

---

## 🚀 Getting Started 

> Requires JDK 17+, Gradle, and a MySQL Database

1. Clone the repository:
```bash
git clone git@github.com:nickTheof/spring-ssr-schoolapp.git
cd spring-ssr-schoolapp
```

2. Set up the database in `application.yml` or `application.properties`.

3. Run the application:
```bash
./gradlew bootRun
```

---

## 📁 Folder Structure (Brief)
```
src/
├── main/
│   ├── java/gr/aueb/cf/schoolspring/
│   │   ├── authentication/
│   │   ├── controller/
│   │   ├── core/
│   │   ├── dto/
│   │   ├── mapper/
│   │   ├── service/
│   │   ├── repository/
│   │   └── model/
│   └── resources/
│       ├── sql/
│       ├── templates/
│       └── static/
```

---

## 📸 Screenshots


---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](licence.txt) file for details.