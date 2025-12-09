# 🎬 LibreMovies - Movie Library Management System

A full-stack web application built with Java Spring Boot to manage personal movie collections. Unlike a shared public list, this application functions as a **Private Movie Diary**, allowing every user to maintain their own isolated library.

## 🚀 Features
- **🔐 Secure Authentication:** Login and Signup with BCrypt password hashing.
- **👤 Personal Libraries:** Every user has their own private database of movies. One user cannot see or edit another user's list.
- **🔎 Search Functionality:** Instantly filter movies by title.
- **📄 Pagination:** Efficiently handles large collections by splitting them into pages.
- **🎨 Responsive UI:** A **Dark Mode** interface featuring Glassmorphism and responsive design.
- **🛡️ Admin Dashboard:** A special "Super Admin" role that can:
  - View all registered users.
  - Delete users (and their data).
  - Moderate content by viewing user libraries.

## 🛠️ Tech Stack
- **Backend:** Java 17, Spring Boot 3, Spring Security, Spring Data JPA
- **Frontend:** Thymeleaf, HTML5, CSS3 (Custom)
- **Database:** MySQL (Production) / H2 (Dev)
- **Deployment:** Docker, Render, Aiven Cloud DB

## 📸 Screenshots

### Movie Dashboard page 1
<img width="100%" alt="Dasboard-pg-1" src="https://github.com/user-attachments/assets/a2901d52-e7e8-4d14-a16f-f84b440f3337" />

### Movie Dashboard page 2 (Admin View)
<img width="100%" alt="admin-view" src="https://github.com/user-attachments/assets/d3dbd5da-6194-426b-af9d-6f5fbcaa2144" />

### Sign Up Page
<img width="100%" alt="Signup-pg" src="https://github.com/user-attachments/assets/8b2d4d2e-1445-48f8-b0db-a75d89a50825" />

## ⚙️ How to Run Locally

1. **Clone the repository**
   ```bash
   git clone [https://github.com/siddhanth-dev/MovieLibrary.git](https://github.com/siddhanth-dev/MovieLibrary.git)


