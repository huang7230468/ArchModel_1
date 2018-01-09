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

           
                
                    
          
