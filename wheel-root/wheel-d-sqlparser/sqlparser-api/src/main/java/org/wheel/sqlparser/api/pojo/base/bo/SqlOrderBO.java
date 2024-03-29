package org.wheel.sqlparser.api.pojo.base.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/31 0:01
 */
@Data
@Accessors(chain = true)
public class SqlOrderBO {

    private String key;

    private String fieldKey;

    private String orderType;

    private String description;
}
