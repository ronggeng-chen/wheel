package org.wheel.sqlparser.api.pojo.cube.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.wheel.sqlparser.api.enums.StatisticalMethodEnum;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2024-02-02-15:04
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CubeDomainFieldExprBO {

    String alise;
    
    String sqlSnippet;

    public CubeDomainFieldExprBO(StatisticalMethodEnum StatisticalMethod, String alise, String sqlSnippet) {
        this.alise = StatisticalMethodEnum.getFieldAlise(StatisticalMethod, alise);
        this.sqlSnippet = sqlSnippet;
    }
}
