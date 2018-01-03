package com.ant.schedule.proxy;

import com.ant.schedule.entity.Entry;
import com.ant.schedule.entity.Task;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
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
    public static final String DATA_TASK_KEY = "task" ;

    private ThreadLocal<Entry> local = new ThreadLocal<Entry>();
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            local.set(new Entry());
        JobDataMap data = jobExecutionContext.getTrigger().getJobDataMap() ;
        Object target = data.get(DATA_TARGET_KEY);
        Method method = (Method) data.get(DATA_TRIGGER_KEY);
        Object[]  params = (Object[]) data.get(DATA_TRIGGER_PARAMS_KEY);
        Task task = (Task) data.get(DATA_TASK_KEY) ;
        task.setExecute(task.getExecute() + 1);
        //local.get().start = System.currentTimeMillis() ;
        long start = System.currentTimeMillis() ;
        try {
            method.invoke(target,params) ;
            long end = System.currentTimeMillis() ;
            task.setLastExecTime(start);
            task.setLastFinishTime(end);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
