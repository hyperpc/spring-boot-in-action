# 自定义配置

## Spring Boot CLI

> spring init -j 1.8 -dweb,data-jpa,h2,thymeleaf --build maven -a study -g org.myApp -n readlist -p jar -x myApp  

## 添加Security

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
```

## 配置文件application.properties

### 禁用模板缓存

```text
    spring.thymeleaf.cache=false
```

### 配置日志

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>
```

还需要修改配置文件application.properties  

```text
    logging.file=BookWorm.log
    logging.level.root=WARN
    logging.level.org.springframework.security=DEBUG
    logging.path=D:/path/myApp/src/logs
```

还可以在src/main/resources目录下创建logback.xml文件，以控制日志的配置
更多设置，请参考[Logback官方文档](https://logback.qos.ch/documentation.html)  

```xml
    <configuration>
        <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
            <encoder>
                <pattern>
                    %d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n
                </pattern>
            </encoder>
        </appender>
        <logger name="root" level="INFO"></logger>
        <root level="INFO">
            <appender-ref ref="STDOUT"></appender-ref>
        </root>
    </configuration>
```

如果针对不同运行时Profile使用不同配置，可以使用如下配置调用:

```text
    logging.config.classpath=logging-config-sde.xml
```

### 配置HTTPS

利用JDK的keytool工具创建密钥存储：
> keytool -keystore mykeys.jks -genkey -alias tomcat -keyalg RSA  
> 本例使用的密钥的密码为letmein  
> 将密钥文件放到程序根目录下：/myApp/mykeys.jks，与pom.xml同一目录  

配置HTTPS服务监听：

```text
    spring.thymeleaf.cache=false
    server.port=8443
    #server.ssl.key-store=file:///D:\path_to\mykeys.jks
    server.ssl.key-store=mykeys.jks
    server.ssl.key-store-password=letmein
    server.ssl.key-password=letmein
```

### 配置数据源

```text
spring.DataSource.url=jdbc:mysql://localhost/readlist
spring.DataSource.username=dbuser
spring.DataSource.password=dbpass
spring.DataSource.driver-class-name=com.mysql.cj.jdbc.Driver
// db connection pool
spring.DataSource.jndi-name=java:/comp/env/jdbc/readlistDS
```

### 配置参数associateId

```text
amazon.associateId=habuma-20
```

然后在引用的组件上添加以下注解，即可读取配置的参数值

```java
@Component
@ConfigurationProperties("amazon")
public class AmazonProperties {
    private String associateId;
    
    public void setAssociateId(String associateId){
        this.associateId = associateId;
    }

    public String getAssociateId(){
        return associateId;
    }
}
```

### 配置Profile

可以在application.properties同级目录下新建两个配置文件：application-development.properties和application-production.properties  
如下配置就是激活了development的配置文件，application.properties里面的其他配置如果未被覆盖，依然有效  

```text
spring.profiles.active:development
```

## 定制错误页面

为Thymeleaf船舰错误模板页error.html  
当错误发生时，Spring Boot会为错误视图提供如下信息：timestamp, status, error, exception, message, errors, trace, and path.  

```text
### Error
error.whitelabel.enabled=false
```

尚未解决的问题：

1. error页面的图片加载不到  
2. 样式文件也加载不到  

![未解决问题](/ch03_configuration/problem.png)

## Tomcat Native错误

由于Spring Boot集成的Tomcat的bin目录下缺失库文件：tcnative-1.dll  
可以从官网下载最新的库文件，放进Windows/System32目录下，即可解决  
