package org.wheel.datasource.api.dto;

import java.util.List;
import java.util.Map;

/**
 * 返回数据集接口
 * aGeng
 */
public interface IDataSet {
    /*写入总计*/
    void setTotalCount(Integer count);
    /*获取总计*/
    Integer getTotalCount();

    /*获取字段信息列表*/
    <T> List<IField<T>> getFields();

    /*获取字段*/
    <T> IField<T> getFieldByFieldCode(String fieldCode);

    /*根据字段Code查询对应信息的值*/
    <T> List<T> getFieldValuesByFieldCode(String fieldCode);

    /*获取查询结果行数*/
    int getRowCount();

    /*获取查询结果列数*/
    int getColCount();

    /*获取所有字段code*/
    List<String> getAllFieldCodes();

    /*根据行索引获取其值*/
    Object getCellValue(String fieldCode, int rowIndex);

    /*获取所有行信息*/
    List<Map<String, Object>> getRows();

    /*写入行信息*/
    void setRows();

    /*根据列索引获取其字段信息*/
    IField<Object> getFieldByColIndex(int colIndex);

    /*根据行索引获取其行信息*/
    List<Object> getRow(int rowIndex);


}

