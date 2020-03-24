package cn.abel.neo4j.dto;

import java.util.List;

/**
 * 用于输出图
 * @author yyb
 * @time 2020/3/24
 */
public class GraphDTO {
    private List<Object> nodes;
    private List<Object> links;

    public List<Object> getNodes() {
        return nodes;
    }

    public void setNodes(List<Object> nodes) {
        this.nodes = nodes;
    }

    public List<Object> getLinks() {
        return links;
    }

    public void setLinks(List<Object> links) {
        this.links = links;
    }
}
