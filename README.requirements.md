# Pet Manager - Java Developer Assignment
Java developer assignment for an interview

# Objective
Develop a simple REST application using Spring Boot for managing pets. The
application must expose REST APIs for creating, updating, deleting, and retrieving pet
information.
# Requirements
- The candidate must implement a Spring Boot application with Java 17+.
- Use of a relational database. For the purpose of this exercise, it is sufficient to mock
database access services.
- Exposure of REST APIs using Spring Web.
# Additional Requirements
- A refactoring of the application has already been planned to replace the chosen
database with a non-relational one. The code should be somewhat prepared for this
eventuality.
# Project Description 
The application must manage a Pet entity with the following properties:
- id (Long, auto-generated)
- name (String, required)
- species (String, e.g., "Dog", "Cat", "Rabbit", required)
- age (Integer, optional, must be greater than or equal to 0)
- ownerName (String, optional)
The application must provide a set of REST APIs for accessing the Pet entity.
Additional Considerations 

The code should be well-structured and readable.

The requirements are intentionally minimal, allowing the candidate the freedom to
implement the project following the best practices they consider important.