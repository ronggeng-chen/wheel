package org.wheel.datasource.api.enums;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wheel.datasource.api.constants.DBConstants;

/**
 * 数据源类型枚举
 *
 * @author hufuhao
 * @date 2022/04/11 9:59
 **/
@Getter
@AllArgsConstructor
public enum DbTypeEnum {
    /**
     * sql server
     */
    SQL_SERVER(DBConstants.DBDatabaseProductType.MICROSOFT_SQL_SERVER, "SQLSERVER", "com.microsoft.sqlserver.jdbc.SQLServerDriver", DbType.sqlserver, SQLSelectQueryBlock.class),
    /**
     * mysql
     */
    MYSQL(DBConstants.DBDatabaseProductType.MYSQL, "MYSQL", "com.mysql.cj.jdbc.Driver", DbType.mysql, SQLSelectQueryBlock.class);;
    private String code;
    private String name;
    private String driverName;
    private DbType dbType;
    private Class<?> sqlSelectQueryBlockClass;

    public static DbTypeEnum getEnum(String code) {
        for (DbTypeEnum dbTypeEnum : DbTypeEnum.values()) {
            if (dbTypeEnum.getCode().equals(code)) {
                return dbTypeEnum;
            }
        }
        return null;
    }
}
