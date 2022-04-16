# Groovy

## 设置CLI项目结构

> mkdir readinglist  
> cd readinglist  
> mkdir static  
> mkdir templates  
> copy static/style.css and templates/readingList.html to new created dir  

## 通过Groovy消除代码噪声

> cd.>Book.groovy // create Book entity  
> cd.>ReadingListRepository.groovy  
> cd.>JdbcReadingListRepository.groovy  
> cd.>schema.sql  
> cd.>Grabs.groovy  
> spring run *.groovy  

注意，schema.sql里面的SQL语句一定要以";"结束，否则会有语法错误  
Grabs.groovy文件的Grab注解，用来引入CLI无法自主解析的依赖，或者统一引入必须的依赖  

## 获取依赖

1. Grab显示声明依赖
2. GrabMetadata覆盖默认依赖版本  
3. GrabResolver获取额外仓库的依赖  

```groovy
// 声明对h2数据库依赖的几种方式  
@Grab("h2")  
@Grab("com.h2database:h2")  
@Grab("com.h2database:h2:1.4.190")  
@Grab(group="com.h2database", module="h2", version="1.4.190")  

//从Maven仓库的com/myorg目录下加载custom-versions.properties文件  
//(该文件指定的依赖，应该包含GroupID和ModuleID。如：com.h2database:h2=1.4.186)  
@GrabMetadata("com.myorg:custom-versions:1.0.0")  

//使用SpringIO平台定义的版本，取代CLI的默认版本  
@GrabMetadata('io.spring.platform:platform-versions:1.0.4.RELEASE')  

//从JBoss仓库加载最新的Hibernate版本  
@GrabResolver(name='jboss', root='https://repository.jboss.org/nexus/content/groups/public-jboss')  
```

## CLI测试

> mkdir tests  
> cd.>ReadingListControllerTest.groovy  
> spring run tests/ReadingListControllerTest.groovy  
> cd.>ReadingListControllerSpec.groovy  
> spring run tests/ReadingListControllerSpec.groovy  

## 部署

> spring jar ReadingList.jar .  
