# Online Ticket Shop

## REST services for an online store with tickets for plays, using Spring Boot framework.

## Features:
- Get top events by sold tickets
- Get number of available seats for an event
- Get events filtered by month or year
- Get orders of client
- Get orders of client filtered by month or year
- Get plays where the client has tickets
- Get all active actors in a year
- Create for all entities
- Get by id for all entities
- Get all entities
- Update for all entities
- Delete for entities

## The ERD Diagram:
![Online ticket shop ERD Diagram](https://github.com/LauraElenaCozma/Spring-Ticket-Shop/blob/main/ticketshop/erd_diagram.JPG)

## And a more simplified version of the schema is:
![Online ticket shop schema](https://github.com/LauraElenaCozma/Spring-Ticket-Shop/blob/main/ticketshop/database_schema.png)

## Spring Boot features:
- Beans defined for the services, repositories, controller, etc.
- DTO classes and object mappers
- Validation for classes 
- REST services for all the features defined earlier
- Unit test using Mockito for the service package with coverage 98% and some integration tests
- The project was documented using Swagger, at `http://localhost:8080/swagger-ui`
- `@ControllerAdvice` for exception handling

## Swagger interface:
![Swagger interface](https://github.com/LauraElenaCozma/Spring-Ticket-Shop/blob/main/ticketshop/Swagger.JPG)
