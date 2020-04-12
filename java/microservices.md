# API Gateway(Zuul/Traefik/Spring Cloud Gateway)

    Security, Rate Limiting, Access Control, Fault Tolerance, Service Aggregation

    - Zuul: @EnableZuulProxy
        request url will be zuul-server-url/application.name/api-url
        @FeignClient(name="zuul-api-gateway-application.name")

# Registry(Naming) Server: Eureka

    - @EnableEurekaServer port: 8761
    - @EnableDiscoveryClient

# Client side Load Balance: Ribbon

    -  @RibbonClient
    -  without Eureka only: currency-exchange-service.ribbon.listOfServers=http://localhost:8000,http://localhost:8001

# Config server: retrieve config from git based on spring profile

    - server side:  @EnableConfigServer, spring.cloud.config.server.git.uri=file://
    - client side:
       bootstrap.properties : spring.cloud.config.uri=http://localhost:8888

# Feign : call other restful ws or microservices

- @EnableFeignClients
- Proxy: @FeignClient

# Distribute Tracing: Zipkin & Spring sleuth

# Spring Cloud Bus

send one get request to /bus/refresh...

RABBIT_URI=amqp://localhost

# Hystrix: Fault Tolerance

## Ports

| Application                       | Port            |
| --------------------------------- | --------------- |
| Limits Service                    | 8080, 8081, ... |
| Spring Cloud Config Server        | 8888            |
| Netflix Eureka Naming Server      | 8761            |
| Netflix Zuul API Gateway Server   | 8765            |
| Zipkin Distributed Tracing Server | 9411            |
