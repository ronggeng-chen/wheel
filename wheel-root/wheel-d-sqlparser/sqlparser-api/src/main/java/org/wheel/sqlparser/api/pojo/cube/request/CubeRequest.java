package org.wheel.sqlparser.api.pojo.cube.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
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

    private String key;

    private String dbType;

    private List<CubeFieldRequest> fields;

    private List<CubeConditionRequest> conditions;

    private Date startTime;

    private Date endTime;

    private List<CubeOrderRequest> orders;

    private CubePageRequest page;

}
