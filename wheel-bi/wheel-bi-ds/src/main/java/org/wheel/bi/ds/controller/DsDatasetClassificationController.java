package org.wheel.bi.ds.controller;

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
import org.wheel.bi.api.pojo.ds.request.DsDatasetClassificationRequest;
import org.wheel.bi.ds.service.IDsDatasetClassificationService;

/**
 * 数据集分类 前端控制器
 *
 * @className: DsDatasetClassificationController
 * @description:
 * @author: aGeng
 * @date: 2024-03-07 23:00:13
 **/
@RestController
@ApiResource(name = "数据集分类", path = "/ds/dataset/classification")
public class DsDatasetClassificationController {

    @Autowired
    IDsDatasetClassificationService service;

    /**
     * 列表
     *
     * @param request
     * @return cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData<T>
     * @author aGeng
     * @date 2024-03-07 23:00:13
     **/
    @GetResource(name = "列表", path = "/list")
    public <T> ResponseData<T> list(@RequestBody @Validated(BaseRequest.list.class) DsDatasetClassificationRequest request) {
        return new SuccessResponseData<>(null);
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
    public <T> ResponseData<T> page(@RequestBody @Validated(BaseRequest.page.class) DsDatasetClassificationRequest request) {
        return new SuccessResponseData<>(null);
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
    public <T> ResponseData<T> get(@RequestBody @Validated(BaseRequest.detail.class) DsDatasetClassificationRequest request) {
        return new SuccessResponseData<>(null);
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
    public <T> ResponseData<T> getInfo(@RequestBody @Validated(BaseRequest.detail.class) DsDatasetClassificationRequest request) {
        return new SuccessResponseData<>(null);
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
    public <T> ResponseData<T> add(@RequestBody @Validated(BaseRequest.add.class) DsDatasetClassificationRequest request) {
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
    public <T> ResponseData<T> edit(@RequestBody @Validated(BaseRequest.edit.class) DsDatasetClassificationRequest request) {
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
    public <T> ResponseData<T> delete(@RequestBody @Validated(BaseRequest.delete.class) DsDatasetClassificationRequest request) {
        return new SuccessResponseData<>();
    }


}
