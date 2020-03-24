package cn.abel.neo4j.bean.relation;

import cn.abel.neo4j.bean.King;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 父子关系实体
 * @author yyb
 * @time 2020/3/24
 */
@RelationshipEntity(type = "father_son")
public class FatherAndSonRelation {

    @Id
    private long id;

    @StartNode
    private King from;

    @EndNode
    private King to;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public King getFrom() {
        return from;
    }

    public void setFrom(King from) {
        this.from = from;
    }

    public King getTo() {
        return to;
    }

    public void setTo(King to) {
        this.to = to;
    }
}
