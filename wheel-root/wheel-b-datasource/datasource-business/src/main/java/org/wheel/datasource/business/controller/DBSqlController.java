package org.wheel.datasource.business.controller;

import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import cn.stylefeng.roses.kernel.scanner.api.annotation.ApiResource;
import cn.stylefeng.roses.kernel.scanner.api.annotation.PostResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.wheel.datasource.api.request.DBSqlRequest;
import org.wheel.datasource.business.service.DBSqlParserService;

import javax.annotation.Resource;
/**
 * 数据来源-sql
 * @description: TODO
 * @author 17578
 * @date 2024/2/27 16:43
 */
@RestController
@Slf4j
@ApiResource(name = "sql", path = "/db")
public class DBSqlController {
    @Resource
    DBSqlParserService dbSqlParserService;
    /**
     * 运行SQL
     * @description:
     * @param: request
     * @return: ResponseData<?>
     * @author 17578
     * @date: 2024/2/27 16:43
     */
    @PostResource(name = "运行SQL", path = "/sql/execute")
    public ResponseData<?> execute(@RequestBody DBSqlRequest request)  {
        return new SuccessResponseData(dbSqlParserService.execute(request));
    }
    /**
     * 运行SQL
     * @description:
     * @param: request
     * @return: ResponseData<?>
     * @author 17578
     * @date: 2024/2/27 16:44
     */
    @PostResource(name = "运行SQL", path = "/sql/executePage")
    public ResponseData<?> executePage(@RequestBody DBSqlRequest request) {
        return new SuccessResponseData(dbSqlParserService.execute(request));
    }
    /**
     * 格式化SQL
     * @description:
     * @param: request
     * @return: ResponseData<?>
     * @author 17578
     * @date: 2024/2/27 16:44
     */
    @PostResource(name = "格式化SQL", path = "/sql/format")
    public ResponseData<?> format(@RequestBody DBSqlRequest request) {
        return new SuccessResponseData(dbSqlParserService.getFormatSql(request));
    }
    /**
     * 校验Sql
     * @description:
     * @param: request
     * @return: ResponseData<?>
     * @author 17578
     * @date: 2024/2/27 16:44
     */
    @PostResource(name = "校验Sql", path = "/sql/checkSelect")
    public ResponseData<?> checkSelect(@RequestBody DBSqlRequest request) {
        return new SuccessResponseData(dbSqlParserService.checkSelectSql(request));
    }
    /**
     * 获取字段根据SQL
     * @description:
     * @param: request
     * @return: ResponseData<?>
     * @author 17578
     * @date: 2024/2/27 16:44
     */
    @PostResource(name = "获取字段根据SQL", path = "/sql/getFieldBySql")
    public ResponseData<?> getFieldBySql(@RequestBody DBSqlRequest request) {
        return new SuccessResponseData(dbSqlParserService.getFieldToSql(request));
    }
    /**
     * 获取字段根据Result
     * @description:
     * @param: request
     * @return: ResponseData<?>
     * @author 17578
     * @date: 2024/2/27 16:44
     */
    @PostResource(name = "获取字段根据Result", path = "/sql/getFieldByResult")
    public ResponseData<?> getFieldByResult(@RequestBody DBSqlRequest request) {
        return new SuccessResponseData(dbSqlParserService.getFieldToResult(request));
    }

}
