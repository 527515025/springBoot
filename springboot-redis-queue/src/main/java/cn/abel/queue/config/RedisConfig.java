package cn.abel.queue.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author yyb
 * @time 2019/8/13
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    @Value("${spring.redis.database}")
    private Integer database;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private Integer maxActive;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Integer maxWait;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer minIdle;
    @Value("${spring.redis.lettuce.shutdown-timeout}")
    private Integer timeout;

    /**
     * 在使用@Cacheable时，如果不指定key，则使用这个默认的key生成器生成的key
     *
     * @return
     */
    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate() {
        return getTemplate(redisConnectionFactory());
    }

    private RedisConnectionFactory redisConnectionFactory() {
        return connectionFactory(maxActive, maxIdle, minIdle, maxWait, host, password, timeout, port, database);
    }


    /**
     * 创建连接
     *
     * @param maxActive
     * @param maxIdle
     * @param minIdle
     * @param maxWait
     * @param host
     * @param password
     * @param timeout
     * @param port
     * @param database
     * @return
     */
    private RedisConnectionFactory connectionFactory(Integer maxActive,
                                                     Integer maxIdle,
                                                     Integer minIdle,
                                                     Integer maxWait,
                                                     String host,
                                                     String password,
                                                     Integer timeout,
                                                     Integer port,
                                                     Integer database) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        LettuceClientConfiguration lettucePoolingConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig).shutdownTimeout(Duration.ofMillis(timeout)).build();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration,
                lettucePoolingConfig);
        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }

    /**
     * 创建 RedisTemplate 连接类型，此处为hash
     *
     * @param factory
     * @return
     */
    private RedisTemplate<Object, Object> getTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setValueSerializer(jackson2JsonRedisSerializer(new ObjectMapper()));
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jackson2JsonRedisSerializer(new ObjectMapper()));

        template.afterPropertiesSet();
        return template;
    }

    /**
     * 对value 进行序列化
     *
     * @param objectMapper
     * @return
     */
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer(ObjectMapper objectMapper) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        return jackson2JsonRedisSerializer;
    }
}
