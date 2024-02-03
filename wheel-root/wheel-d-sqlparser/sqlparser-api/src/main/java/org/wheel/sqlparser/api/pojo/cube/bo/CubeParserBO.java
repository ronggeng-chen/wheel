package org.wheel.sqlparser.api.pojo.cube.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class CubeParserBO {

    String dbType;

    List<CubeParserResultFieldBO> resultFields;

    List<CubeParserDomainBO> computeDomains;

    public CubeParserBO() {
        this.resultFields = new ArrayList<>();
        this.computeDomains = new ArrayList<>();
    }
}
