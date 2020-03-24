package cn.abel.neo4j.dao;

import cn.abel.neo4j.bean.King;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 使用Springboot - data - neo4j 生成
 *
 * @author yyb
 * @time 2020/3/23
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface KingDao extends Neo4jRepository<King, Long> {

    /**
     * 通过名字查找King
     *
     * @param name
     * @return
     */
    King findByName(String name);


    /**
     * 获取当前节点下的所有king
     *
     * @param name
     * @return
     */
    @Query("match p=(a:King)-[r:`传位`*0..]->(b:King) where a.name={0} return p")
    List<King> getKings(String name);

}
