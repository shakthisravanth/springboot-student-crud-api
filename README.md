How to Run the Application ?

Prerequisites:

make sure you have the following installed before running the application:

Java (JDK): Required to compile and run the Spring Boot application.

MySQL: The application likely uses a MySQL database, which must be installed and running (and configured correctly within the application) for it to function properly.


You can start the Spring Boot application from the directory where the project is located 
(e.g., /Users/apple/Desktop/Student).

Use one of the following commands:

Using the Maven Wrapper:

**Bash**

./mvnw spring-boot:run



Using Maven (if installed globally):

**Bash**

mvn spring-boot:run
Once started, the application will be listening on:  http://localhost:8080


All the samples everything is present in the Postman collection :

postman collection

https://shakthi-6dbba4b2-2786614.postman.co/workspace/Shakthi_Kodnest's-Workspace~b8e09ba8-bab4-4add-8507-c5bd99debd69/collection/50161021-fcea206b-6326-44af-89dc-c4126b4842d9?action=share&source=copy-link&creator=50161021




**Student CRUD API with JWT Authentication**

A Spring Boot application featuring User Authentication (Signup & Signin) using JWT, and a fully secured Student CRUD API.


**Authentication Flow**

User signs up → account is created → password encrypted → token returned

User signs in → credentials verified → JWT token returned

User calls protected APIs → includes Authorization: Bearer <token>

JWT Filter validates token → access granted



