package cn.abel.neo4j.dao;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * @author yyb
 * @time 2020/3/23
 */
public class Neo4jSession {
    private static final Logger logger = LoggerFactory.getLogger(Neo4jSession.class);
    private Session session;
    private static final int DEPTH_ENTITY = 1;

    public Neo4jSession(SessionFactory factory) {
        session = factory.openSession();
    }

    /**
     * 执行cypher命令。
     * 增、删、改、查的 cypher 命令都可以用此方法操作
     *
     * @param cypher
     * @return
     */
    public Iterator<Map<String, Object>> exec(String cypher) {
        long time = System.currentTimeMillis();
        Iterator<Map<String, Object>> it = session.query(cypher, Collections.EMPTY_MAP).iterator();
        logger.info("ExecCypher {} cost:{}ms", cypher, System.currentTimeMillis() - time);
        return it;
    }


    /**
     * 存储对象
     *
     * @param o
     * @return
     */
    public void execSave(Object o) {
        long time = System.currentTimeMillis();
        session.clear();
        session.save(o);
        logger.info("ExecCypher {} cost:{}ms", o.toString(), System.currentTimeMillis() - time);
    }

    /**
     * 清空 session 缓存。
     */
    public void clear() {
        session.clear();
    }
}
