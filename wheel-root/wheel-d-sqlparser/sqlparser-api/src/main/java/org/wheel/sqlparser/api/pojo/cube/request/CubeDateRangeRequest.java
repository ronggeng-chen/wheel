package org.wheel.sqlparser.api.pojo.cube.request;

import java.util.Date;

public class CubeDateRangeRequest {
    /**
     * 日期字段Key
     */
    private String dateFieldKey;
    /**
     * 日期类型
     */
    private String dateType;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
}
