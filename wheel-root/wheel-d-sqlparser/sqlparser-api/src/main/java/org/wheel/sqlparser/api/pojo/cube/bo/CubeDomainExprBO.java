package org.wheel.sqlparser.api.pojo.cube.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2024-02-02-15:25
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CubeDomainExprBO {

    String alise;

    List<CubeDomainFieldExprBO> fieldList;

    Date startTime;

    Date endTime;
}
