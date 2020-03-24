package cn.abel.neo4j.service;

import cn.abel.neo4j.bean.King;
import cn.abel.neo4j.bean.Queen;
import cn.abel.neo4j.bean.relation.FatherAndSonRelation;
import cn.abel.neo4j.dao.KingDao;
import cn.abel.neo4j.dao.Neo4jDao;
import cn.abel.neo4j.dao.Neo4jSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
     * 查询一个皇帝信息，此处使用spring-data-neo4j 接口
     *
     * @param name
     */
    public King findByName(String name) {
        King t = kingDao.findByName(name);
        return t;
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
