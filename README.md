项目环境要求：   
JDK 1.8+   
Mysql 5.7+  

项目部署：   
1 、初始化数据库：在mysql中执行doc目录下的[blog.sql文件](./doc/blog.sql) 
2、配置application的配置选项(见下表)   
3、将resources文件夹设置为source root.    
4、运行blogApiApplication文件的函数main()

项目说明  
项目架构：SpringBoot+MybatisPlus   
项目自定义配置：application.properties
        
配置项       | 说明     |
|:-------- |:-------- |
|server.port                        | 项目访问端口        |
|spring.datasource.username         | 数据库帐户          |
|spring.datasource.password         | 数据库密码          |
|upload.dir                         | 文件上传目录        |
