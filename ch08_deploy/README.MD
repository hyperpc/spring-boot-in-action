# 部署

打包方式：

|部署方式|生产方式|目标环境|
|:---|:---|:---|
|Groovy源码|手写|Cloud Foundry及容器部署，比如Docker|
|可执行JAR|Maven、Gradle或Spring Boot CLI|云环境，包括Cloud Foundry和Heroku等PaaS平台，及容器部署，比如Docker|
|WAR|Maven或Gradle|Java应用服务器或云环境，比如Cloud Foundry|

## 部署WAR到Java应用服务器

### 构建WAR

pom.xml配置：  

```xml
    <packaging>war</packaging>
```

创建SpringBootServletInitializer的子类：  

```java
public class ReadingListServletInitializer 
       extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(
                                    SpringApplicationBuilder builder) {
    return builder.sources(ReadingListApplication.class);
  }
}
```

通过Gradle构建应用程序：  

> gradle build  

通过Maven构建应用程序：  

> mvn package  

在build/libs目录下生成对应的war文件，部署到webapps目录里，在tomcat运行时，会自动解压安装  

### 创建生产Profile

一种方式是，声明一个DataSource Bean，并注解生产环境为application.properties文件中配置的环境  
这里的DataSource类型，是org.apache.tomcat.jdbc.pool.DataSource，而不是其父类javax.sql.DataSource  

另一种更推荐的写法，是在application.properties文件中配置。如下是对postgresql数据库的一段配置：  

```text
spring.profiles=production
spring.datasource.url=jdbc:postgresql://localhost:5432/readinglist
spring.datasource.username=habuma
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.profiles.active=production
```

### 数据库迁移

[Flyway](https://flywaydb.org/)  
[Liquibase](https://www.liquibase.org/)  

Flyway脚本放在src/main/resources/db/migration下，脚本名称由大写“V”开头，紧跟版本号，然后两个下划线分隔符，最后是简短的自定义描述，如V1__initialize.sql，脚本内容见源码  

```xml
    <dependency>
        <groupId>org.flywayfb</groupId>
        <artifactId>flyway-core</artifactId>
    </dependency>
```

Liquibase与Flyway的sql脚本不同的是，它支持YAML格式、XML格式和JSON，支持不同的数据库平台，不受sql语法的局限  
其脚本放在src/main/resources/db/changelog下。这种方式下的所有更改，一般都放在一个文件里，以changeset的id属性标识更新的顺序  
然后在application.propertise文件里配置Liquibase要加载的文件，如：liquibase.change-log.classpath=/db/changelog/db.changelog-master.yaml  

```xml
    <dependency>
        <groupId>org.liquibase</groupId>
        <artifactId>liquibase-core</artifactId>
    </dependency>
```

更多使用方式，请参考对应的官方文档  

## 部署JAR到Cloud Foundry

Cloud Foundry既有开源版本，也有商业版本，是Pivotal的PaaS平台  
可以通过它提供的CF命令行工具进行推送发布(WAR，Jar，甚至未编译的Groovy脚本)  

> cf login -a https://api.run.pivotal.io  
> cf push readingList -p buid/libs/readingList.war  

## 部署JAR到Heroku

构建过程也是由Heroku执行  
Heroku通过git仓库的方式，实现自动构建和部署  
