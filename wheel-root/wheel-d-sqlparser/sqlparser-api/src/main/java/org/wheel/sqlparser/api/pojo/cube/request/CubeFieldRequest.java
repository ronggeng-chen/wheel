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
    /**
     * 查询字段别名
     */
    String alias;
    /**
     * 数据集编码
     */
    String dataSetCode;
    /**
     * 字段编码
     */
    String fieldKey;

    String statisticalMethod;
}
