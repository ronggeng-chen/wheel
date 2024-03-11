package org.wheel.bi.api.pojo.ds.bo;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class DsCubeQueryParserDomainBO {

    private String domainCode;

    private SQLSelect fromSqlSelect;

    private List<DsCubeQueryParserDomainFieldBO> fields;

    private List<DsCubeQueryParserDomainConditionBO> conditions;

}
