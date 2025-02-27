# Spring等框架集合

项目只需依赖jdk环境即可，无需第三方依赖

## 项目结构

- my-framework
    - log # 日志
    - servlet-api # servlet接口
    - spring-boot-starter-web # springboot web版
    - spring-framework # 框架层
    - tomcat # tomcat实现
    - web-app-tomcat # 业务层war包
    - web-app-springboot # 业务层jar

### Spring-Framework

当前项目以实现的功能包括：

- IOC和DI
- AOP
    - 模拟实现事务
    - 动态代理
        - 基于接口实现，实现jdk动态代理
        - 基于子类实现（由于需要引入第三方依赖，因此，已注释掉），移除cglib（该项目作者已不维护，Spring5之后也修改为基于bytebuddy实现）使用基于bytebuddy实现
- MVC

### Tomcat

使用socket技术实现，遵循http超文本协议规范，引入自己定义的servlet-api，已实现的功能：

- 处理http请求
- session会话
- session过期清理（懒惰清理+定期清理策略，提升性能和降低内存压力）
- 多servlet容器支持
- 超时设置
- IO多路复用（后续计划实现）



常见的服务器中间件

- Tomcat
- Jetty
- 宝蓝德
- Uvicorn 
- Nginx



首先回顾下计算机网络的五(七)层协议：物理层、数据链路层、网络层、传输层、(会话层、表示层)和应用层。那么从协议上来讲：

- TCP是传输层协议，主要解决数据如何在网络中传输
- HTTP 是应用层协议，主要解决如何包装数据（文本信息），是建立在tcp协议之上的应用。TCP协议是以二进制数据流的形式解决传输层的事儿，但对上层的应用开发极不友好，所以面向应用层的开发又产生了HTTP协议。



Tomcat和Servlet的关系

- Servlet 是规范，Tomcat 是实现
- Tomcat是一个免费的开放源代码的Servlet容器



### spring-boot-starter-web

已实现的功能：

- 无需外部服务器（例如：Tomcat），使用内置自己编写的tomcat实现
- 配置文件支持（application.yaml）

## 启动

- web-app-tomcat在idea中，将web-app项目打包成web-app.war，然后使用Tomcat8.5启动

- web-app-springboot直接执行WebApplication.java类即可

## 使用

引入依赖

```xml
<!--引入依赖-->
<dependency>
    <groupId>cn.xian</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <version>1.0</version>
</dependency>
```
