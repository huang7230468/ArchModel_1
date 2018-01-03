package com.ant.task;

import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;


/**
 * @author
 * @create 2018-01-03 22:47
 **/
@Service
public class taskDemo {
    Logger log = LoggerFactory.getLogger(this.getClass());
    @Scheduled(cron = "0/10 * * * * ?")
    public void taskA(){
        log.info("当天时间的时间戳"+System.currentTimeMillis());
    }
}
