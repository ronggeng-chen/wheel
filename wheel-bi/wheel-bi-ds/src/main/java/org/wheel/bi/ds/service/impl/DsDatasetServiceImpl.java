package org.wheel.bi.ds.service.impl;

import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.wheel.bi.api.pojo.ds.dto.DsDatasetDTO;
import org.wheel.bi.api.pojo.ds.request.DsDatasetRequest;
import org.wheel.bi.ds.entity.DsDataset;
import org.wheel.bi.ds.mapper.mapping.DsDatasetMapper;
import org.wheel.bi.ds.service.IDsDatasetService;

import java.util.List;

/**
 * 数据集 服务实现类
 *
 * @className: DsDatasetServiceImpl
 * @description:
 * @author: aGeng
 * @date: 2024-03-07 23:00:13
 **/
@Slf4j
@Service
public class DsDatasetServiceImpl extends ServiceImpl<DsDatasetMapper, DsDataset> implements IDsDatasetService {
    @Override
    public List<DsDatasetDTO> getList(DsDatasetRequest request) {
        return null;
    }

    @Override
    public PageResult<DsDatasetDTO> getPage(DsDatasetRequest request) {
        return null;
    }

    @Override
    public DsDatasetDTO get(DsDatasetRequest request) {
        return null;
    }

    @Override
    public DsDatasetDTO getInfo(DsDatasetRequest request) {
        return null;
    }

    @Override
    public void add(DsDatasetRequest request) {

    }

    @Override
    public void edit(DsDatasetRequest request) {

    }

    @Override
    public void delete(DsDatasetRequest request) {

    }
}
