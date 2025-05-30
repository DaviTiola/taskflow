[Leia em Português (Brasil)](README_pt-BR.md)

---

# TaskFlow API (English)

A RESTful API for task management, built with Spring Boot, Java, and PostgreSQL. This project allows users to efficiently create, list, view details of, update, and delete tasks.

## Overview

The TaskFlow API is a backend service that provides a set of endpoints to interact with a task management system. It has been developed as a learning and portfolio project, utilizing best practices in development with the Spring ecosystem.

## Key Features

-   [x] Creation of new tasks with description, status, and due date.
-   [x] Listing of all registered tasks.
-   [x] Retrieval of a specific task by its ID.
-   [x] Full update of an existing task's information.
-   [x] Deletion of tasks.
-   [ ] (Future) Input data validation.
-   [ ] (Future) Custom error handling.
-   [ ] (Future) Pagination and sorting for task listings.

## Technologies Used

-   **Java:** 21
-   **Spring Boot:** 3.x.x (Ex: 3.3.0 - *Please check your version in `pom.xml`*)
-   **Spring Web:** For building REST APIs.
-   **Spring Data JPA:** For data persistence.
-   **Hibernate:** JPA implementation.
-   **PostgreSQL:** Relational database (Ex: 16, 17 - *Please check your version*)
-   **Maven:** Dependency management and project build.
-   **Lombok:** To reduce boilerplate code.
-   **Postman (or similar):** For API testing.

## Prerequisites

Before you begin, ensure you have the following software installed on your system:

-   JDK (Java Development Kit) - Version 21 or higher.
-   Maven - Version 3.6 or higher.
-   PostgreSQL - Server installed and running.
-   Your preferred IDE (e.g., IntelliJ IDEA, Eclipse, VS Code).
-   Postman or Insomnia (optional, for testing the API).

## Environment Setup and Installation

Follow the steps below to set up and run the project locally:

1.  **Clone the Repository:**
    ```bash
    git clone <YOUR_GITHUB_REPOSITORY_URL_HERE>
    cd taskflow # Or your project's folder name
    ```

2.  **PostgreSQL Database Setup:**
    * Ensure your PostgreSQL service is running.
    * Create a new database. We used the name `taskflow_db` during development:
        ```sql
        -- Connect to psql as a superuser (e.g., postgres)
        CREATE DATABASE taskflow_db;
        ```
    * Create a new user (role) with a password. We used `taskflow_user` during development:
        ```sql
        CREATE USER taskflow_user WITH PASSWORD '<YOUR_CHOSEN_PASSWORD_HERE>';
        GRANT ALL PRIVILEGES ON DATABASE taskflow_db TO taskflow_user;
        ALTER DATABASE taskflow_db OWNER TO taskflow_user;
        ```
        **Remember the password you set!**

3.  **Application Configuration (`application.properties`):**
    * Navigate to `src/main/resources/application.properties`.
    * Configure the database connection properties according to your PostgreSQL setup:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/taskflow_db
        spring.datasource.username=taskflow_user
        spring.datasource.password=<YOUR_CHOSEN_PASSWORD_HERE>
        spring.datasource.driver-class-name=org.postgresql.Driver

        spring.jpa.hibernate.ddl-auto=update
        spring.jpa.show-sql=true
        spring.jpa.properties.hibernate.format_sql=true
        # spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect # Optional, Spring Boot usually auto-detects
        ```

4.  **Build the Project (Optional, your IDE might do this):**
    If you want to build via Maven manually:
    ```bash
    mvn clean install
    ```

## Running the Application

1.  **Via IDE:**
    * Import the project as a Maven project into your IDE.
    * Find the main class `TaskflowApplication.java` (in `br.com.davidev.taskflow`).
    * Run the `main` method of this class (usually right-click > "Run" or "Debug").

2.  **Via Command Line (Maven):**
    In the project's root directory, execute:
    ```bash
    mvn spring-boot:run
    ```

The application will be running at `http://localhost:8080` (Tomcat's default embedded port).

## API Documentation (Endpoints)

The base URL for all endpoints is: `http://localhost:8080/api/v1/tasks`

---

### 1. Create New Task

-   **HTTP Method:** `POST`
-   **URL:** `/` (relative to the base URL, i.e., `http://localhost:8080/api/v1/tasks`)
-   **Description:** Creates a new task in the system.
-   **Request Body (JSON):**
    ```json
    {
        "descricao": "Description of the new task",
        "status": "PENDING", // Ex: PENDING, IN_PROGRESS, COMPLETED
        "dataConclusaoPrevista": "YYYY-MM-DDTHH:MM:SS" // Ex: "2025-12-31T10:00:00" (Optional)
    }
    ```
-   **Success Response:**
    -   **Code:** `201 Created`
    -   **Body (JSON):** The newly created task, including its `id` and `dataCriacao`.
        ```json
        {
            "id": 1,
            "descricao": "Description of the new task",
            "status": "PENDING",
            "dataCriacao": "YYYY-MM-DDTHH:MM:SS.fffffffff", // Auto-generated
            "dataConclusaoPrevista": "2025-12-31T10:00:00",
            "dataFinalizacao": null
        }
        ```

---

### 2. List All Tasks

-   **HTTP Method:** `GET`
-   **URL:** `/`
-   **Description:** Returns a list of all registered tasks.
-   **Request Body:** None.
-   **Success Response:**
    -   **Code:** `200 OK`
    -   **Body (JSON):** An array of task objects.
        ```json
        [
            {
                "id": 1,
                "descricao": "Description of task 1",
                "status": "PENDING",
                "dataCriacao": "...",
                "dataConclusaoPrevista": "...",
                "dataFinalizacao": null
            },
            {
                "id": 2,
                "descricao": "Description of task 2",
                "status": "IN_PROGRESS",
                "dataCriacao": "...",
                "dataConclusaoPrevista": "...",
                "dataFinalizacao": null
            }
        ]
        ```
        (Will return `[]` if no tasks exist).

---

### 3. Get Task by ID

-   **HTTP Method:** `GET`
-   **URL:** `/{id}` (e.g., `/1` to fetch task with ID 1)
-   **Description:** Returns the details of a specific task.
-   **Request Body:** None.
-   **Success Response:**
    -   **Code:** `200 OK`
    -   **Body (JSON):** The found task object.
        ```json
        {
            "id": 1,
            "descricao": "Description of task 1",
            "status": "PENDING",
            "dataCriacao": "...",
            "dataConclusaoPrevista": "...",
            "dataFinalizacao": null
        }
        ```
-   **Error Response:**
    -   **Code:** `404 Not Found` (If the task with the specified ID does not exist).

---

### 4. Update Existing Task

-   **HTTP Method:** `PUT`
-   **URL:** `/{id}` (e.g., `/1` to update task with ID 1)
-   **Description:** Updates the information of an existing task.
-   **Request Body (JSON):** The fields to be updated.
    ```json
    {
        "descricao": "New updated description",
        "status": "COMPLETED",
        "dataConclusaoPrevista": "YYYY-MM-DDTHH:MM:SS" // New date (Optional)
    }
    ```
-   **Success Response:**
    -   **Code:** `200 OK`
    -   **Body (JSON):** The updated task.
        ```json
        {
            "id": 1,
            "descricao": "New updated description",
            "status": "COMPLETED",
            "dataCriacao": "...", // Remains the same
            "dataConclusaoPrevista": "...", // The new value, if sent
            "dataFinalizacao": "..." // Populated if status changed to COMPLETED
        }
        ```
-   **Error Response:**
    -   **Code:** `404 Not Found` (If the task with the specified ID does not exist – currently, the service throws an exception which might result in a 500 error; this can be refined).

---

### 5. Delete Task

-   **HTTP Method:** `DELETE`
-   **URL:** `/{id}` (e.g., `/1` to delete task with ID 1)
-   **Description:** Removes a task from the system.
-   **Request Body:** None.
-   **Success Response:**
    -   **Code:** `204 No Content`
    -   **Body:** None.
-   **Error Response:**
    -   **Code:** `404 Not Found` (If the task with the specified ID does not exist and cannot be deleted – currently, the service throws an exception which might result in a 500 error; this can be refined).

---

## How to Test the API

It is recommended to use tools like [Postman](https://www.postman.com/) or [Insomnia](https://insomnia.rest/) to send HTTP requests to the endpoints listed above and verify the responses.

## Future Enhancements Planned (Examples)

-   Improve error handling and validations.
-   Implement DTOs (Data Transfer Objects).
-   Add pagination and sorting to results.
-   Implement user authentication and authorization.
-   Develop a frontend to interact with the API.

---

**Author**

Davi <YOUR_LAST_NAME_HERE>

* GitHub: `https://github.com/<YOUR_GITHUB_USERNAME_HERE>`
* LinkedIn: `https://linkedin.com/in/<YOUR_LINKEDIN_PROFILE_HERE>` (If applicable)

---
