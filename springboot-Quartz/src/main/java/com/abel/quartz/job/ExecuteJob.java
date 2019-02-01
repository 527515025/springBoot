package com.abel.quartz.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by yangyibo on 2019/2/1.
 */
@Service
public class ExecuteJob {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteJob.class);

    /**
     * 方法名在quartz定义
     */
    public void execute() {
        System.out.println("定时任务执行了。。。。。");

    }
}
