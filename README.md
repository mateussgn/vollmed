# Vollmed API
Project developed based on the Alura Spring Boot Course
- During this course, I created a REST API using Spring Boot and also developed a CRUD.
- I learned what Spring Boot is and what its main differences are compared to traditional Spring. I used Spring Initializr to build my project.
- I understood how the directory structure works and the "pom.xml" file. I learned to use some of its modules, such as Web, Validation, and Spring Data JPA.
- I used other libraries, such as the MySQL driver, Flyway, and Lombok. I also learned how configurations work in a Spring Boot project.
- I learned how to perform migrations to control the database's evolution history and how to implement the REST API from creating the controllers. I also learned to use repositories to simplify database access and JPA.
- I mapped JPA entities and implemented CRUD with validations.
- Standardization of API method returns using ResponseEntity, applying HTTP protocol best practices.
- Error handling whenever an exception occurs within any controller class.
- Add Spring Security to the project, implementing the concept of authentication and authorization through JWT (JSON Web Token) tokens.
- Understanding of security configurations for Spring Security.
- Configure Spring Security to not follow the standard authentication and authorization process.
- Perform authentication with stateless configuration.
- Configure password hashing using the BCrypt algorithm.
- Application of filters to intercept requests, verify if the JWT token is included, and log the user.
- For each request, we need to authenticate the user to Spring due to the stateless concept.
