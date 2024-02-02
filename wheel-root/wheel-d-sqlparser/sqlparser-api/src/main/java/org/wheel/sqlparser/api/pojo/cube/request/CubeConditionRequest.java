package org.wheel.sqlparser.api.pojo.cube.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2023-11-01-15:28
 * @Modified By:
 */
@Data
@Accessors(chain = true)
public class CubeConditionRequest {

    String dataSetCode;
    
    String fieldKey;

    String operator;

    String operator;

    List<Object> values;

}
