# Order optima
The Order Optima application is a comprehensive solution designed to streamline ordering processes and enhance collaboration between vendors/suppliers and restaurants. This documentation provides detailed information on the Order Optima microservices architecture.

<br>

## Functional services

Order optima is decomposed into 4 core microservices. All of them are independently deployable applications organized around certain business domains.

#### Auth service
 Handles user authentication and authorization for secure access to Order Optima microservices.

Method	| Path	| Description	| User	| Admin
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
POST	| /api/v1/auth/login	| login user to system	|   |
POST	| /api/v1/auth/forgot-password	| reset user password	|   | 



#### user service
Manages user accounts, including registration, login, and profile information.

Method	| Path	| Description	| User	| Admin
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
POST	| /api/v1/user/restaurant/register	| register restaurants          |  | 	
GET	| /api/v1/user/restaurant/get/{id}	| Get current specified restaurant 	|  | 
GET	| /api/v1/user/restaurant/all	| get all registerd restaurant	|  x | 
GET	| /api/v1/user/restaurant/search	| search restaurant by name	|   |  
PUT	| /api/v1/user/restaurant/approve/{id}	| approve specific  restuarant registerd 	| x  | 
DELETE	| /api/v1/user/restaurant/delete/{id}	| delete specific restauarnt	|  x |  |
POST	| /api/v1/user/api/v1/user/vendor/register	| register vendors         | x | 	
GET	| /api/v1/user/api/v1/user/vendor/get/{vendorId}	| get specified vendor          | x | 	
GET	| /api/v1/user/api/v1/user/vendor/all	| return all registed vendors        | x | 	
GET	| /api/v1/user/api/v1/user/vendor/search	| search vendors by name       | x | 	
DELETE	| /api/v1/user/api/v1/user/vendor/delete/{vendorId}	| delete specified vendor       |  x| 	






#### Inventory service
Provides  inventory data and stock management functionalities for vendors and restaurants.

Method	| Path	| Description	| User	| Admin
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
GET	| /api/v1/category/all	| get all catagory	|   |  
GET	| /api/v1/category/{id}	| get specified catagory	|   | 
POST	| /api/v1/products/create	| create product	|   | 
GET	| /api/v1/products/{id}	| return specified product 	|   | 
GET	| /api/v1/products/search	| search products by name 	|   | 	|   | 


#### Order service
Orchestrates the ordering process, handling order creation, modification, and fulfillment.

Method	| Path	| Description	| User	| Admin
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
POST	| /api/v1/orders/place	| create an order	|   | 
GET	| /api/v1/orders/all	| return specified order 	|   | 
GET	| /api/v1/orders/restaurant/{restaurantId}	| get  orderd restaurant	|   | 	|   | 
PUT	| /api/v1/orders/{orderId}/complete	| update order status	|   | x	|  | 

## Infrastructure

[![](https://app.eraser.io/workspace/NINw1zDx4sdwuyg424ZY/preview?elements=ZLhUWgGHwdQUxw0FG4ppHA&type=embed)](https://app.eraser.io/workspace/NINw1zDx4sdwuyg424ZY?elements=ZLhUWgGHwdQUxw0FG4ppHA)

### Auth service
 Handles user authentication and authorization for secure access to Order Optima microservices.

Spring Cloud Security provides convenient annotations and autoconfiguration to make this really easy to implement on both server and client side. You can learn more about that in [documentation](http://cloud.spring.io/spring-cloud-security/spring-cloud-security.html).

On the client side, everything works exactly the same as with traditional session-based authorization. You can retrieve `Principal` object from the request, check user roles using the expression-based access control and `@PreAuthorize` annotation.

Each orderoptima client has a scope: `server` for backend services  We can use `@PreAuthorize` annotation to protect controllers from  an external access:

``` java
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryRegistrationRequest updateCategory(@PathVariable Long id, @RequestBody CategoryRegistrationRequest request){
        return categoryService.updateCategory(id, request);
    }
```

### API Gateway
API Gateway is a single entry point into the system, used to handle requests and routing them to the appropriate backend service or by [aggregating results from a scatter-gather call](http://techblog.netflix.com/2013/01/optimizing-netflix-api.html). 


```yml
spring:
  application:
    name: api-service

eureka:
  client:
    service-url:
      defaultZone : http://localhost:8761/eureka
server:
  port: 8181
```

### Service Discovery

Service Discovery allows automatic detection of the network locations for all registered services. These locations might have dynamically assigned addresses due to auto-scaling, failures or upgrades.

The key part of Service discovery is the Registry. In this project, we use Netflix Eureka.
With Spring Boot, you can easily build Eureka Registry using the `spring-cloud-starter-eureka-server` dependency, `@EnableEurekaServer` annotation and simple configuration properties.

Client support enabled with `@EnableDiscoveryClient` annotation a `application.yml` with application name:
``` yml
spring:
  application:
    name: auth-service
```

This service will be registered with the Eureka Server and provided with metadata such as host, port, health indicator URL, home page etc. Eureka receives heartbeat messages from each instance belonging to the service. If the heartbeat fails over a configurable timetable, the instance will be removed from the registry.

Also, Eureka provides a simple interface where you can track running services and a number of available instances: `http://localhost:8761`

## Infrastructure automation

Here is a simple Continuous Delivery workflow, implemented in this project:
[![](https://app.eraser.io/workspace/ON9G8nZqoZOjA9qjpLQ1/preview?elements=vbrUMi01z7Q_UAitwTrvoA&type=embed)](https://app.eraser.io/workspace/ON9G8nZqoZOjA9qjpLQ1?elements=vbrUMi01z7Q_UAitwTrvoA)

In this [configuration](http://git.gebeya.training/order-optima/backend-services/backend-microservice.git/.gitlab-ci.yml), Gitlab CI builds tagged images for each successful git push. So, there are always the `latest` images for each microservice on [Docker Hub](https://hub.docker.com/r/sondawitmekonnen/) and older images, tagged with git commit hash. It's easy to deploy any of them and quickly rollback, if needed.

## Let's try it out

Note that starting 6 Spring Boot applications, postgres  instances  requires at least 4Gb of RAM.

#### Before you start
- Install Docker and Docker Compose.
- Change environment variable values in `.env` file 

#### Production mode
In this mode, all latest images will be pulled from Docker Hub.
Just copy `docker-compose.yml` and hit `docker-compose up`

#### Development mode
If you'd like to build images yourself, you have to clone the repository and  run `docker-compose -f docker-compose.yml -f docker-compose.dev.yml up`

`docker-compose.dev.yml` inherits `docker-compose.yml` with additional possibility to build images locally and expose all containers ports for convenient development.

If you'd like to start applications in Intellij Idea you need to either use [EnvFile plugin](https://plugins.jetbrains.com/plugin/7861-envfile) or manually export environment variables listed in `.env` file (make sure they were exported: `printenv`)

#### Important endpoints
- http://localhost:8181 - Gateway
- http://localhost:8761 - Eureka Dashboard

