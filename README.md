
# Spring Cloud Microservice Boilerplate Code

This project was created to help jump start microservice development using Spring Cloud. Due to **Netflix OSS**
being phased out, this projects shows microservice pattern implementation using Spring Cloud native dependencies.
- *Note: Although Netflix OSS is in maintenance mode, this project uses Eureka which is still supported.*

---

### Implemented Microservice Patterns
| Design Pattern | Description | Service | Tool |
|----------------|-------------|---------|------|
| Service Registry | Discovery Server | Discovery-Server | Netflix Eureka |
| Circuit Breaker | Fault tolerance | ServiceA | Spring Resilience4j |
| Gateway | Single access point to serve APIs | Gateway-Service | Spring Cloud Gateway |
| Client-Side Load Balancing | Routing optimization | ServiceA | Spring Cloud LoadBalancer |
| Centralized App Configuration | Runtime configuration modification | Config-Server | Spring Cloud Config |

### Microservice Patterns Not Implemented
- Database synchronization using events
- Communication with RPC (REST) or Messaging (Broker)
- API contract
- CORS configuration
- IAM (identity and access management) for authorization and authentication
- Access tokens using JWT
- Monitoring
- Containerization [docker]
- Orchestration [kubernetes]
- Continuous delivery [jenkins]
- Testing services and integration

---

## Projects and dependencies
- `Discovery-Server`: Eureka Server
- `Hidden-Service`: Eureka Discovery Client, Spring Web
- `Config-Server`: Eureka Discovery Client, Spring Config Server
- `ServiceA`: Eureka Discovery Client, Spring Web, Spring Reactive Web, Spring Cloud Circuit Breaker (Resilience4j), Spring AOP, Spring Cloud LoadBalancer, Spring Config Client, Spring Cloud Bootstrap
- `Gateway-Service`: Eureka Discovery Client, Spring Cloud Gateway

---

## Detailed Design Pattern Implementation
Below you can see how services work to implement each microservice design pattern.

### Eureka Discovery Server
#### Registery service
- `Discovery-Server`: A registry that takes care of service discovery using Netflix Eureka.
- `ServiceA`: A Eureka client tha uses the discovery server to find the 'Hidden-Service'.
- `Hidden-Service`: A basic Eureka client service with a simple rest controller. It is registered with the discovery server.
- `Config-Server`: A Eureka client that is registered so other services can find and get config files.
- `Gateway-Service`: A Eureka client that finds services by using the discovery service. However, it does not register with the discovery server.

### Spring Cloud Configuration Server
#### Centralized configuration service
- `Config-Server`: A service that handles centralized configurations. Configs are retrieved from git and served to services.
- `ServiceA`: A basic service that loads its config from the config server
- `Hidden-Service`: A basic service that loads its config from the config server
- `Config-Repository`: A Folder on git, not a project. It holds configurations for multiple services

### Spring Cloud Gateway
#### Intelligent routing
- `Gateway-Service`: Forwards requests using defined routes. Also load balances when forwarding requests to service instances.
- `ServiceA`: A simple service that gets requests forwarded to it from the gateway. Then, the service calls the 'Hidden-Service' endpoint.
- `Hidden-Service`: A service that gets requests routed to it. It does not have a gateway routing.

### Spring Cloud LoadBalancer
#### Client load balancing [netflix ribbon deprecated]
- `ServiceA`: Makes a load balanced call to the 'Hidden-Service'
- `Hidden-Service`: A basic service that can be called by a client

### Spring Cloud CircuitBreaker using Resilience4j
#### Circuit breaker [netflix hystrix deprecated]
- `ServiceA`: Makes a circuit breaker protected call to the 'Hidden-Service'
- `Hidden-Service`: A basic service that can be called by a client

---

## Notes
- This project was created in a maven module format for easier development. There is a parent pom.xml, and child pom.xml.
  Make sure you do not but conflicting service dependencies in the parent pom.xml.
- This project makes the assumption it will be used for synchronous web calls. It is not designed to support reactive programming or webflux.
- Add the `spring-cloud-starter-bootstrap` to any client that needs to connect to a 'Spring Cloud Config Server'
  using a bootstrap file.
- You must add the `spring-boot-starter-aop` to the pom.xml of any service that uses 'resilienc4j'. Otherwise, annotations will not work!
- Add more runtime configurations in order to run a service with multiple instances.

### Multi-module project
- Make sure you update the pom.xml file of any child service added. It's parent needs to be updated to the
  projects parent, duplicate info needs to be removed, and it's a good idea to specify the .jar output name.

---

## Running the project

### With IntelliJ
- First run the `Discovery-Server` to allow clients to connect to it when they startup
- Then run the `Config-Server` to allow clients to use boostrap to set up their configuration
- Now, you can run any service (eureka clients, config clients, gateway, basic services)

### With Docker CLI
- Run maven package for the project root
- Run `docker-compose up --build`
- Everything should get up and running properly


