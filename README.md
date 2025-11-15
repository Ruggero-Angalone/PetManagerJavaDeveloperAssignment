# PetManager API

## Overview
This project is a simple Spring Boot application for managing pets. It exposes REST APIs to create, read, update, and delete pets, and demonstrates a basic structure for a service layer and DAO layer.

## Further Improvements
- Introduce a table for owners and migrate to using `ownerId` as the foreign key to ensure a reliable and consistent relationship between pets and their owners.
- Implement security for the APIs (e.g., token-based authentication).
- Introduce a table for species to allow dynamic addition instead of hard-coded enums.
- Replace the in-memory mocked database with a real relational database. 

```
Note: The commit history shows some early experimentation with an Owner entity that was ultimately removed to match the assignment requirements.
```

Thanks for reading.