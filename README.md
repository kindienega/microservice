# Order optima
Order optima is a simple financial advisor app built to demonstrate the [Microservice Architecture Pattern](http://martinfowler.com/microservices/) using Spring Boot, Spring Cloud and Docker. The project is intended as a tutorial, but you are welcome to fork it and turn it into something else!

<br>

## Functional services

Order optima is decomposed into 4 core microservices. All of them are independently deployable applications organized around certain business domains.

#### Auth service
Contains general input logic and validation: incomes/expenses items, savings and account settings.

Method	| Path	| Description	| User authenticated	| Available from UI
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
GET	| /api/v1/auth/{login}	| Get specified account data	|  | 	
POST	| /api/v1/auth/login	| Register new account	|   | ×


#### user service
Performs calculations on major api/v1/user parameters and captures time series for each account. Datapoint contains values normalized to base currency and time period. This data is used to track cash flow dynamics during the account lifetime.

Method	| Path	| Description	| User authenticated	| Available from UI
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
GET	| /api/v1/user/{account}	| Get specified account api/v1/user	          |  | 	
GET	| /api/v1/user/current	| Get current account api/v1/user	| × | × 
GET	| /api/v1/user/demo	| Get demo account api/v1/user	|   | × 
PUT	| /api/v1/user/{account}	| Create or update time series datapoint for specified account	|   | 


#### Inventory service
Stores user contact information and notification settings (reminders, backup frequency etc). Scheduled worker collects required information from other services and sends e-mail messages to subscribed customers.

Method	| Path	| Description	| User authenticated	| Available from UI
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
GET	| /notifications/settings/current	| Get current account notification settings	| × | ×	
PUT	| /notifications/settings/current	| Save current account notification settings	| × | ×

#### Order service
Stores user contact information and notification settings (reminders, backup frequency etc). Scheduled worker collects required information from other services and sends e-mail messages to subscribed customers.

Method	| Path	| Description	| User authenticated	| Available from UI
------------- | ------------------------- | ------------- |:-------------:|:----------------:|
GET	| /notifications/settings/current	| Get current account notification settings	| × | ×	
PUT	| /notifications/settings/current	| Save current account notification settings	| × | ×

## Infrastructure

[![](https://app.eraser.io/workspace/NINw1zDx4sdwuyg424ZY/preview?elements=ZLhUWgGHwdQUxw0FG4ppHA&type=embed)](https://app.eraser.io/workspace/NINw1zDx4sdwuyg424ZY?elements=ZLhUWgGHwdQUxw0FG4ppHA)

### Auth service
Authorization responsibilities are extracted to a separate server, which grants [OAuth2 tokens](https://tools.ietf.org/html/rfc6749) for the backend resource services. Auth Server is used for user authorization as well as for secure machine-to-machine communication inside the perimeter.

In this project, I use [`Password credentials`](https://tools.ietf.org/html/rfc6749#section-4.3) grant type for users authorization (since it's used only by the UI) and [`Client Credentials`](https://tools.ietf.org/html/rfc6749#section-4.4) grant for service-to-service communciation.

Spring Cloud Security provides convenient annotations and autoconfiguration to make this really easy to implement on both server and client side. You can learn more about that in [documentation](http://cloud.spring.io/spring-cloud-security/spring-cloud-security.html).

On the client side, everything works exactly the same as with traditional session-based authorization. You can retrieve `Principal` object from the request, check user roles using the expression-based access control and `@PreAuthorize` annotation.

Each PiggyMetrics client has a scope: `server` for backend services and `ui` - for the browser. We can use `@PreAuthorize` annotation to protect controllers from  an external access:

``` java
@PreAuthorize("#oauth2.hasScope('server')")
@RequestMapping(value = "api/v1/auth/{name}", method = RequestMethod.GET)
public List<DataPoint> getapi/v1/userByAccountName(@PathVariable String name) {
	return api/v1/userService.findByAccountName(name);
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

