package org.wheel.sqlparser.sdk.cube;

import lombok.Data;
import lombok.experimental.Accessors;
import org.wheel.sqlparser.api.pojo.dataset.bo.ISqlField;
import org.wheel.sqlparser.api.pojo.dataset.bo.SqlFilterBO;

import java.util.ArrayList;
import java.util.List;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2023-11-02-11:02
 * @Modified By:
 */
@Data
@Accessors(chain = true)
public class CubeHandleModel {

    private String dataSetCode;

    private List<ISqlField> selectFields;

    private List<ISqlField> selectTQFields;

    private List<ISqlField> selectHQFields;

    private List<SqlFilterBO> filters;

    public CubeHandleModel() {
        this.selectFields = new ArrayList<>();
        this.selectTQFields = new ArrayList<>();
        this.selectHQFields = new ArrayList<>();
    }
}