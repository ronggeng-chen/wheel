package org.wheel.datasource.api.dto;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBDataSetDTO implements IDataSet {
    /*字段结果*/
    private List<Field<Object>> fields = new ArrayList<Field<Object>>();
    /*查询行结果*/
    private List<Map<String, Object>> rows;
    /*总条数*/
    private Integer totalCount;

    @Override
    public void setTotalCount(Integer count) {
        this.totalCount = count;
    }

    @Override
    public Integer getTotalCount() {
        return totalCount;
    }

    @Override
    public List<Field<Object>> getFields() {
        return fields;
    }

    public void setFields(List<Field<Object>> fields) {
        this.fields = fields;
    }

    @Override
    public Field<Object> getFieldByFieldCode(String fieldCode) {
        for (Field<Object> field : getFields()) {
            if (fieldCode.equals(field.getFieldCode())) {
                return field;
            }
        }
        return null;
    }

    @Override
    public List<Object> getFieldValuesByFieldCode(String fieldCode) {
        Field<Object> field = getFieldByFieldCode(fieldCode);
        if (ObjectUtil.isAllNotEmpty(field)) {
            return field.getValues();
        }
        return null;
    }


    @Override
    public int getRowCount() {
        if (CollUtil.isEmpty(getFields())) {
            return 0;
        }
        return getFields().get(0).getValues().size();
    }

    @Override
    public int getColCount() {
        if (CollUtil.isEmpty(getFields())) {
            return 0;
        }
        return getFields().size();
    }

    @Override
    public List<String> getAllFieldCodes() {
        List<String> fieldCodes = new ArrayList<String>();
        for (Field<Object> field : getFields()) {
            fieldCodes.add(field.getFieldCode());
        }
        return fieldCodes;
    }

    @Override
    public Object getCellValue(String fieldCode, int rowIndex) {
        return rows.get(rowIndex).get(fieldCode);
    }

    @Override
    public List<Map<String, Object>> getRows() {
        if (CollUtil.isEmpty(rows)) {
            setRows();
        }
        return rows;
    }

    @Override
    public void setRows() {
        if (CollUtil.isEmpty(rows)) {
            rows = new ArrayList<>();
        }
        int rowCount = getRowCount();
        if (rowCount == 0) {
            return;
        } else {
            for (int i = 0; i < rowCount; i++) {
                rows.add(new HashMap<String, Object>());
            }
        }
        List<Field<Object>> fields = getFields();
        for (Field<Object> field : fields) {
            String fieldCode = field.getFieldCode();
            List<Object> values = field.getValues();
            if (CollUtil.isNotEmpty(values)) {
                for (int i = 0; i < rowCount; i++) {
                    rows.get(i).put(fieldCode, values.get(i));
                }
            }
        }
    }

    @Override
    public IField<Object> getFieldByColIndex(int colIndex) {
        return fields.get(colIndex);
    }

    @Override
    public List<Object> getRow(int rowIndex) {
        List<Object> row = new ArrayList<Object>();
        for (int i = 0; i < getColCount(); i++) {
            row.add(getFieldByColIndex(i).getValues().get(i));
        }
        return row;
    }

    public DBDataSetDTO() {

    }

    public DBDataSetDTO(List<String> fieldList) {
        for (String fieldCode : fieldList) {
            Field<Object> field = new Field<>();
            field.setFieldCode(fieldCode);
            field.setValues(new ArrayList<>());
            fields.add(field);
        }
    }
}