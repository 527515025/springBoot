package cn.abel.neo4j.bean;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;

/**
 * @author yyb
 * @time 2020/3/23
 */
public class Person {

    @Id
//    @GeneratedValue 该注解应去除否则插入不成功
    @Index
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
