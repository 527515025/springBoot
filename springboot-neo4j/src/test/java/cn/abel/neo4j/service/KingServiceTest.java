package cn.abel.neo4j.service;

import cn.abel.neo4j.BaseTest;
import cn.abel.neo4j.bean.King;
import cn.abel.neo4j.bean.Queen;
import cn.abel.neo4j.dao.KingDao;
import cn.abel.neo4j.dto.GraphDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author yyb
 * @time 2020/3/23
 */
public class KingServiceTest extends BaseTest {
    @Autowired
    private KingService kingService;
    @Autowired
    private KingDao kingDao;


    @Test
    public void saveKing() throws Exception {
        List<King> kingList = InitData.initKing();
        kingService.saveKing(kingList);
    }

    @Test
    public void saveQueen() throws Exception {
        List<Queen> queenList = InitData.initQueen();
        kingService.saveQueen(queenList);
    }

    @Test
    public void saveRelation() throws Exception {
        kingService.saveRelation("朱元璋", "朱棣");
    }

    @Test
    public void getOneKing() throws Exception {
        King king = kingService.findByName("朱棣");
        System.out.println();

    }

    @Test
    public void getKingAndQueen() throws Exception {
        GraphDTO graphDTO = kingService.getKingAndQueen("朱棣");
        System.out.println();

    }

}