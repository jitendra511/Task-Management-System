
# Task Management System

This is a Role based(Admin,User) Task Management System designed to help organize their tasks with ease and efficiency. It is a backend micro-service architecture, leveraging the robust Spring Boot framework for the backend, and the dependable MySQL database for data storage.



## Features

- User Authentication - Users can register as admin or normal user and log in to access the task securely. I Used JWT Authentication which ensures that only authorized users can perform operations.
- API Gateway - API Gateway acts as the central entry point for managing and routing all client requests to backend services.
- Eureka Server - Eureka Server is a service registry that plays a key role in service discovery in microservices architectures. It allows services to register themselves and discover other services without hardcoding their locations, enabling dynamic scaling, failover, and seamless communication.

- Fault tolerance - In distributed systems, fault tolerance ensures the application remains functional despite hardware, software, or network failures, which is critical for maintaining availability, reliability, and a good user experience.

- Task Operation
  - Create Task - Users can effortlessly add new tasks to the system.
  - Get Task - displays all tasks for quick and easy reference.
  - Edit Task - Users have the flexibility to modify task details as needed.
  - Delete Task - Unwanted tasks can be removed to keep the task list organized.

## Tech Stack

**Client:** Java, Spring Boot, Micro Services, MySQL.

**Server:** Tomcat Server


## Project Setup
   - ## Backend
        - In Eclipse or similar IDE import the "backend" from this repo with option "import existing maven project".
        - Build the maven project to install all the required dependencies.
        - To setup database for every micro-services, install MySQL.
          - Make database for task user service micro-service which named as tmsuserdb and update below details in file application.properties inside /resources folder.
            - spring.datasource.url=jdbc:mysql://localhost:3306/tmsuserdb
            - spring.datasource.username=root
            - spring.datasource.password=root
          - Make database for task service micro-service which named as tmstaskdb and update below details in file application.properties inside /resources folder.
            - spring.datasource.url=jdbc:mysql://localhost:3306/tmstaskdb
            - spring.datasource.username=root
            - spring.datasource.password=root
          - Make database for task service micro-service which named as tmssubmissiondb and update below details in file application.properties inside /resources folder.
            - spring.datasource.url=jdbc:mysql://localhost:3306/tmssubmissiondb
            - spring.datasource.username=root
            - spring.datasource.password=root

        - Run the all Spring Boot application using your IDE.
  

## API Endpoints
&nbsp;1. Create Task
- Endpoint: POST http://localhost:8080//tms/tasks/createTask
- Description: Creates a new task for the specified user.

&nbsp;2. Get Task by Id
- Endpoint: POST http://localhost:8080/tms/tasks/getTaskById/{id}
- Description: Retrieves a task by its ID.

&nbsp;3. Get All Task 
- Endpoint: POST http://localhost:8080/tms/tasks/getAllTask
- Description: Retrieves all task.

&nbsp;4. Delete Task by Id
- Endpoint: DELETE http://localhost:8080/tms/tasks/deleteTask/{taskId}
- Description: Delete a task by its ID.

&nbsp;4. Update Task by Id
- Endpoint: PUT http://localhost:8080/tms/tasks/updateTask/{taskId}
- Description: Update or Modify a task by its ID.

&nbsp;4. assigned Tasks of User 
- Endpoint: PUT http://localhost:8080/tms/tasks/assignedTasksOfUser
- Description: Retrieves Task which is already assigned to user

&nbsp;4. assigned Tasks To User 
- Endpoint: PUT http://localhost:8080/tms/tasks/assignedTasksToUser/{taskId}/userId
- Description: Admin assign Task to user
