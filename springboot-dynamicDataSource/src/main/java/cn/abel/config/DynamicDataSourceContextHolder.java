package cn.abel.config;

import cn.abel.enums.DatabaseTypeEnum;

/**
 * @author yyb
 * @time 2019/3/27
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<DatabaseTypeEnum> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseTypeEnum type){
        contextHolder.set(type);
    }

    public static DatabaseTypeEnum getDatabaseType(){
        return contextHolder.get();
    }

    public static void resetDatabaseType() {
        contextHolder.set(DatabaseTypeEnum.PRIMARY);
    }
}
