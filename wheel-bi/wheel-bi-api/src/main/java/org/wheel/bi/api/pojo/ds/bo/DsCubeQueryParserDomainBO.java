package org.wheel.bi.api.pojo.ds.bo;

import com.alibaba.druid.sql.ast.statement.SQLSelect;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class DsCubeQueryParserDomainBO {

    private String domainCode;

    private SQLSelect fromSqlSelect;

    private Map<String,DsCubeQueryParserDomainFieldBO> fieldMap = new HashMap<>();

    private List<DsCubeQueryParserDomainConditionBO> conditions = new ArrayList<>();

}
