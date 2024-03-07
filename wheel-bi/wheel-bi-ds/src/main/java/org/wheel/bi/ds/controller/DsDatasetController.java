package org.wheel.bi.ds.controller;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.GetResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;
import org.wheel.bi.api.pojo.ds.request.DsDatasetRequest;
import org.wheel.bi.ds.service.IDsDatasetService;

import java.util.List;

/**
 * 数据集 前端控制器
 *
 * @className: DsDatasetController
 * @description:
 * @author: aGeng
 * @date: 2024-03-07 23:00:13
 **/
@RestController
@ApiResource(name = "数据集", path = "/ds/dataset")
public class DsDatasetController {

    @Autowired
    IDsDatasetService service;

    /**
     * 列表
     *
     * @param request
     * @return cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData<T>
     * @author aGeng
     * @date 2024-03-07 23:00:13
     **/
    @GetResource(name = "列表", path = "/list")
    public ResponseData<List<DsDatasetDTO>> list(@RequestBody @Validated(BaseRequest.list.class) DsDatasetRequest request) {
        return new SuccessResponseData<>(service.getList(request));
    }

    /**
     * 分页
     *
     * @param request
     * @return cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData<T>
     * @author aGeng
     * @date 2024-03-07 23:00:13
     **/
    @GetResource(name = "分页", path = "/page")
    public ResponseData<PageResult<DsDatasetDTO>> page(@RequestBody @Validated(BaseRequest.page.class) DsDatasetRequest request) {
        return new SuccessResponseData<>(service.getPage(request));
    }

    /**
     * 获取数据
     *
     * @param request
     * @return cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData<T>
     * @author aGeng
     * @date 2024-03-07 23:00:13
     **/
    @GetResource(name = "获取数据", path = "/get")
    public ResponseData<DsDatasetDTO> get(@RequestBody @Validated(BaseRequest.detail.class) DsDatasetRequest request) {
        return new SuccessResponseData<>(service.getInfo(request));
    }

    /**
     * 获取详情
     *
     * @param request
     * @return cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData<T>
     * @author aGeng
     * @date 2024-03-07 23:00:13
     **/
    @GetResource(name = "获取详情", path = "/getInfo")
    public ResponseData<DsDatasetDTO> getInfo(@RequestBody @Validated(BaseRequest.detail.class) DsDatasetRequest request) {
        return new SuccessResponseData<>(service.getInfo(request));
    }

    /**
     * 新增
     *
     * @param request
     * @return cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData<T>
     * @author aGeng
     * @date 2024-03-07 23:00:13
     **/
    @PostResource(name = "新增", path = "/add")
    public <T> ResponseData<T> add(@RequestBody @Validated(BaseRequest.add.class) DsDatasetRequest request) {
        service.add(request);
        return new SuccessResponseData<>();
    }

    /**
     * 编辑
     *
     * @param request
     * @return cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData<T>
     * @author aGeng
     * @date 2024-03-07 23:00:13
     **/
    @PostResource(name = "编辑", path = "/edit")
    public <T> ResponseData<T> edit(@RequestBody @Validated(BaseRequest.edit.class) DsDatasetRequest request) {
        service.edit(request);
        return new SuccessResponseData<>();
    }

    /**
     * 删除
     *
     * @param request
     * @return cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData<T>
     * @author aGeng
     * @date 2024-03-07 23:00:13
     **/
    @PostResource(name = "删除", path = "/delete")
    public <T> ResponseData<T> delete(@RequestBody @Validated(BaseRequest.delete.class) DsDatasetRequest request) {
        service.delete(request);
        return new SuccessResponseData<>();
    }


}
