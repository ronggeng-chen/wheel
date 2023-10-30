package org.wheel.datasource.api.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import org.wheel.datasource.api.constants.DBConstants;
import org.wheel.datasource.api.dto.DBFieldDTO;
import org.wheel.datasource.api.dto.DBTableDTO;
import org.wheel.datasource.api.enums.DBExceptionEnum;
import org.wheel.datasource.api.enums.JdbcTypeEnum;
import org.wheel.datasource.api.exception.DBException;
import org.wheel.datasource.api.request.DBConfigRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBMetaData {

    private DBConfigRequest dbConfigRequest;

    private Connection connection;

    private DBMetaData() {
    }

    private DBMetaData(DBConfigRequest dbConfigRequest) {
        this.dbConfigRequest = dbConfigRequest;
        this.connection = DBConnectionUtil.getConnection(dbConfigRequest);
    }

    public static DBMetaData getDBMetaDataFactory(DBConfigRequest request) {
        return new DBMetaData(request);
    }

    /**
     * 获取数据库
     *
     * @param
     * @return
     * @throws Exception
     */
    public List<String> getDataBaseList() throws Exception {
        List<String> dataBaseList = new ArrayList<>();
        DatabaseMetaData metaData = this.connection.getMetaData();
        ResultSet result = metaData.getCatalogs();
        try {
            while (result.next()) {
                dataBaseList.add(result.getString(1));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new DBException(DBExceptionEnum.DB_METADATA_ERROR, "数据库获取失败 msg:{}", exception.getMessage());
        } finally {
            DbUtil.close(this.connection);
        }
        return dataBaseList;
    }

    /**
     * 获取模式名
     *
     * @param
     * @return
     * @throws Exception
     */
    public List<String> getSchemaList(String databaseName) throws Exception {
        List<String> schemaList = new ArrayList<>();
        DatabaseMetaData metaData = this.connection.getMetaData();
        ResultSet result = metaData.getSchemas(StrUtil.isBlank(databaseName) ? this.connection.getCatalog() : databaseName, null);
        try {
            while (result.next()) {
                schemaList.add(result.getString(1));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new DBException(DBExceptionEnum.DB_METADATA_ERROR, "模式名获取失败 msg:{}", exception.getMessage());
        } finally {
            DbUtil.close(result, this.connection);
        }
        return schemaList;
    }

    /**
     * 获取实体表
     *
     * @param request
     * @param schemaName
     * @return
     * @throws Exception
     */
    public List<DBTableDTO> getTableList(DBConfigRequest request, String databaseName, String schemaName, String tableCode) throws Exception {
        String[] types = {"TABLE"};
        return getAllTableList(databaseName, schemaName, tableCode, types);
    }

    /**
     * 获取视图
     *
     * @param
     * @param schemaName
     * @return
     * @throws Exception
     */
    public List<DBTableDTO> getViewList(String databaseName, String schemaName, String tableCode, String type) throws Exception {
        String[] types = {"VIEW"};
        return getAllTableList(databaseName, schemaName, tableCode, types);
    }

    /**
     * 获取所有表和视图
     *
     * @param
     * @param schemaName
     * @param types
     * @return
     * @throws Exception
     */
    public List<DBTableDTO> getAllTableList(String databaseName, String schemaName, String tableCode, String[] types) throws Exception {
        List<DBTableDTO> tableList = new ArrayList<>();
        DatabaseMetaData metaData = this.connection.getMetaData();
        String databaseProductName = metaData.getDatabaseProductName().trim();
        Statement statement = null;
        ResultSet result = null;
        try {
            result = metaData.getTables(StrUtil.isBlank(databaseName) ? this.connection.getCatalog() : databaseName, StrUtil.isBlank(schemaName) ? null : schemaName, StrUtil.isBlank(tableCode) ? null : "%" + tableCode + "%", types);
            while (result.next()) {
                DBTableDTO table = new DBTableDTO();
                table.setTableCode(result.getString("TABLE_NAME"));
                table.setTableType(result.getString("TABLE_TYPE"));
                table.setTableName(result.getString("REMARKS"));
                tableList.add(table);
            }
            if (StrUtil.equals(databaseProductName, DBConstants.DBDatabaseProductType.MICROSOFT_SQL_SERVER)) {
                String rmarkSql =
                        "SELECT DISTINCT" + "    CONVERT(varchar(200), d.name) name," + "    CONVERT(varchar(200), f.value)value " + "   FROM" +
                                "    syscolumns a" + "  " +
                                "  LEFT JOIN systypes b ON a.xusertype= b.xusertype" + "    INNER JOIN sysobjects d ON a.id= d.id " + "    AND d.xtype= 'U' " + "    AND d.name<> 'dtproperties'" + "    LEFT JOIN syscomments e ON a.cdefault= e.id" + "    LEFT JOIN sys.extended_properties g ON a.id= G.major_id " + "    AND a.colid= g.minor_id" + "    LEFT JOIN sys.extended_properties f ON d.id= f.major_id " + "    AND f.minor_id= 0";
                statement = this.connection.createStatement();
                result = statement.executeQuery(rmarkSql);
                while (result.next()) {
                    for (DBTableDTO dbTableDTO : tableList) {
                        if (StrUtil.equals(dbTableDTO.getTableCode(), Convert.toStr(result.getObject("name")))) {
                            dbTableDTO.setTableName(Convert.toStr(result.getObject("value")));
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new DBException(DBExceptionEnum.DB_METADATA_ERROR, "表或视图获取失败 msg:{}", exception.getMessage());
        } finally {
            DbUtil.close(result, statement, this.connection);
        }
        return tableList;
    }


    public List<DBFieldDTO> getFieldList(String databaseName, String schemaName, String tableCode) throws Exception {
        List<DBFieldDTO> fieldList = new ArrayList<>();
        DatabaseMetaData metaData = this.connection.getMetaData();
        Statement statement = null;
        ResultSet result = null;
        try {
            result = metaData.getColumns(StrUtil.isBlank(databaseName) ? this.connection.getCatalog() : databaseName, StrUtil.isBlank(schemaName) ? null : schemaName, StrUtil.isBlank(tableCode) ? null : tableCode, null);
            while (result.next()) {
                DBFieldDTO field = new DBFieldDTO();
                field.setFieldCode(result.getString("COLUMN_NAME"));
                field.setFieldName(result.getString("REMARKS"));
                field.setTableCode(result.getString("TABLE_NAME"));
                field.setDataType(result.getInt("DATA_TYPE"));
                field.setDataTypeName(result.getString("TYPE_NAME"));
                JdbcTypeEnum jdbcTypeEnum = JdbcTypeEnum.getEnum(field.getDataType());
                if (jdbcTypeEnum != null) {
                    field.setJavaType(jdbcTypeEnum.getJavaType().getName());
                }
                field.setFieldSize(result.getInt("COLUMN_SIZE"));
                field.setDecimalDigits(result.getInt("DECIMAL_DIGITS"));
                field.setNullable(result.getInt("NULLABLE"));
                field.setNumPrecRadix(result.getInt("NUM_PREC_RADIX"));
                field.setFieldName(result.getString("REMARKS"));
                fieldList.add(field);
            }
            String databaseProductName = metaData.getDatabaseProductName().trim();
            if (StrUtil.equals(databaseProductName, DBConstants.DBDatabaseProductType.MICROSOFT_SQL_SERVER)) {
                String sql = "select CONVERT(varchar(200), a.name)name,  remarks=isnull(CONVERT(varchar(200), g.[value]),'') from syscolumns a " + "inner join   sysobjects   d   on   a.id=d.id     and   d.xtype='U'   and     d.name<>'dtproperties'" + "left join   sys.extended_properties   g   on   a.id=g.major_id   and   a.colid=g.minor_id " + "where  d.name='" + tableCode + "'";
                statement = this.connection.createStatement();
                result = statement.executeQuery(sql);
                while (result.next()) {
                    for (DBFieldDTO dbFieldDTO : fieldList) {
                        if (result.getString("name").equals(dbFieldDTO.getFieldCode())) {
                            dbFieldDTO.setFieldName(result.getString("remarks"));
                        }
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new DBException(DBExceptionEnum.DB_METADATA_ERROR, "字段获取失败 msg:{}", exception.getMessage());
        } finally {
            DbUtil.close(result, statement, this.connection);
        }
        return fieldList;
    }

    public ResultSet getTableRemark(PreparedStatement preparedStatement) {
        ResultSet resultSet = null;
        String sql = "SELECT DISTINCT" + "    d.name," + "    f.value " + "   FROM" + "    syscolumns a" + "    LEFT JOIN systypes b ON a.xusertype= b.xusertype" + "    INNER JOIN sysobjects d ON a.id= d.id " + "    AND d.xtype= 'U' " + "    AND d.name<> 'dtproperties'" + "    LEFT JOIN syscomments e ON a.cdefault= e.id" + "    LEFT JOIN sys.extended_properties g ON a.id= G.major_id " + "    AND a.colid= g.minor_id" + "    LEFT JOIN sys.extended_properties f ON d.id= f.major_id " + "    AND f.minor_id= 0";
        try {
            preparedStatement = this.connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (Exception throwables) {
            throwables.printStackTrace();
            throw new DBException(DBExceptionEnum.DB_METADATA_ERROR, "表注释获取失败 msg:{}", throwables.getMessage());
        } finally {
            DbUtil.close(resultSet, preparedStatement);
        }
        return resultSet;
    }

}

