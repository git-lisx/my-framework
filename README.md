# Spring等框架集合

项目只需依赖jdk环境即可，无需第三方依赖

## 项目结构

- my-springframework
    - log # 日志
    - servlet-api # servlet接口
    - spring-boot-starter-web # springboot web版
    - spring-framework # 框架层
    - tomcat # tomcat实现
    - web-app-tomcat # 业务层war包
    - web-app-springboot # 业务层jar

### spring-framework

当前项目以实现的功能包括：

- IOC和DI
- AOP
    - 模拟实现事务
    - 动态代理
        - 基于接口实现，实现jdk动态代理
        - 基于子类实现（由于需要引入第三方依赖，因此，已注释掉），移除cglib（该项目作者已不维护，Spring5之后也修改为基于bytebuddy实现）使用基于bytebuddy实现
- MVC

### tomcat

使用socket技术实现，遵循http超文本协议规范，引入自己定义的servlet-api，已实现的功能：

- 处理http请求
- session会话
- session过期清理（懒惰清理+定期清理策略，提升性能和降低内存压力）
- 多servlet容器支持
- 超时设置
- IO多路复用（后续计划实现）

### spring-boot-starter-web

已实现的功能：

- 无需外部服务器（例如：Tomcat），使用内置自己编写的tomcat实现
- 配置文件支持（application.yaml）

## 启动

web-app在idea中，将web-app项目打包成web-app.war，然后使用Tomcat8.5启动

web-app-springboot直接执行WebApplication.java类即可

## 使用

引入依赖

```xml

<dependency>
    <groupId>cn.xian</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>1.0</version>
</dependency>
```
