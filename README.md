# PetManager API

## Overview
This project is a simple Spring Boot application for managing pets and their owners. It exposes REST APIs to add and remove pets and owners, and demonstrates a basic structure for a service layer and DAO layer.

## Design Decisions

### GenericDao and DAO Interfaces
- **GenericDao**: Created to provide a reusable base for common CRUD operations. It reduces code duplication and allows us to handle different entities with a consistent pattern.
- **OwnerDaoInterface & PetDaoInterface**: These interfaces define entity-specific operations for owners and pets. They separate the DAO logic from the service layer and allow for flexible implementations or testing.

### Foreign Key Consideration
Currently, `pet` entities reference their owner using `ownerName` as a foreign key. **This is not recommended**, because:
- `ownerName` is not guaranteed to be unique.
- Any change to an owner's name would break the relationship.
- It can lead to data integrity issues.

**Recommendation**: Migrate to using `ownerId` as the foreign key to ensure a reliable and consistent relationship between pets and their owners. This could not be implemented in this project because the requirement explicitly stated using `ownerName`.

## Authentication Recommendations
While this project does not currently implement authentication, in a production-ready API it is **highly recommended** to implement token-based authentication. Suggested approach:

- **Token-based Authentication**: Use JWT (JSON Web Tokens) or a similar mechanism to authenticate API requests.
- **Token Expiration**: Each token should have a limited lifetime (e.g., 15–60 minutes) to reduce risk if a token is compromised.
- **Refresh Tokens**: Optionally implement a refresh token mechanism to allow users to obtain new access tokens without re-authenticating.
- **Validation**: Every API request should validate the token to ensure it is still valid and not expired.
- **Revocation Policy**: Have a way to revoke tokens if a user logs out or if a token is suspected to be compromised.
- **Role-based Access Control (Optional)**: If different users have different permissions (e.g., admin vs. normal user), the token should include roles and permissions for access validation.

Implementing authentication with these measures would ensure that the API is secure and resistant to unauthorized access.


## Project Structure
- `com.petmanager.dao` - Contains DAO interfaces and implementations.
- `com.petmanager.entity` - Contains JPA entity classes.
- `com.petmanager.dto` - Contains response DTOs for API exposure.
- `com.petmanager.service` - Contains service classes that implement business logic.
- `com.petmanager.controller` - REST controllers exposing the APIs.

## Notes
- This project is primarily an exercise for learning DAO patterns, REST API design, and entity relationships in Spring Boot.
- Future improvements include:
  - Migrating to `ownerId` as the foreign key.
  - Implementing token-based authentication with expiration and validation.
  - Adding proper role-based access control if needed.
