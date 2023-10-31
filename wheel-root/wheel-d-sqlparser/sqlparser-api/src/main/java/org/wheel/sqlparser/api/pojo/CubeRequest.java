package org.wheel.sqlparser.api.pojo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.wheel.sqlparser.api.pojo.base.SqlFilterBO;


import org.wheel.sqlparser.api.pojo.base.SqlOrderBO;

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

    private List<CubeFieldRequest> fields;

    private List<SqlOrderBO> sqlOrder;

    private CubePageRequest page;
}
