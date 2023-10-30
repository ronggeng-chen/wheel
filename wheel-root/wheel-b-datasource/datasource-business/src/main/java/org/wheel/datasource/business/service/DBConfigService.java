package org.wheel.datasource.business.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.wheel.datasource.api.DBConfigApi;
import org.wheel.datasource.api.dto.DBConfigDTO;
import org.wheel.datasource.api.dto.DBTypeDTO;
import org.wheel.datasource.api.request.DBConfigRequest;
import org.wheel.datasource.business.po.DbConfig;

import java.util.List;

public interface DBConfigService extends IService<DbConfig>, DBConfigApi {
    /**
     * 获取集合
     * @param request
     * @return
     */
    List<DBConfigDTO> getList(DBConfigRequest request, Boolean isShowPassword);
    default List<DBConfigDTO> getList(DBConfigRequest request){
     return getList(request,true);
    }
    /**
     * 检查入参
     * @param request
     */
    void checkConfig(DBConfigRequest request);

    /**
     * 新增
     * @param dbConfigRequest
     */
    void addConfig(DBConfigRequest dbConfigRequest);

    /**
     * 删除
     * @param dbConfigRequest
     */
    void deleteConfig(DBConfigRequest dbConfigRequest);

    /**
     * 编辑
     * @param dbConfigRequest
     */
    void editConfig(DBConfigRequest dbConfigRequest);


    /**
     * 获取分页 DTO
     * @param dbConfigRequest
     * @return
     */
    PageResult getPage(DBConfigRequest dbConfigRequest);

    /**
     * 获取数据源类型
     * @return
     */
    List<DBTypeDTO> getDbTypeList();

}
