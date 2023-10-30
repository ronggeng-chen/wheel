package org.wheel.datasource.api.dto;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Field<T> implements IField<T> {

    /*字段Code*/
    private String fieldCode;

    /*字段列值*/
    private List<T> values = new ArrayList<T>();

    @Override
    public String getFieldCode() {
        return fieldCode;
    }

    @Override
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    @Override
    public List<T> getValues() {
        return values;
    }

    @Override
    public void setValues(List<T> values) {
        this.values = values;
    }

    @Override
    public void addValue(T value) {
        values.add(value);
    }

    @Override
    public void addValue(T value, Integer pos) {
        getValues().set(pos, value);
    }

    @Override
    public Integer getValueSize() {
        return values.size();
    }

    @Override
    public IField<Object> deepClone() {
        IField<Object> field = new Field<Object>();
        BeanUtils.copyProperties(this, field);
        return field;
    }

    @Override
    public List<Object> getNoDuplicateValues() {
        List<Object> values = new ArrayList<Object>();
        for (Object value : getValues()) {
            if (value != null && !values.contains(value)) {
                values.add(value);
            }
        }
        return values;
    }

    @Override
    public List<String> getNoDuplicateStringValues() {
        List<String> values = new ArrayList<String>();
        for (Object value : getValues()) {
            if (value != null && !values.contains(value.toString())) {
                values.add(value.toString());
            }
        }
        return values;
    }
}
