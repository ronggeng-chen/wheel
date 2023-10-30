package org.wheel.sqlparser.api;

import org.wheel.sqlparser.api.pojo.IDataSet;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 0:48
 */
public interface IDataSetSqlParser {


   String generateSql(IDataSet dataset);
}
