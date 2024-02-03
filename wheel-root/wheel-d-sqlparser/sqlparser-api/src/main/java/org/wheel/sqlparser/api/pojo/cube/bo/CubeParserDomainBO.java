package org.wheel.sqlparser.api.pojo.cube.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.wheel.sqlparser.api.pojo.cube.request.CubeConditionRequest;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class CubeParserDomainBO {

    String alise;

    String dataSetCode;

    List<CubeParserDomainFieldBO> fields;

    List<CubeConditionRequest>

    public CubeParserDomainBO() {
        this.fields = new ArrayList<>();
    }
}
