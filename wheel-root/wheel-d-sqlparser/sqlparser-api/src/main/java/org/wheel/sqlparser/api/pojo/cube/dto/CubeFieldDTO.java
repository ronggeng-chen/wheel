package org.wheel.sqlparser.api.pojo.cube.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * wheel
 *
 * @Author: aGeng
 * @Description:
 * @Target:
 * @Date Created in 2024-02-02-15:03
 * @Modified By:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CubeFieldDTO {
    List<CubeFieldDTO> fields;
    List<Map<String, Object>> rows;
}
