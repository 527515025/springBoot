package cn.abel.user.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yyb
 * @time 2020/3/10
 */
public class CommentUtils {

    /**
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List copyList(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList();
        }
        return JSON.parseArray(JSON.toJSONString(list), list.get(0).getClass());
    }

}
