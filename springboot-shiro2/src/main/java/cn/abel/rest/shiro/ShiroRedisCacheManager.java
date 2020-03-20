package cn.abel.rest.shiro;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.abel.rest.constants.Constants;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 实现Shiro的缓存管理器CacheManger接口，将Spring应用缓存管理器注入shiro缓存管理器，这样shiro的缓存都由Spring处理
 *
 * @date 2019/02/21 18:12
 */
@Component
public class ShiroRedisCacheManager implements CacheManager {

    private final static String CACHE_KEY_PREFIX = Constants.REDIS_KEY_PRE + ":shiro:";

    @Autowired
    private ShiroProperty shiroProperty;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /** 超时时间 */
    private long expireTime;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if ("passwordRetryCache".equals(name)) {
            expireTime = shiroProperty.getShiroRetryExpireTimeRedis();
        } else {
            expireTime = shiroProperty.getShiroAuthorizationExpireTimeRedis();
        }
        return new ShiroRedisCache<K, V>(CACHE_KEY_PREFIX + name, expireTime);
    }

    /**
     * 为shiro量身定做的一个redis cache,为Authorization cache做了特别优化
     */
    public class ShiroRedisCache<K, V> implements Cache<K, V> {

        private String cacheKey;

        // 缓存的超时时间，单位为s
        private long expireTime = 3600;

        public ShiroRedisCache(String cacheKey, long expireTime) {
            this.cacheKey = cacheKey;
            this.expireTime = expireTime;
        }

        @Override
        public V get(K key) throws CacheException {
            BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
            Object k = hashKey(key);
            return hash.get(k);
        }

        @Override
        public V put(K key, V value) throws CacheException {
            BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
            //超时时间
            Object k = hashKey(key);
            hash.put((K) k, value);
            hash.expire(expireTime, TimeUnit.SECONDS);
            return value;
        }

        @Override
        public V remove(K key) throws CacheException {
            BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);

            V value = null;
            try {
                Object k = hashKey(key);
                value = hash.get(k);
                hash.delete(k);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return value;
        }

        @Override
        public void clear() throws CacheException {
            redisTemplate.delete(cacheKey);
        }

        @Override
        public int size() {
            BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
            return hash.size().intValue();
        }

        @Override
        public Set<K> keys() {
            BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
            return hash.keys();
        }

        @Override
        public Collection<V> values() {
            BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
            return hash.values();
        }

        protected Object hashKey(K key) {
            //此处很重要,如果key是登录凭证,那么这是访问用户的授权缓存;将登录凭证转为user对象,返回user的id属性做为hash key,否则会以user对象做为hash key,这样就不好清除指定用户的缓存了
            if (key instanceof PrincipalCollection) {
                PrincipalCollection pc = (PrincipalCollection) key;
                ShiroUser user = (ShiroUser) pc.getPrimaryPrincipal();
                return user.getUserId();
            }
            return key;
        }
    }
}