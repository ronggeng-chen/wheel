package org.wheel.bi.api.pojo.ds.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class DsCubeQueryParserBO {

    private Map<String,DsCubeQueryParserDomainBO> domainMap = new HashMap<>();

    private DsCubeQueryParserResultDomainBO resultDomainModel = new DsCubeQueryParserResultDomainBO();

}
