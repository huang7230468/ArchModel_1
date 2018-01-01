package com.ant.schedule.service.impl;

import com.ant.schedule.entity.Task;
import com.ant.schedule.selfannotation.Comment;
import com.ant.schedule.service.ITaskService;
import com.mchange.v1.util.ListUtils;
import javafx.application.Application;
import org.quartz.Scheduler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 任务方法类
 *
 * @author
 * @create 2018-01-01 22:03
 **/
public class TaskService implements ITaskService ,ApplicationContextAware {
    private ApplicationContext applicationContext ;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private Map<String,String>  cronDesc = new LinkedHashMap<String,String>();

    private List<Method> triggerList = new ArrayList<Method>();
    //保存所有任务列表
    private Map<String ,Task> allTask = new LinkedHashMap<String, Task>() ;

    private Scheduler scheduler ;
    Random random = new Random() ;
    private void addTolist(Task task, Object o) {
        this.allTask.put(task.getId() , task);
    }

    /**
     * 创建一个调度任务
     * @param method
     * @param cron
     * @return
     */
    private Task createTask(Method method, String cron) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Task task = new Task("" + (System.currentTimeMillis() + random.nextInt()));
        Class cls = method.getDeclaringClass() ;
        task.setGroup(cls.getName());
        //判断任务组是否存在描述
        if(cls.isAnnotationPresent(Comment.class)){
            Annotation comment = cls.getAnnotation(Comment.class) ;
            Method taskM = comment.getClass().getMethod("value",null) ;
            //Comment comment1 = taskM.getAnnotation(Comment.class) ;
            String groupDesc = taskM.invoke(comment,null).toString();
            task.setGroupDesc(groupDesc);
        }
        List<String> params = new ArrayList<String>();
        for(Class<?> parameter :  method.getParameterTypes()){
            params.add(parameter.getName()) ;
        }
        task.setTrigger(cls.getName()+"."+method.getName()+(params.size() == 0 ?"()" : "()"));
        if(!"".equals(cron)){
            task.setCron(cron);
            task.setCronDesc("无");
            task.setTriggerDesc("无");
        }else if(method.isAnnotationPresent(Scheduled.class)){
            Annotation Scheduled = cls.getAnnotation(Scheduled.class) ;
            Method taskM = Scheduled.getClass().getMethod("cron",null) ;
            String crons = taskM.invoke(Scheduled,null).toString();
            task.setTriggerDesc(cronDesc.get(crons));
            task.setCron(crons);
            task.setCronDesc(cronDesc.get(crons));
        }
        if(method.isAnnotationPresent(Comment.class)){
            Annotation comment = cls.getAnnotation(Comment.class) ;
            Method taskM = comment.getClass().getMethod("value",null) ;
            String groupDesc = taskM.invoke(comment,null).toString();
            task.setDesc(groupDesc);
        }
        return task ;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext ;
            scheduler = schedulerFactoryBean.getScheduler() ;

            for(String name : this.applicationContext.getBeanDefinitionNames()){
                Class<?> cls = this.applicationContext.getBean(name).getClass() ;
                for(Method method : cls.getDeclaredMethods()){
                    if(method.isAnnotationPresent(Scheduled.class)){

                        try {
                            Scheduled scheduled = method.getAnnotation(Scheduled.class) ;
                            triggerList.add(method) ;
                            Task task = createTask(method , scheduled.cron());
                            addTolist(task , null);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }

                    }
                }
            }
    }


}
