package cn.abel.neo4j.controller;


import cn.abel.neo4j.bean.King;
import cn.abel.neo4j.service.KingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yyb
 * @time 2020/3/23
 */
@Controller
@RequestMapping("/king")
public class KingController {

    @Autowired
    private KingService kingService;
    @RequestMapping(value = "/save/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String save(@PathVariable long id){
        List<King> kingList = new ArrayList<>();
        King king = new King();
        king.setId(id);
        king.setName("朱元璋");
        king.setClan("创业者");
        king.setPosthumousTitle("高皇帝");
        king.setTimes("洪武（1368年——1398年）");
        king.setTempleNumber("太祖");
        king.setTomb("孝陵");
        king.setRemark("空印案，胡惟庸案");
        kingList.add(king);
        kingService.saveKing(kingList);
        return "ok";
    }


}
