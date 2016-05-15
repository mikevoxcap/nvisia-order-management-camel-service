# nvisia-order-management-camel-service

## Overview
This project holds an example that leverages Apache Camel 2.17 and Spring Boot 1.3 to provide out an API around order management. Here are the technologies used:

* Apache Maven - build management
* Apache Camel - Mediator and routing framework implementing EIPs
* Spring Boot - Opinionated framework for quickly newing up applications that leverage the Spring Framework

## Testing 
The best way to test out the API is to leverage Swagger. I've added the Swagger dist into the project's webapp folder. Just start up the server and you can access Swagger UI from the following URL:

http://localhost:8082/nvisia-order-management-camel-service/swagger/index.htm 

When the page opens, simply enter the following as the API URL:

http://localhost:8082/nvisia-order-management-camel-service/api/api-doc
