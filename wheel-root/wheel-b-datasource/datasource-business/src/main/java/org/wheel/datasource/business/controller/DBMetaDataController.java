package org.wheel.datasource.business.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wheel.datasource.api.request.DBMetaDataRequest;
import org.wheel.datasource.business.service.DBMetaDataService;

@RestController
@Slf4j
@ApiResource(name = "元数据", path = "/db")
public class DBMetaDataController {

    @Autowired
    DBMetaDataService dbMetaDataService;

    @PostResource(name = "获取数据库列表", path = "/metadata/getDatabaseList")
    public ResponseData<?> page(@RequestBody DBMetaDataRequest request) throws Exception {
        return new SuccessResponseData(dbMetaDataService.getDatabaseList(request));
    }

    @PostResource(name = "获取模式列表", path = "/metadata/getSchemaList")
    public ResponseData<?> getSchemaList(@RequestBody DBMetaDataRequest request) throws Exception {
        return new SuccessResponseData(dbMetaDataService.getSchemaList(request));
    }

    @PostResource(name = "获取表列表", path = "/metadata/getTableList")
    public ResponseData<?> getTableList(@RequestBody DBMetaDataRequest request) throws Exception {
        return new SuccessResponseData(dbMetaDataService.getTableList(request));
    }

    @PostResource(name = "字段列表", path = "/metadata/getFieldList")
    public ResponseData<?> getFieldList(@RequestBody DBMetaDataRequest request) throws Exception {
        return new SuccessResponseData(dbMetaDataService.getFieldList(request));
    }
}
