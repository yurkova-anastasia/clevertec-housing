# Web Application for House and Tenant Management

## Description
This web application is designed for managing houses and their tenants using Spring Boot and Hibernate. The system provides a REST API for performing CRUD operations on House and Person entities.

## Database Initialization with Liquibase and Docker
The application's database is initialized using Liquibase, 
and the entire setup is containerized using Docker. 
The `docker-compose.yml` file orchestrates the PosgresSQL database and Liquibase for migrations.


## Entities
### House
- id (Database identifier)
- uuid (Unique identifier for the house)
- area (Area of the house)
- country (Country where the house is located)
- city (City where the house is located)
- street (Street where the house is situated)
- number (House number)
- create_date (Creation date of the house)
- update_date (Last update date of the house record)
- owners (List of house owners)
- tenants (List of house residents)

### Person
- id (Database identifier)
- uuid (Unique identifier for the person)
- name (First name)
- surname (Last name)
- sex (Gender: Male or Female)
- passport_series (Passport series)
- passport_number (Passport number)
- create_date (Creation date of the person record)
- update_date (Last update date of the person record)
- residency (House where the person resides)

## REST API
### House API
- **GET /houses**: Get a list of all houses with pagination (default page size: 15).
- **GET /houses/{id}**: Get information about the house with the specified identifier.
- **POST /houses**: Create a new house.
- **PUT /houses/{id}**: Update information about the house with the specified identifier.
- **DELETE /houses/{id}**: Delete the house with the specified identifier.

### Person API
- **GET /people**: Get a list of all people with pagination (default page size: 15).
- **GET /people/{id}**: Get information about the person with the specified identifier.
- **POST /persons**: Create a new person record.
- **PUT /people/{id}**: Update information about the person with the specified identifier.
- **DELETE /people/{id}**: Delete the person record with the specified identifier.

### Additional APIs
- **GET /houses/{houseId}/residents**: Get a list of all residents of the house with the specified identifier.
- **GET /persons/{personId}/owned-houses**: Get a list of all houses owned by the person with the specified identifier.

## Configuration File: application.yml
```yaml
  datasource:
    url: jdbc:postgresql://localhost:5432/postgres
    username: your_username
    password: your_password
