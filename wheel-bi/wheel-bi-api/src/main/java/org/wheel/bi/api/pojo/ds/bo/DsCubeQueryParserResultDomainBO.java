package org.wheel.bi.api.pojo.ds.bo;

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
public class DsCubeQueryParserResultDomainBO {

    Map<String,DsCubeQueryParserResultDomainFieldBO> fieldMap = new HashMap<>();

    DsCubeQueryParserResultDomainJoinBO join;

    DsCubeQueryParserResultDomainPageBO page;

    List<DsCubeQueryParserResultDomainOrderBO> orders = new ArrayList<>();
}
