package org.wheel.sqlparser.api.pojo.cube.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CubeParserResultFieldBO {

    String alise;

    String sqlSnippet;
}
