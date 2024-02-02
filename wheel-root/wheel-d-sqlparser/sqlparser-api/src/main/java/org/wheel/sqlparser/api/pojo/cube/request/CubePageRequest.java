package org.wheel.sqlparser.api.pojo.cube.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2023-08-23-16:36
 * @Modified By:
 */
@Data
@Accessors(chain = true)
public class CubePageRequest {
    private Integer pageNo;
    private Integer pageSize;
}
