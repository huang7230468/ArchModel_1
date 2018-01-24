package com.ant.task;

import com.alibaba.dubbo.config.annotation.Service;
import com.ant.schedule.selfannotation.Comment;
import com.ant.schedule.selfannotation.TaskName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author
 * @create 2018-01-03 22:47
 **/
@Component
public class taskDemo {
    Logger log = LoggerFactory.getLogger(this.getClass());


    @Scheduled(cron = "0/10 * * * * ?")
    @Comment("输出时间")
    @TaskName("任务调用demo")
    public void taskA(){
        log.info("当天时间的时间戳"+System.currentTimeMillis());
    }
}
