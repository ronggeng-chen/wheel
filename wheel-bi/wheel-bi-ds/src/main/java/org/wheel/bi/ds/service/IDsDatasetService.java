package org.wheel.bi.ds.service;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;
import org.wheel.bi.api.pojo.ds.request.DsDatasetRequest;
import org.wheel.bi.ds.entity.DsDataset;

import java.util.List;

/**
 * 数据集 服务类
 *
 * @className: IDsDatasetService
 * @description:
 * @author: aGeng
 * @date: 2024-03-07 23:00:13
 **/
public interface IDsDatasetService extends IService<DsDataset> {
    List<DsDatasetDTO> getList(DsDatasetRequest request);

    PageResult<DsDatasetDTO> getPage(DsDatasetRequest request);

    DsDatasetDTO get(DsDatasetRequest request);

    DsDatasetDTO getInfo(DsDatasetRequest request);

    void add(DsDatasetRequest request);

    void edit(DsDatasetRequest request);

    void delete(DsDatasetRequest request);
}
