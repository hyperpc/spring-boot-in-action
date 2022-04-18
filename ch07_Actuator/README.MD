# Actuator

## 端点

在Spring Boot的众多Starter POMs中有一个特殊的模块：spring-boot-starter-actuator。它是一个用于暴露自身信息的模块，主要用于监控与管理，以了解应用程序运行时的内部状况  

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

### 修改端点ID

> endpoints.shutdown.id=kill  

### 启用和禁用端点

> endpoints.enabled=false  
> endpoints.metrics.enabled=true  

### 添加自定义度量信息

Actuator会自动配置CounterService和GaugeService类的Bean  
Spring Boot已提供二者的实现。  
根据业务需求，可以将两个Bean的实例注入对应的Controller里  
对于复杂一点的度量值，可以实现PublicMetrics接口，重写metrics()方法，以提供自己所需的度量值  

### 创建自定义跟踪仓库

默认地，/trace端点报告的跟踪信息，在内存仓库里，最多存储100条  
为了提高存储容量，可以在ActuatorConfig类里声明自己的InMemoryTraceRepository Bean。  
如果信息巨大，想要永久保存，可以实现Spring Boot的TraceRepository接口  

- List&lt;Trace&gt; findAll()查找所有存储的Trace对象  
- void add(Map&lt;String, Object&gt; traceInfo)保存一个Trace，包含跟踪信息的Map对象  

通过引入以下依赖，可以存入MongoDB  

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-bbot-starter-data-mongodb</artifactId>
</dependency>
```

然后，在业务代码中，通过MongoOperations来对信息进行操作  

### 插入自定义健康指示器

声明一个AmazonHealth类，继承HealthIndicator，并重写health()方法  
在重写的方法中，通过Spring Boot的RestTemplate，向Amazon的url发起一个GET请求  
如果成功则返回一个“UP”状态: return Health.up().build()  
否则，返回一个“DOWN”状态：return Health.down().build()  

还可以添加一些异常信息：return Health.down().withDetail("reason", e.getMessage()).build();  
其中，.withDetail()支持多次调用  

## 保护Actuator端点

Spring Security可以保护安全相关的端点  

在SecurityConfig.java的第一个配置HttpSecurity的configure方法中，设置仅限ADMIN权限的用户访问/shutdown端点  
在第二个授权管理的configure方法中，添加角色为ADMIN和READER，以及密码  

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private ReaderRepository readerRepository;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/").access("hasRole('READER')")
        .antMatchers("/mgmt/**").access("hasRole('ADMIN')")
        .antMatchers("/**").permitAll()
      .and()
      .formLogin()
        .loginPage("/login")
        .failureUrl("/login?error=true");
  }
  
  @Override
  protected void configure(
              AuthenticationManagerBuilder auth) throws Exception {
    auth
      .userDetailsService(new UserDetailsService() {
        @Override
        public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
          UserDetails user = readerRepository.findOne(username);
          if (user != null) {
            return user;
          }
          throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
      })


      .and()
      .inMemoryAuthentication()

        .withUser("manager").password("s3cr3t").roles("ADMIN","READER");

  }
}
```

在application.properties配置文件中，添加如下配置，可以为所有端点添加路径前缀，已实现分组管理  

```text
management.context-path=/mgmt
```

然后，在访问端点时，如下所示，才会收到响应信息：

> /mgmt/metrics  
