package com.us.example.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;



public  class CommonUtil {

    /**
     * 从request中获得参数Map，并返回可读的Map.
     *
     * @param request the request
     * @return the parameter map
     */
    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map<String, String[]> properties = request.getParameterMap();
        //返回值Map
        Map<String, Object> returnMap = new HashMap<String, Object>();
        Set<String> keySet = properties.keySet();
        for(String key: keySet){
            String[] values = properties.get(key);
            String value = "";
            if(values != null && (values.length==1&&StringUtils.isNotBlank(values[0]))?true:false){
                for(int i=0;i<values.length;i++){
                    if(values[i] != null && !"".equals(values[i])){
//							value = new String(values[i].getBytes("ISO-8859-1"),"UTF-8") + ",";
                        value += values[i] + ",";
                    }
                }
                if(value != null && !"".equals(value)){
                    value = value.substring(0, value.length()-1);
                }
                if(key.equals("keywords")){//关键字特殊查询字符转义
                    value =  value.replace("_", "\\_").replace("%", "\\%");
                }
                returnMap.put(key, value);
            }
        }
        return returnMap;
    }

}
