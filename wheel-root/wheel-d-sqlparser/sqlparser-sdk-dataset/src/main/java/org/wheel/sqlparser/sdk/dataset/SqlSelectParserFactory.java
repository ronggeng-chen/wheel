package org.wheel.sqlparser.sdk.dataset;

import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.sqlparser.api.IDataSetSqlParser;
import org.wheel.sqlparser.api.enums.DataSetTypeEnum;
import org.wheel.sqlparser.api.exception.SqlParserException;
import org.wheel.sqlparser.api.exception.enums.SqlParserExceptionEnum;
import org.wheel.sqlparser.api.pojo.dataset.bo.IDataSet;
import org.wheel.sqlparser.sdk.dataset.view.SqlSelectParserViewParser;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2023-11-02-13:53
 * @Modified By:
 */
public class SqlSelectParserFactory {

    public static IDataSetSqlParser getSqlParser(DbTypeEnum dbTypeEnum, IDataSet dataSet) {
        DataSetTypeEnum dataSetTypeEnum = DataSetTypeEnum.getEnum(dataSet.getDataSetType());
        IDataSetSqlParser sqlSelectParser = null;
        if (dataSetTypeEnum == DataSetTypeEnum.VIEW) {
            sqlSelectParser = new SqlSelectParserViewParser(dbTypeEnum, dataSet);
        } else if (dataSetTypeEnum == DataSetTypeEnum.SQL) {

        } else if (dataSetTypeEnum == DataSetTypeEnum.FILE) {

        } else {
            throw new SqlParserException(SqlParserExceptionEnum.SQL_PARSER_PARAM_ERROR, "无法识别数据集类型");
        }
        return sqlSelectParser;
    }
}
