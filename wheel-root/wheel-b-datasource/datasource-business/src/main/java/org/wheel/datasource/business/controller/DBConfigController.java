package org.wheel.datasource.business.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wheel.datasource.api.request.DBConfigRequest;
import org.wheel.datasource.business.service.DBConfigService;

@RestController
@Slf4j
@ApiResource(name = "数据源管理", path = "/db")
public class DBConfigController {

    @Autowired
    DBConfigService dbConfigService;

    @PostResource(name = "测试数据源", path = "/config/test")
    public ResponseData<?> test(@RequestBody DBConfigRequest request) {
        return dbConfigService.testConnection(request);
    }

    @PostResource(name = "新建数据源", path = "/config/add")
    public ResponseData<?> add(@RequestBody DBConfigRequest request) {
        dbConfigService.addConfig(request);
        return new SuccessResponseData();
    }

    @PostResource(name = "修改数据源", path = "/config/edit")
    public ResponseData<?> edit(@RequestBody DBConfigRequest request) {
        dbConfigService.editConfig(request);
        return new SuccessResponseData();
    }

    @PostResource(name = "删除数据源", path = "/config/delete")
    public ResponseData<?> delete(@RequestBody DBConfigRequest request) {
        dbConfigService.deleteConfig(request);
        return new SuccessResponseData();
    }

    @PostResource(name = "查询数据源列表", path = "/config/list")
    public ResponseData<?> list(@RequestBody DBConfigRequest request) {
        return new SuccessResponseData(dbConfigService.getList(request,false));
    }

    @PostResource(name = "查询数据源分页", path = "/config/page")
    public ResponseData<?> page(@RequestBody DBConfigRequest request) {
        return new SuccessResponseData(dbConfigService.getPage(request));
    }

    @PostResource(name = "根据code获取数据源", path = "/config/get")
    public ResponseData<?> getDbConfigByDbCode(@RequestBody DBConfigRequest request) {
        return new SuccessResponseData(dbConfigService.getConfig(request,false));
    }

    @PostResource(name = "获取数据源类型列表", path = "/dbType/list")
    public ResponseData<?> getDbType() {
        return new SuccessResponseData(dbConfigService.getDbTypeList());
    }
}
