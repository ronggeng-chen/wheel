package org.wheel.sqlparser.api.pojo.cube.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 23:04
 */
@Data
@Accessors(chain = true)
public class CubeRequest {
    /**
     * CUBE UUID
     */
    private String key;
    /**
     * 数据源类型
     */
    private String dbType;
    /**
     * 字段
     */
    private List<CubeFieldRequest> fields;
    /**
     * 条件
     */
    private List<CubeConditionRequest> conditions;
    /**
     * 排序
     */
    private List<CubeOrderRequest> orders;
    /**
     * 日期范围
     */
    private CubeDateRangeRequest dateRange;
    /**
     * 分页
     */
    private CubePageRequest page;

}
