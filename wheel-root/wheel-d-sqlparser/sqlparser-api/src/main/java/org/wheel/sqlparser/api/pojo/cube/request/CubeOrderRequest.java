package org.wheel.sqlparser.api.pojo.cube.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 23:04
 */
@Data
@Accessors(chain = true)
public class CubeOrderRequest {

    private String key;

    private String fieldKey;
}
