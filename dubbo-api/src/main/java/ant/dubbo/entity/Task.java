package ant.dubbo.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 任务实体类
 *
 * @author
 * @create 2018-01-01 21:44
 **/

public class Task implements Serializable{

    private String id ;                 //任务ID,
    private String parentid ;           //父级任务ID
    private String name ;               //任务名称
    private String desc ;               //任务描述
    private int planExeTimes = 0 ;      //计划执行次数，默认为0
    private String group ;              //任务组名称 触发器的类名
    private String groupDesc ;          //任务组描述
    private String cron ;               //任务表达式
    private String cronDesc ;           //任务表达式描述
    private String trigger ;            //触发器   实现方法名
    private String triggerDesc ;        //触发器描述
    private int execute = 0 ;           //任务被执行次数
    private Long lastExecTime = 0l ;    //最后一次开始执行时间
    private Long lastFinishTime = 0l ;  //最后一次执行完成时间
    private int state =  1 ;            //任务状态 0 ：禁用、1：启动、2：删除
    private int deply = 0 ;             //延时启动 默认为0 ，表示不延时启动

    public Task(){}

    public Task(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPlanExeTimes() {
        return planExeTimes;
    }

    public void setPlanExeTimes(int planExeTimes) {
        this.planExeTimes = planExeTimes;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getCronDesc() {
        return cronDesc;
    }

    public void setCronDesc(String cronDesc) {
        this.cronDesc = cronDesc;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getTriggerDesc() {
        return triggerDesc;
    }

    public void setTriggerDesc(String triggerDesc) {
        this.triggerDesc = triggerDesc;
    }

    public int getExecute() {
        return execute;
    }

    public void setExecute(int execute) {
        this.execute = execute;
    }

    public Long getLastExecTime() {
        return lastExecTime;
    }

    public void setLastExecTime(Long lastExecTime) {
        this.lastExecTime = lastExecTime;
    }

    public Long getLastFinishTime() {
        return lastFinishTime;
    }

    public void setLastFinishTime(Long lastFinishTime) {
        this.lastFinishTime = lastFinishTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDeply() {
        return deply;
    }

    public void setDeply(int deply) {
        this.deply = deply;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", parentid='" + parentid + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", planExeTimes=" + planExeTimes +
                ", group='" + group + '\'' +
                ", groupDesc='" + groupDesc + '\'' +
                ", cron='" + cron + '\'' +
                ", cronDesc='" + cronDesc + '\'' +
                ", trigger='" + trigger + '\'' +
                ", triggerDesc='" + triggerDesc + '\'' +
                ", execute=" + execute +
                ", lastExecTime=" + lastExecTime +
                ", lastFinishTime=" + lastFinishTime +
                ", state=" + state +
                ", deply=" + deply +
                '}';
    }
}
