# PetManager - Commands & Running Instructions

### Prerequisites
- Java 17+ installed
- Maven installed
- (Optional) intellij for the run configurations

### Build the Project
From the project root:

```bash
mvn clean install
```
### Run the project
#### Using IntelliJ
- Open the project 
- Run RUN as a Spring Boot app
#### From the command line
```bash
mvn spring-boot:run
```
### Swagger API Documentation
After running the project, Swagger UI is available at:
http://localhost:8080/swagger-ui/index.html
### Run test:
#### From intellij
- Run the configuration "RUN TEST"
#### From the command line
```bash
mvn test
```