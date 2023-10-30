package org.wheel.datasource.api.dto;

import java.util.List;

/**
 * 结果字段接口
 * aGeng
 *
 * @param <T>
 */
public interface IField<T> {

    /*获取字段code*/
    String getFieldCode();

    /*写入字段code*/
    void setFieldCode(String fieldCode);

    /*获取字段值列表*/
    List<T> getValues();

    /*添加值*/
    void addValue(T value);

    /*添加值根据索引*/
    void addValue(T value, Integer pos);

    /*获取值长度*/
    Integer getValueSize();

    /*写入值列表*/
    void setValues(List<T> values);

    /*清空字段*/
    IField<Object> deepClone();

    /*获取不重复的值列表*/
    List<Object> getNoDuplicateValues();
    /*获取不重复的String类型值列表*/

    List<String> getNoDuplicateStringValues();
}

