package cn.abel.constants;

/**
 * @author yangyibo
 * @time 2019/4/3
 */
public class Constants {

    /**
     * es 查询query 拼接 前缀
     */
    public static final String ES_QUERY_JSON_PREFIX = "{\n" +
            "  \"query\": {\n" +
            "    \"term\": {\n" +
            "      \"userId\": {\n" +
            "        \"value\": \"";
    /**
     * es 查询query 拼接后缀
     */
    public static final String ES_QUERY_JSON_SUFFIX = "\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}\n";

    /**
     * 需要查询的es的 index
     */
    public static final String ES_INDEX_PATH = "/logstash_indes/_search";

}


