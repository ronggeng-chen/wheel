package org.wheel.sqlparser.api.pojo.cube.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 23:16
 */
@Data
@Accessors(chain = true)
public class CubeFieldRequest {

    String alias;

    String dataSetCode;

    String fieldKey;

    String statisticalMethod;
}
