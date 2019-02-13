package com.abel.quartz.config;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.zaxxer.hikari.HikariDataSource;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by yangyibo on 2019/1/16.
 */
@Configuration
public class QuartzConfig {

    /**
     * 1.通过name+group获取唯一的jobKey;2.通过groupname来获取其下的所有jobkey
     */
    final static String GROUP_NAME = "QuartzJobGroups";

    @Value("${quartz.scheduler.instanceName}")
    private String quartzInstanceName;

    @Value("${spring.datasource.driverClassName}")
    private String myDSDriver;

    @Value("${spring.datasource.url}")
    private String myDSUrl;

    @Value("${spring.datasource.username}")
    private String myDSUser;

    @Value("${spring.datasource.password}")
    private String myDSPassword;

    @Value("${org.quartz.dataSource.myDS.maxConnections}")
    private int myDSMaxConnections;

    /**
     * 设置属性
     *
     * @return
     * @throws IOException
     */
    private Properties quartzProperties() throws IOException {
        Properties prop = new Properties();
        // 调度标识名 集群中每一个实例都必须使用相同的名称
        prop.put("quartz.scheduler.instanceName", quartzInstanceName);
        // ID设置为自动获取 每一个必须不同
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        // 禁用quartz软件更新
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        prop.put("org.quartz.scheduler.jmx.export", "true");


        // 数据库代理类，一般org.quartz.impl.jdbcjobstore.StdJDBCDelegate可以满足大部分数据库
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        // 数据保存方式为数据库持久化
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        // 数据库别名 随便取
        prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
        //prop.put("org.quartz.jobStore.dataSource", "myDS");
        // 表的前缀，默认QRTZ_
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        // 是否加入集群
        prop.put("org.quartz.jobStore.isClustered", "true");

        // 调度实例失效的检查时间间隔
        prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        // 信息保存时间 ms 默认值60秒
        prop.put("org.quartz.jobStore.misfireThreshold", "120000");
        prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");
        prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS WHERE LOCK_NAME = ? FOR UPDATE");

        // 程池的实现类（一般使用SimpleThreadPool即可满足几乎所有用户的需求）
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        // 定线程数，至少为1（无默认值）(一般设置为1-100之间的整数合适)
        prop.put("org.quartz.threadPool.threadCount", "10");
        // 设置线程的优先级（最大为java.lang.Thread.MAX_PRIORITY 10，最小为Thread.MIN_PRIORITY 1，默认为5）
        prop.put("org.quartz.threadPool.threadPriority", "5");
        prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");

        prop.put("org.quartz.plugin.triggHistory.class", "org.quartz.plugins.history.LoggingJobHistoryPlugin");
        prop.put("org.quartz.plugin.shutdownhook.class", "org.quartz.plugins.management.ShutdownHookPlugin");
        prop.put("org.quartz.plugin.shutdownhook.cleanShutdown", "true");

        //#自定义连接池
        //org.quartz.dataSource.myDS.connectionProvider.class=com.poly.pay.schedule.DruidConnectionProvider

        return prop;
    }

    /**
     * 数据源
     *
     * @return
     * @throws PropertyVetoException
     */
    @Bean
    public HikariDataSource createDataSource() throws PropertyVetoException {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(myDSUrl);
        dataSource.setDriverClassName(myDSDriver);
        dataSource.setUsername(myDSUser);
        dataSource.setPassword(myDSPassword);
        dataSource.setMaximumPoolSize(myDSMaxConnections);
        return dataSource;
    }


    /**
     * 创建触发器工厂
     *
     * @param jobDetail
     * @param cronExpression
     * @return
     */
    private static CronTriggerFactoryBean cronTriggerFactoryBean(JobDetail jobDetail, String cronExpression) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression(cronExpression);
        return factoryBean;
    }


/****************************************************以下配置需要注意******************************************************/


    /**
     * 调度工厂
     * 此处配置需要调度的触发器 例如 executeJobTrigger
     *
     * @param executeJobTrigger
     * @return
     * @throws IOException
     * @throws PropertyVetoException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("executeJobTrigger") Trigger executeJobTrigger) throws IOException, PropertyVetoException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // this allows to update triggers in DB when updating settings in config file:
        //用于quartz集群,QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);
        //用于quartz集群,加载quartz数据源
        //factory.setDataSource(dataSource);
        //QuartzScheduler 延时启动，应用启动完10秒后 QuartzScheduler 再启动
        //factory.setStartupDelay(10);
        //用于quartz集群,加载quartz数据源配置
        factory.setAutoStartup(true);
        factory.setQuartzProperties(quartzProperties());
        factory.setApplicationContextSchedulerContextKey("applicationContext");
        factory.setDataSource(createDataSource());
        //注册触发器
        Trigger[] triggers = {executeJobTrigger};
        factory.setTriggers(triggers);

        return factory;
    }


    /**
     * 加载触发器
     *
     * 新建触发器进行job 的调度  例如 executeJobDetail
     * @param jobDetail
     * @return
     */
    @Bean(name = "executeJobTrigger")
    public CronTriggerFactoryBean executeJobTrigger(@Qualifier("executeJobDetail") JobDetail jobDetail) {
        //每天凌晨3点执行
        return cronTriggerFactoryBean(jobDetail, "0 1 0 * * ? ");
    }


    /**
     * 加载job
     *
     * 新建job 类用来代理
     *
     *
     * @return
     */
    @Bean
    public JobDetailFactoryBean executeJobDetail() {
        return createJobDetail(InvokingJobDetailFactory.class, GROUP_NAME, "executeJob");
    }


    /**
     * 执行规则job工厂
     *
     * 配置job 类中需要定时执行的 方法  execute
     * @param jobClass
     * @param groupName
     * @param targetObject
     * @return
     */
    private static JobDetailFactoryBean createJobDetail(Class<? extends Job> jobClass,
                                                        String groupName,
                                                        String targetObject) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);
        factoryBean.setGroup(groupName);
        Map<String, String> map = new HashMap<>();
        map.put("targetMethod", "execute");
        map.put("targetObject", targetObject);
        factoryBean.setJobDataAsMap(map);
        return factoryBean;
    }

}