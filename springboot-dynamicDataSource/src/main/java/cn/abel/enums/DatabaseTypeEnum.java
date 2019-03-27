package cn.abel.enums;

/**
 * @author yyb
 * @time 2019/3/27
 */
public enum DatabaseTypeEnum {
    PRIMARY("1"), USER("2");

    private String code;

    DatabaseTypeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static DatabaseTypeEnum getDatabaseTypeEnum(String code) {
        for (DatabaseTypeEnum databaseTypeEnum : DatabaseTypeEnum.values()) {
            if (databaseTypeEnum.getCode().equals(code)) {
                return databaseTypeEnum;
            }
        }
        return null;
    }
}
