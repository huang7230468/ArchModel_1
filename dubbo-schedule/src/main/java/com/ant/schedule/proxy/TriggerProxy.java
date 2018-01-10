package com.ant.schedule.proxy;

import ant.dubbo.entity.Entry;
import ant.dubbo.entity.Task;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author
 * @create 2018-01-02 21:46
 **/
public class TriggerProxy implements Job {
    public static final String DATA_TARGET_KEY = "target" ;
    public static final String DATA_TRIGGER_KEY = "trigger" ;
    public static final String DATA_TRIGGER_PARAMS_KEY = "trigger_params" ;
    public static final String DATA_TRIGGER_PARAMS_CLASSTYPE = "trigger_classtype" ;
    public static final String DATA_TASK_KEY = "task" ;
    public static final String DATA_GROUP_NAME = "groupName" ;


    private ThreadLocal<Entry> local = new ThreadLocal<Entry>();
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            local.set(new Entry());
        System.out.println("execute1--------------------------");
        JobDataMap data = jobExecutionContext.getTrigger().getJobDataMap() ;
        Object target = data.get(DATA_TARGET_KEY);
        String methodName = (String)data.get(DATA_TRIGGER_KEY);
        String groupName = (String)data.get(DATA_GROUP_NAME);
        Class<?>[]  paramsClassType = (Class<?>[]) data.get(DATA_TRIGGER_PARAMS_CLASSTYPE);
        Object[]  params = (Object[]) data.get(DATA_TRIGGER_PARAMS_KEY);
        Task task = (Task) data.get(DATA_TASK_KEY) ;
        task.setExecute(task.getExecute() + 1);
        local.get().start = System.currentTimeMillis() ;
        try {
            Class<?> cls = Class.forName(groupName);
            Method method = cls.getMethod(methodName, paramsClassType);
            /**
             * 类名 + 入参
             */
            method.invoke(target,params) ;
            local.get().end = System.currentTimeMillis() ;
            task.setLastExecTime( local.get().start);
            task.setLastFinishTime( local.get().end);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
