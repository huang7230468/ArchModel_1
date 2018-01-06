package com.ant.schedule.service.impl;

import ant.dubbo.api.taskTimer.ITaskService;
import ant.dubbo.dto.ResultMsg;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.ant.schedule.entity.Task;
import com.ant.schedule.proxy.TriggerProxy;
import com.ant.schedule.selfannotation.Comment;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.calendar.AnnualCalendar;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.*;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 任务方法类
 *
 * @author
 * @create 2018-01-01 22:03
 **/
@Component
public class TaskService implements ITaskService, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    private Map<String, String> cronDesc = new LinkedHashMap<String, String>();

    private List<Method> triggerList = new ArrayList<Method>();
    //保存所有任务列表
    private Map<String, Task> allTask = new LinkedHashMap<String, Task>();
    private final  static  String regex = "(.+)\\((.*)\\)";

    private Scheduler scheduler;
    Random random = new Random();
    private static  Map<String, Class<?>> classTypes = new HashMap<String, Class<?>>(){
        {
            put("boolean",boolean.class);
            put("byte",byte.class);
            put("short",short.class);
            put("int",int.class);
            put("long",long.class);
            put("float",float.class);
            put("double",double.class);
            put("char",char.class);
            put("java.lang.Boolean",Boolean.class);
            put("java.lang.Byte",Byte.class);
            put("java.lang.Short",Short.class);
            put("java.lang.Integer",Integer.class);
            put("java.lang.Long",Long.class);
            put("java.lang.Float",Float.class);
            put("java.lang.Double",Double.class);
            put("java.lang.Character",Character.class);
        }
    };




    private ResultMsg<?> addTolist(Task task, Object o) throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, NoSuchMethodException, SchedulerException, ParseException {
        if (task.getGroup() == null || task.getGroup().trim().length() == 0) {
            return null;
        }
        Class<?> cls = Class.forName(task.getGroup());

        Object target = null;
        try {
            target = applicationContext.getBean(cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (target == null) {
            target = cls.newInstance();
        }
        String triggerName = task.getTrigger().replaceAll(task.getGroup() + ".", "");
        String methodName = triggerName;
        List<Class<?>> classList = new ArrayList<Class<?>>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(triggerName);
        while (m.find()) {
            methodName = m.group(1);
            String paramStr = m.group(2);
            String[] paramNames = paramStr.split("\\,");
            for (String str : paramNames) {
                if ("".equals(str)) {
                    continue;
                }
                if (classTypes.containsKey(str)) {
                    classList.add(classTypes.get(str));
                } else {
                    classList.add(Class.forName(str));
                }
            }
        }
        Class<?>[] param = new Class<?>[classList.size()];
        String taskId = task.getId();
        //方法参数
        //若任务的方法有入参时，则必须重新实现下这个地方，包括如何接收前台传的入参
        Object[] proxyParam = null ;
      /*  Calendar calendar = GregorianCalendar.getInstance() ;
        AnnualCalendar annualCalendar = new AnnualCalendar() ;
        annualCalendar.setDayExcluded(calendar,true);
        scheduler.addCalendar("now",annualCalendar,true,true);*/

        JobDetailImpl jobDetail = new JobDetailImpl(taskId, task.getGroup(), TriggerProxy.class);
        CronTriggerImpl cronTrigger = new CronTriggerImpl(taskId, task.getTrigger(),task.getCron());
        cronTrigger.getJobDataMap().put(TriggerProxy.DATA_TARGET_KEY, target);
        cronTrigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_KEY, methodName);
        cronTrigger.getJobDataMap().put(TriggerProxy.DATA_TASK_KEY, task);
        cronTrigger.getJobDataMap().put(TriggerProxy.DATA_GROUP_NAME, task.getGroup());
        cronTrigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_PARAMS_KEY, proxyParam);
        cronTrigger.getJobDataMap().put(TriggerProxy.DATA_TRIGGER_PARAMS_CLASSTYPE, param);
        //cronTrigger.setCalendarName("now");

        scheduler.scheduleJob(jobDetail, cronTrigger);
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
        if (!allTask.containsKey(taskId)) {
            this.allTask.put(taskId, task);
        }
        return new ResultMsg<Object>(Boolean.TRUE, taskId);
    }

    /**
     * @param taskName      任务名称
     * @param taskClassName 任务类名
     * @param tiggerName    触发器名（任务方法名）
     * @param cron          任务cron表达式
     * @return
     */
    public ResultMsg<?> createTask(String taskName, String taskClassName, String tiggerName, String cron) {
        return createTask(taskName, null, taskClassName, null, tiggerName, cron);
    }

    /**
     * @param taskName        任务名称
     * @param taskGroupName   任务组名称
     * @param taskClassName   任务类名
     * @param tiggerGroupName 触发器组名
     * @param triggerName     触发器名（任务方法名）
     * @param cron            任务cron表达式
     * @return
     */
    public ResultMsg<?> createTask(String taskName, String taskGroupName, String taskClassName, String tiggerGroupName, String triggerName, String cron) {

        try {
            Class<?> cls = Class.forName(taskClassName);
            //todo
            //这个地方如果任务有参数则需要定义getMethod入参
            Method method = cls.getMethod(triggerName,String.class);
            Task task = createTask(method, null);
            task.setName(taskName);
            if (taskGroupName != null) {
                task.setGroup(taskGroupName);
            }
            task.setCron(cron);
            return addTolist(task, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultMsg<Object>(Boolean.FALSE, "失败");
        }

    }

    /**
     * 创建一个调度任务
     *
     * @param method
     * @param cron
     * @return
     */
    private Task createTask(Method method, String cron) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Task task = new Task("" + (System.currentTimeMillis() + random.nextInt()));
        Class cls = method.getDeclaringClass();
        task.setGroup(cls.getName());
        //判断任务组是否存在描述
        if (cls.isAnnotationPresent(Comment.class)) {
            Annotation comment = cls.getAnnotation(Comment.class);
            Method taskM = comment.getClass().getMethod("value", null);
            //Comment comment1 = taskM.getAnnotation(Comment.class) ;
            String groupDesc = taskM.invoke(comment, null).toString();
            task.setGroupDesc(groupDesc);
        }
        List<String> params = new ArrayList<String>();
        for (Class<?> parameter : method.getParameterTypes()) {
            params.add(parameter.getName());
        }
        task.setTrigger(cls.getName() + "." + method.getName() + (params.size() == 0 ? "()" : ("("+StringUtils.join(params,",")+")")));
        if (!"".equals(cron)) {
            task.setCron(cron);
            task.setCronDesc("无");
            task.setTriggerDesc("无");
        } else if (method.isAnnotationPresent(Scheduled.class)) {
            Annotation Scheduled = cls.getAnnotation(Scheduled.class);
            Method taskM = Scheduled.getClass().getMethod("cron", null);
            String crons = taskM.invoke(Scheduled, null).toString();
            task.setTriggerDesc(cronDesc.get(crons));
            task.setCron(crons);
            task.setCronDesc(cronDesc.get(crons));
        }
        if (method.isAnnotationPresent(Comment.class)) {
            Annotation comment = cls.getAnnotation(Comment.class);
            Method taskM = comment.getClass().getMethod("value", null);
            String groupDesc = taskM.invoke(comment, null).toString();
            task.setDesc(groupDesc);
        }
        return task;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("启动监控任务");
        this.applicationContext = applicationContext;
        scheduler = schedulerFactoryBean.getScheduler();

        for (String name : this.applicationContext.getBeanDefinitionNames()) {
            Class<?> cls = this.applicationContext.getBean(name).getClass();
            for (Method method : cls.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Scheduled.class)) {

                    try {
                        Scheduled scheduled = method.getAnnotation(Scheduled.class);
                        triggerList.add(method);
                        Task task = createTask(method, scheduled.cron());
                        addTolist(task, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }

                }
            }
        }
    }


}
