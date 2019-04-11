package cn.abel.service.prehandle;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by yyb on 2018/12/10.
 */
@Service
@Slf4j
public class SplitService {

    private static final Logger logger = LoggerFactory.getLogger(SplitService.class);

    public void saveAndSplitLog(String message) {
        JSONObject jsonObject = JSONObject.parseObject(message);
        // 从kafka 中获取到数据
        String content = jsonObject.getString("message");
    }

}
