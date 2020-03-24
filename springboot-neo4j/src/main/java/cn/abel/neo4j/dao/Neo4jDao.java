package cn.abel.neo4j.dao;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Properties;

/**
 * @author yyb
 * @time 2020/3/23
 */
@Repository
public class Neo4jDao {
    private static final Logger logger = LoggerFactory.getLogger(Neo4jDao.class);
    private static final String packages = "cn.abel.neo4j.bean";

    private SessionFactory sessionFactory = null;

    @Value("${neo4j.url}")
    private String boltUri;
    @Value("${neo4j.username}")
    private String username;
    @Value("${neo4j.password}")
    private String password;

    /**
     * 打开一个到neo4j的连接会话。
     */
    public Neo4jSession open() {
        SessionFactory factory = getSessionFactory();
        Neo4jSession session = new Neo4jSession(factory);
        return session;
    }

    private synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            ConfigurationSource props = () -> {
                Properties properties = new Properties();
                properties.setProperty("URI", boltUri);
                properties.setProperty("username", username);
                properties.setProperty("password", password);
                return properties;
            };
            Configuration configuration = new Configuration.Builder(props).build();
            sessionFactory = new SessionFactory(configuration, packages);
        }
        return sessionFactory;
    }
}

