package cn.abel.rest.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import cn.abel.rest.constants.Constants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis缓存配置
 *
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);
    /**
     * serializer
     */
    private static final StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();

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

    /**
     * 声明缓存管理器
     */
    @Override
    @Bean
    public CacheManager cacheManager() {
        //1. entryTtl: 定义默认的cache time-to-live.
        //2. disableCachingNullValues: 禁止缓存Null对象. 视需求而定.
        //3. computePrefixWith: 此处定义了cache key的前缀, 避免公司不同项目之间的key名称冲突.
        //4. serializeKeysWith, serializeValuesWith: 定义key和value的序列化协议, 同时的hash key和hash value也被定义.

        // 缓存配置
        RedisCacheConfiguration cacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues().computePrefixWith(cacheName -> Constants.REDIS_KEY_PRE.concat(":").concat(cacheName).concat(":")).entryTtl(Duration.ofDays(Constants.REDIS_TIMEOUT));

        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(2);
        //redisCacheConfigurationMap.put(Constants.CACHE_NAME_PERMISSION, permissionCacheConfiguration);

        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory());

        RedisSerializationContext.SerializationPair<String> valuePair =
                RedisSerializationContext.SerializationPair.fromSerializer(STRING_SERIALIZER);
        //RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer(new ObjectMapper()));
        RedisCacheConfiguration defaultCacheConfig =
                RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(valuePair);
        //设置默认过期时间是1天
        defaultCacheConfig.entryTtl(Duration.ofDays(Constants.REDIS_TIMEOUT));

        //初始化RedisCacheManager
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig,
                redisCacheConfigurationMap);
        cacheManager.afterPropertiesSet();
        logger.info("RedisCacheManager config success");

        return cacheManager;
    }

    @Bean(name = "redisTemplate")
    @Primary
    public RedisTemplate<String, Object> redisTemplate() {
        return getTemplate(redisConnectionFactory());
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }

    private RedisConnectionFactory redisConnectionFactory() {
        return connectionFactory(maxActive, maxIdle, minIdle, maxWait, host, password, timeout, port, database);
    }

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
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWait);
        LettuceClientConfiguration lettucePoolingConfig = LettucePoolingClientConfiguration.builder()
                //.commandTimeout(Duration.ofSeconds(15))
                .poolConfig(poolConfig).shutdownTimeout(Duration.ofMillis(timeout)).build();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration,
                lettucePoolingConfig);
        connectionFactory.afterPropertiesSet();

        return connectionFactory;
    }

    private RedisTemplate<String, Object> getTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        template.setKeySerializer(STRING_SERIALIZER);
        template.setValueSerializer(jackson2JsonRedisSerializer());
        //不能用stringSerializer，有地方使用ShiroUser做key，不能转换
        //template.setHashKeySerializer(STRING_SERIALIZER);
        //template.setHashKeySerializer(jackson2JsonRedisSerializer());

        template.afterPropertiesSet();

        return template;
    }

    private RedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

}