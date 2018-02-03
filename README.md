# ArchModel_1
架构版本 1.0
本项目是关联简书中 作者  『骑着蚂蚁上因特奈特』中『架构源于实践(1)』<br />
    地址 ：http://www.jianshu.com/p/82fd9728eb68<br />
1、项目检出到idea中运行<br />
2、配置数据库<br />
    此处用的是oracle <br />
    到sqlplus中直接执行脚本中的createuser.sql文件，创建表空间及用户，系统中配置是 db/123123<br />
    配置好后，可通过plsql登录查看，通过需要执行 脚本中 createTables.sql,创建表<br />
3、修改配置文件<br />
    数据库配置 ： dubbo-service 下的 Resources下jdbc.properties<br /> 
    zookeeper配置 ：dubbo-service 下的 Resources下 dubbo_provider.xml<br />
                    dubbo-web 下的 Resources下 dubbo_config.xml<br />
4、到此，项目基本配置好，可以放进tomcat中，启动<br />
5、访问 http://loaclhost:8080/dubbo-web/index?name=1 <br />       
6、新增dubbo-schedule模块，quartz任务自动化，不断完善
## 知识点
 - SpringMvc 中入参注解 @RequestParam是为了解决 consumes = "application/x-www-form-urlencoded"的情况，客户端发起请求必须是这种content-type
    其次 入参注解 @RequestBody是为了解决content-type为"application/json" 这种客户端请求
    
 - 跨域实验
   方法上增加@CrossOrigin即可
    - 第一组实验
       接口中不指定  method 
         客户端用 post  请求   403 
         客户端用 get  请求   200 
    - 第二组实验
       接口中指定  method = post
        客户端用 post  请求   200
        客户端用 get  请求   405 Method Not Allowed
    - 第三组实验
       接口中指定  method = get
        客户端用 post  请求   405 Method Not Allowed
        客户端用 get  请求   200  
`
实验结论 ：针对第一组实验为什么只有get请求能成功，读源码得出的结论，因为内部实现，当没有指定method时，默认将get请求作为跨域允许的方法
  `      

           
                
                    
          
