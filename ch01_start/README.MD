# 入门

## Spring Boot精要

- 自动配置
- 起步依赖
- 命令行界面
- Actuator

## 安装Maven

[Release](https://maven.apache.org/download.cgi)  

新建repository目录: \apache-maven-3.8.5\repository  

更新配置\apache-maven-3.8.5\conf\setting.xml：

```xml
    <localRepository>D:\Program\apache-maven-3.8.5\repository</localRepository>
    ...
    <mirror>
      <id>aliyunmaven</id>
      <mirrorOf>central</mirrorOf>
      <name>Aliyun maven</name>
      <url>https://maven.aliyun.com/repository/central</url>
    </mirror>
    ...
    <profile>
      <id>jdk-1.8</id>
      <activation>
        <jdk>1.8</jdk>
        <activeByDefault>true</activeByDefault>
      </activation>

      <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
      </properties>
    </profile>
```

设置环境变量：  

> MAVEN_HOME: D:\apache-maven-3.8.5  
> PATH: %MAVEN_HOME%\bin  
> mvn -v  

## 安装 Spring Boot CLI

[Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started.html#getting-started.introducing-spring-boot)  
[Releases](https://repo.spring.io/ui/native/release/org/springframework/boot/spring-boot-cli/)  

## 命令

> 将spring-boot/bin目录，设置到系统环境变量PATH  
> SPRING_HOME: D:\spring-2.6.6  
> PATH: %SPRING_HOME%\bin
> spring --version  
> spring help  
> spring help init  
> spring init --list  
> spring init  
> spring init -dweb,jpa,security  
> spring init -dweb,jpa,security --build gradle  
> spring init -dweb,jpa,security --build gradle -p war  
> spring init -dweb,jpa,security --build gradle -p war myApp  
> spring init -dweb,jpa,security --build gradle -p jar x
