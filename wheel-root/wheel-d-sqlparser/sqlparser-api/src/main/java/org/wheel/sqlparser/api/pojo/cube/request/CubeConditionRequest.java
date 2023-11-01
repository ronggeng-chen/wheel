package org.wheel.sqlparser.api.pojo.cube.request;

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
public class CubeConditionRequest {

    String dataSetCode;

    String fieldKey;

    String valueType;

    List<Object> values;
}
