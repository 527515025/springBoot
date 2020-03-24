package cn.abel.neo4j.service;

import cn.abel.neo4j.bean.King;
import cn.abel.neo4j.bean.Queen;
import cn.abel.neo4j.bean.relation.FatherAndSonRelation;
import cn.abel.neo4j.dao.KingDao;
import cn.abel.neo4j.dao.Neo4jDao;
import cn.abel.neo4j.dao.Neo4jSession;
import cn.abel.neo4j.dto.GraphDTO;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.InternalRelationship;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yyb
 * @time 2020/3/23
 */
@Service
public class KingService {
    @Autowired
    private Neo4jDao neo4jDao;

    @Autowired
    private KingDao kingDao;

    /**
     * 保存皇帝信息
     *
     * @param list
     */
    public void saveKing(List<King> list) {
        Neo4jSession session = neo4jDao.open();
        list.forEach(x -> {
            session.execSave(x);
        });
    }

    /**
     * 保存皇后信息
     *
     * @param list
     */
    public void saveQueen(List<Queen> list) {
        Neo4jSession session = neo4jDao.open();
        list.forEach(x -> {
            session.execSave(x);
        });
    }


    /**
     * 通过皇帝名字，查到当前的皇后和上一任皇帝
     *
     * @param
     */
    public GraphDTO getKingAndQueen(String kingName) {
        Neo4jSession session = neo4jDao.open();
        String cypher = "match p=(a:King)-[r:`传位`]->(b:King)-[r2:`皇后`*1..]->(c:Queen) where a.name ='" + kingName + "' return p";
//        通过皇帝名字查询到当前皇帝皇后以及上一任皇帝皇后
//        match p=(a:King)-[r:`传位`]->(b:King)-[r2:`皇后`*1..]->(c:Queen), (a:King)-[r3:`皇后`*1..]->(d:Queen) where b.name ='朱棣' return a,b,c,d
        Iterator<Map<String, Object>> iterator = session.exec(cypher);
        GraphDTO gto = mapToGraph(iterator);
        return gto;
    }


    private GraphDTO mapToGraph(
            Iterator<Map<String, Object>> neo4jDataIterator) {
        Map<Long, Object> nodeMap = new HashMap<>();
        Map<Long, Object> linkMap = new HashMap<>();

        while (neo4jDataIterator.hasNext()) {
            Map<String, Object> each = neo4jDataIterator.next();
            if (!each.containsKey("p")) {
                continue;
            }

            InternalPath internalPath = (InternalPath) each.get("p");
            //归纳出所有节点。
            for (Node node : internalPath.nodes()) {
                InternalNode internalNode = (InternalNode) node;
                if (nodeMap.containsKey(internalNode.id())) {
                    continue;
                }
                nodeMap.put(internalNode.id(), internalNode);
            }
            //归纳出所有关系。
            for (Relationship relation : internalPath.relationships()) {
                InternalRelationship internalRelation = (InternalRelationship) relation;
                if (linkMap.containsKey(internalRelation.id())) {
                    continue;
                }
                linkMap.put(internalRelation.id(), internalRelation);
            }
        }

        GraphDTO dto = new GraphDTO();
        dto.setNodes(nodeMap.values().stream().collect(Collectors.toList()));
        dto.setLinks(linkMap.values().stream().collect(Collectors.toList()));
        return dto;
    }


    /**
     * 查询一个皇帝信息，此处使用spring-data-neo4j 接口
     *
     * @param name
     */
    public King findByName(String name) {
        King t = kingDao.findByName(name);
        return t;
    }


    /**
     * 获取当前节点下的所有king
     *
     * @param name
     * @return
     */
    public List<King> getKings(String name) {
        return kingDao.getKings(name);
    }


    /**
     * 保存父子关系
     *
     * @param fatherName
     * @param sonName
     * @return
     */
    public void saveRelation(String fatherName, String sonName) {
        King from = kingDao.findByName(fatherName);
        King to = kingDao.findByName(sonName);
        FatherAndSonRelation fatherAndSonRelation = new FatherAndSonRelation();
        fatherAndSonRelation.setFrom(from);
        fatherAndSonRelation.setTo(to);
        from.addRelation(fatherAndSonRelation);
        kingDao.save(from);
    }
}
