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
<img width="100%" alt="user dashboard" src="https://github.com/user-attachments/assets/fc29a3b3-d578-4544-96f5-dedbe099f440" />

### Movie Dashboard page 2 (Admin View)
<img width="100%" alt="admin dasboard" src="https://github.com/user-attachments/assets/7e8172ac-ced2-458c-96f8-ba9f4ab086e0" />

### Sign Up Page
<img width="100%" alt="Sign up pg" src="https://github.com/user-attachments/assets/9e078fe9-665f-4c35-aedd-04ffdc8c4fae" />

## ⚙️ How to Run Locally

1. **Clone the repository**
   ```bash
   git clone [https://github.com/siddhanth-dev/MovieLibrary.git](https://github.com/siddhanth-dev/MovieLibrary.git)

2. **Configure Database Update** src/main/resources/application.properties with your MySQL credentials.

3. **Run the App**
    ~Bash
    mvn spring-boot:run

4. **Access the App Open** http://localhost:8080 in your browser.

5. 🔑 **Default Admin Credentials**
The system automatically creates a Super Admin on first launch if one does not exist.

Username: admin
Password: admin123 
