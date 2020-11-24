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

一分钟了解微服务的好处和陷阱
微服务架构设计代表了一种架构设计思想，配合现在的容器技术（如 Docker），可在软件开发流程、部署、服务维护等各方面产生效率提升。

但不一定所有的业务场景都适合微服务，有时候非常简单的业务场景下，微服务反而会降低效率。什么是微服务，其特性，好处及陷阱，是本文要讨论的内容。

一、什么是微服务

微服务是一种软件架构风格，它是以专注于单一责任与功能的小型功能区块为基础，利用模组化的方式组合出复杂的大型应用程序，各功能区块使用与语言无关的 API（例如 REST）集相互通讯，且每个服务可以被单独部署，它具备以下三个核心特点：

微服务为大型系统而生。随着业务的快速增长，会带来系统流量压力和复杂度的上升，系统的可维护性和可扩展性成为架构设计的主要考虑因素，微服务架构设计理念通过小而美的业务拆分，通过分而自治来实现复杂系统的优雅设计实现。

微服务架构是面向结果的。 微服务架构设计风格的产生并非是出于学术或为标准而标准的设计，而是在软件架构设计领域不断演进过程中，面对实际工业界所遇到问题，而出现的面向解决实际问题的架构设计风格。

专注于服务的可替代性来设计。 微服务架构设计风格核心要解决的问题之一便是如何便利地在大型系统中进行系统组件的维护和替换，且不影响整体系统稳定性。

二、微服务的特征

每个微服务仅对单个业务负责，且为该业务的容量负责；

每个微服务可以进行独立部署，即不需要依赖其它微服务及其相关资源，如数据库、内存缓存系统等；

轻量级的通信协议，例如 REST、STOMP、AMQP 等；

服务的可替代性，代表着每个微服务原则上都可以使用不同的语言、框架进行技术实现，且更换实现后的微服务对于整个业务系统不会造成影响；

每个微服务拥有单独的数据存储；

每个微服务由小团队维护，服务以业务来进行拆分后，每个微服务的维护工作将有人数不多的小团队进行维护；

三、微服务带来的好处

独立的可扩展性，每个微服务都可以独立进行横向或纵向扩展，根据业务实际增长情况来进行快速扩展；

独立的可升级性，每个微服务都可以独立进行服务升级、更新，不用依赖于其它服务，结合持续集成工具可以进行持续发布，开发人员就可以独立快速完成服务升级发布流程；

易维护性，每个微服务的代码均只专注于完成该单个业务范畴的事情，因此微服务项目代码数量将减少至 IDE 可以快速加载的大小，这样可以提高了代码的可读性，进而可以提高研发人员的生产效率；

语言无关性，研发人员可以选用自己最为熟悉的语言和框架来完成他们的微服务项目（当然，一般根据每个公司的实际技术栈需要来了），这样在面对新技术或新框架的选用时，微服务能够更好地进行快速响应；

故障和资源的隔离性，在系统中出现不好的资源操作行为时，例如内存泄露、数据库连接未关闭等情况，将仅仅只会影响单个微服务；

优化跨团队沟通，如果要完全实践微服务架构设计风格，研发团队势必会按照新的原则来进行划分，由之前的按照技能、职能划分的方式变为按照业务（单个微服务）来进行划分，如此这般团队里将有各个方向技能的研发人员，沟通效率上来说要优于之前按照技能进行划分的组织架构；

原生基于“云”的系统架构设计，基于微服务架构设计风格，我们能构建出来原生对于“云”具备超高友好度的系统，与常用容器工具如 Docker 能够很方便地结合，构建持续发布系统与 IaaS、PaaS 平台对接，使其能够方便的部署于各类“云”上，如公用云、私有云以及混合云。

四、避免微服务的陷阱

不要以微服务作为开始，在项目刚开始时，一般都还很小，不需要进行非常完整的业务拆分，如果采用“微服务”作为开始会有点杀鸡用牛刀的感觉，当然，你的项目非常之庞大的话，以“微服务”为始是个不错的选择；

不要自己进行基础设施的管理，微服务意味着一堆的数据库、消息系统、数据缓存系统等，会带来相应的运维管理成本（这里的前提是，没有良好的自动化运维平台和工具），建议多使用 IaaS、PaaS 平台，部署发布与其对接；

无 DevOps、不微服务，如果研发团队不具备 DevOps 的理念并贯彻执行，仅想单独来实施微服务的话，在实施过程中会发现比之前的架构维护要困难些，主要原因是微服务需要持续集成、持续部署及监控等工具或系统的配合才能降低其带来的维护成本；

不要创建过多的微服务，微服务的业务颗粒度一定要根据实际业务系统的现状及日后规划来制定，切记不要制定过细的拆分颗粒度；

可能带来的延迟问题，由于服务拆分开来，部署到不同的平台或网络，可能会引起微服务间的调用延迟问题，服务间的调用延迟可能带来整体系统的响应缓慢问题；

微服务不是银弹。
