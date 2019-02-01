package com.abel.quartz.config;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.lang.reflect.Method;


/**
 * Created by yangyibo on 2019/2/1.
 */
public class InvokingJobDetailFactory extends QuartzJobBean {

    /**
     * 计划任务所在类
     */
    private String targetObject;

    /**
     * 具体需要执行的计划任务
     */
    private String targetMethod;

    private ApplicationContext ctx;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Object obj = ctx.getBean(targetObject);
            Method m = null;
            try {
                m = obj.getClass().getMethod(targetMethod);
                //调用被代理对象的方法
                m.invoke(obj);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}
