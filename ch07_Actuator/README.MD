# Actuator

## 端点

在Spring Boot的众多Starter POMs中有一个特殊的模块：spring-boot-starter-actuator。它是一个用于暴露自身信息的模块，主要作用是用于监控与管理，以了解应用程序运行时的内部状况  

引入依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-bbot-starter-actuator</artifactId>
</dependency>
```

```groovy
@Grab('spring-boot-starter-actuator')
```

三种端点：配置端点、度量端点和其他端点  
路径省略前缀：/actuator  
|路径|HTTP方法|描述|
|:---|:---|:---|
|/conditions|GET|提供了一份自动配置报告，记录哪些自动配置条件通过了，哪些没通过|
|/configprops|GET|描述配置属性（包含默认值）如何注入 Bean|
|/beans|GET|描述应用程序上下文里全部的bean，以及它们的关系|
|/caches|GET|获取所有的 Cachemanager|
|/caches/{cache}|DELETE|移除某个 CacheManager|
|/env|GET|获取全部环境属性|
|/env/{toMatch}|GET|获取指定名称的特定环境的属性值|
|/health|GET|报告应用程序的健康指标，由HealthIndicator的实现类提供|
|/httptrace|GET|提供基本的HTTP请求跟踪信息(时间戳、HTTP 头等)|
|/info|GET|获取应用程序定制信息，由Info开头的属性提供|
|/loggers|GET|获取bean的日志级别|
|/loggers/{name}|GET|获取某个包、类路径端点的日志级别|
|/loggers/{name}|POST|新增某个包、类路径端点的日志级别|
|/mappings|GET|描述全部的URL路径，以及它们和控制器(包含Actuator端点)的映射关系|
|/metrics|GET|报告各种应用程序度量信息，比如内存用量、HTTP请求计数|
|/metrics/{name}|GET|根据名称获取度量信息|
|/shutdown|POST|关闭应用，要求endpoints.shutdown.enabled为true|
|/scheduledtasks|GET|获取所有定时任务信息|
|/threaddump|GET|获取线程活动快照|

### 查看配置明细

获取Bean装配报告: GET http://localhost:8080/beans  

"positiveMatches"是通过了自动配置的beans；"negativeMatches"是无法完成自动配置的bean  

环境配置属性: GET http://localhost:8080/env  
配置的amazon属性: GET http://localhost:8080/env/amazon.associate_id  
端点到控制器的映射: GET http://localhost:8080/mappings  

### 运行时度量

查看应用运行时度量值: GET http://localhost:8080/metrics  
查看空闲内存: GET http://localhost:8080/metrics/mem.free  

/metrics端点报告的度量值和计数器  
|分类|前缀|报告内容|
|:---|:---|:---|
|垃圾收集器|gc.*|GC次数、GC耗费的时间，适用于标记-清理垃圾收集器和并行收集器（数据来源于java.lang.management.GarbageCollectorMXBean）|
|内存|mem.*|分配给应用程序的内存数量和空闲的内存数量（数据源自java.lang.Runtim）|
|堆|heap.*|当前堆内存用量，数据源自java.lang.management.MemoryUsage|
|类加载器|classes.*|JVM类加载器加载与卸载的类的数量，数据源自java.lang. management.ClassLoadingMXBean|
|系统|processors、instance.uptime、uptime、systemload.average|系统信息，例如处理器数量（数据源自java.lang.Runtime）、运行时间（数据源自java.lang.management.RuntimeMXBean）、平均负载（数据源自java.lang.management.OperatingSystemMXBean）|
|线程池|thread.*|线程、守护线程的数量、JVM启动后的线程数量峰值（数据源自java.lang .management.ThreadMXBean）|
|数据源|datasource.*|数据源链接的数量（DataSource Bean）|
|Tomcat会话|httpsessions.*|Tomcat的会话数和最大会话数（疏数据源自嵌入式Tomcat的Bean）|
|HTTP|counter.status.*、gauge.reponse.|多种应用程序HTTP请求的度量值与计数器|

追踪Web请求: GET http://localhost:8080/trace  
导出线程活动：GET http://localhost:8080/dump  
监控应用的健康状况：GET http://localhost:8080/health  
/health 端点所提供的所有信息都是由一个或多个健康指示器提供。  
spring boot自带的健康指示器：

|健康指示器|键|报告内容|
|:---|:---|:---|
|ApplicationHealthIndicator|none|永远为up|
|DataSourceHealthIndicator|db|如果数据库能连上，则内容是up和数据类型，否则为DOWN|
|DiskSpaceHealthIndicator|diskSpace|如果可用空间大于阈值，则内容为UP和可用磁盘空间，如果空间不足则为DOWN|
|JmsHealthIndicator|jms|如果能连上消息代理，则内容是up和JMS提供方的名称，否则为DOWN|
|MailHealthIndicator|mail|如果能连上邮件服务器，则内容是up和邮件服务器主机和端口，否则为DOWN|
|MongoHealthIndicator|mongo|如果能连上MongoDB服务器，则内容是up和MongoDB服务器版本，否则为DOWN|
|RabbitHealthIndicator|rabbit|如果能连上RabbitMQ服务器，则内容是up和版本号，否则为DOWN|
|RedisHealthIndicator|redis|如果能连上服务器，则内容是up和Redis服务器版本，否则为DOWN|
|SolrHealthIndicator|solr|如果能连上solr服务器，则内容是up，否则为DOWN|

### 关闭应用

application.properties:  

```text
    endpoints.shutdown.enabled=true
```

POST http://localhost:8080/shutdowm  

### 获取应用信息

GET http://localhost:8080/info  
GET http://localhost:8080/info/amazon.associate_id  

## 远程shell

CRaSH是一种能嵌入任意Java应用程序的shell，Spring Boot扩展了CRaSH，并添加了不少Spring Boot特有命令  

添加远程shell依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-bbot-starter-remote-shell</artifactId>
</dependency>
```

```groovy
@Grab("spring-boot-starter-remote-shell")
```

添加完依赖以后，构建并运行应用程序。  
在启动的时候，可以看到写进日志的密码，用户名为user，端口号2000  
SSH链接命令：
> ssh user@localhost -p 2000  
> Password authentication  
> Password:  

Spring Boot添加的shell命令  

|命令|描述|
|:---|:---|
|autoconfig|生成自动配置说明报告，和/autoconfig端点输出的内容类似，只是把JSON换成了纯文本|
|beans|列出Spring应用程序上下文的bean，与/beans端点输出的内容类似|
|endpoint|调用Actuator端点|
|metrics|显示Spring Boot的度量信息，与/metrics端点类似，但显示的是实时更新的数据。Ctrl+C返回shell|

### 调用Actuator端点

> endpoint list  
> endpoint invoke health  

输出的信息是原始格式，并非格式化的JSON文本  

## JMX

Actuator端点可以MBean的形式发布，以JMX查看和管理  
Actuator端点发布在org.springframework.boot域下，通过JConsole查看请求的映射端点  

## 定制Actuator
