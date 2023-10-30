package org.wheel.datasource.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.DbUtil;
import cn.stylefeng.roses.kernel.db.api.factory.PageFactory;
import cn.stylefeng.roses.kernel.db.api.factory.PageResultFactory;
import cn.stylefeng.roses.kernel.db.api.pojo.page.PageResult;
import cn.stylefeng.roses.kernel.rule.pojo.response.ResponseData;
import cn.stylefeng.roses.kernel.rule.pojo.response.SuccessResponseData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wheel.datasource.api.dto.DBConfigDTO;
import org.wheel.datasource.api.dto.DBTypeDTO;
import org.wheel.datasource.api.enums.DBExceptionEnum;
import org.wheel.datasource.api.enums.DbTypeEnum;
import org.wheel.datasource.api.exception.DBException;
import org.wheel.datasource.api.request.DBConfigRequest;
import org.wheel.datasource.api.util.DBConnectionUtil;
import org.wheel.datasource.business.mapper.DBConfigMapper;
import org.wheel.datasource.business.po.DbConfig;
import org.wheel.datasource.business.service.DBConfigService;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBConfigServiceImpl extends ServiceImpl<DBConfigMapper, DbConfig> implements DBConfigService {

    //######################################API########################################

    @Override
    public DBConfigDTO getConfig(DBConfigRequest request,Boolean isShowPassword) {
        DbConfig dbConfig = null;
        if (request.getDbId() != null) {
            dbConfig = this.baseMapper.selectById(request.getDbId());
        } else if (StrUtil.isNotBlank(request.getDbCode())) {
            LambdaQueryWrapper<DbConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DbConfig::getDbCode, request.getDbCode());
            dbConfig = this.baseMapper.selectOne(lambdaQueryWrapper);
        }
        if(BooleanUtil.isFalse(isShowPassword)){
            dbConfig.setPassword("***");
        }
        return BeanUtil.copyProperties(dbConfig, DBConfigDTO.class);
    }

    @Override
    public Connection getConnection(DBConfigRequest request) {
        DBConfigDTO dto = getConfig(request);
        return DBConnectionUtil.getConnection(BeanUtil.copyProperties(dto, DBConfigRequest.class));
    }

    @Override
    public ResponseData<Boolean> testConnection(DBConfigRequest request) {
        if(request.getDbId() != null){
            DBConfigDTO dbConfigDTO = this.getConfig(request );
            request = BeanUtil.toBean(dbConfigDTO,DBConfigRequest.class);
        }
        ResponseData<Boolean> responseData = new SuccessResponseData<Boolean>();
        request.setDriverClassName(getDbTypeEnum(request.getDbType()).getDriverName());
        try {
            DBConnectionUtil.testConnection(request);
            responseData.setData(true);
        } catch (Exception ex) {
            responseData.setData(false);
            responseData.setMessage(ex.getMessage());
        }
        return responseData;
    }

    @Override
    public boolean getTestConnection(DBConfigRequest request) {
        request.setDriverClassName(getDbTypeEnum(request.getDbType()).getDriverName());
        return DBConnectionUtil.getTestConnection(request);
    }

    @Override
    public DbTypeEnum getDbTypeEnum(String dbTypeCode) {
        return DbTypeEnum.getEnum(dbTypeCode);
    }
    //######################################API########################################

    @Override
    public List<DBConfigDTO> getList(DBConfigRequest request,Boolean isShowPassword) {
        LambdaQueryWrapper<DbConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StrUtil.isNotBlank(request.getDbType()), DbConfig::getDbType, request.getDbType()).and(StrUtil.isNotBlank(request.getKeyword()), wrapper -> wrapper.like(DbConfig::getDbCode, request.getKeyword()).or().like(DbConfig::getDbName, request.getKeyword()));
        List<DbConfig> list = list(lambdaQueryWrapper);

        if(BooleanUtil.isFalse(isShowPassword)){
            list.forEach(item->{
                item.setPassword("***");
            });
        }
        return Convert.toList(DBConfigDTO.class, list);
    }


    @Override
    public void checkConfig(DBConfigRequest request) {
        if (ObjectUtil.isNotEmpty(request.getDbId())) {
            DbConfig dbConfig = this.baseMapper.selectOne(new LambdaQueryWrapper<DbConfig>().eq(DbConfig::getDbCode, request.getDbCode()).ne(DbConfig::getDbId, request.getDbId()));
            if (ObjectUtil.isNotEmpty(dbConfig)) {
                throw new DBException(DBExceptionEnum.DB_UPDATE_ERROR, "已存在该数据源编码");
            }
            DbConfig config = this.baseMapper.selectOne(new LambdaQueryWrapper<DbConfig>().eq(DbConfig::getUrl, request.getUrl()).ne(DbConfig::getDbId, request.getDbId()));
            if (ObjectUtil.isNotEmpty(config)) {
                throw new DBException(DBExceptionEnum.DB_UPDATE_ERROR, "已存在相同数据源");
            }
        } else {
            DbConfig dbConfig = this.baseMapper.selectOne(new LambdaQueryWrapper<DbConfig>().eq(DbConfig::getDbCode, request.getDbCode()));
            if (ObjectUtil.isNotEmpty(dbConfig)) {
                throw new DBException(DBExceptionEnum.DB_ADD_ERROR, "已存在该数据源编码");
            }
            DbConfig config = this.baseMapper.selectOne(new LambdaQueryWrapper<DbConfig>().eq(DbConfig::getUrl, request.getUrl()));
            if (ObjectUtil.isNotEmpty(config)) {
                throw new DBException(DBExceptionEnum.DB_ADD_ERROR, "已存在相同数据源");
            }
        }
    }


    @Override
    public void addConfig(DBConfigRequest request) {
        checkConfig(request);
        request.setDriverClassName(getDbTypeEnum(request.getDbType()).getDriverName());
        DbConfig dbConfig = BeanUtil.copyProperties(request, DbConfig.class);
        dbConfig.setDatabaseName(getDataBaseName(request));
        this.baseMapper.insert(dbConfig);
    }

    @Override
    public void deleteConfig(DBConfigRequest dbConfigRequest) {
        this.baseMapper.deleteById(dbConfigRequest.getDbId());
    }

    @Override
    public void editConfig(DBConfigRequest request) {
        checkConfig(request);
        request.setDriverClassName(getDbTypeEnum(request.getDbType()).getDriverName());
        DbConfig dbConfig = BeanUtil.copyProperties(request, DbConfig.class);
        dbConfig.setDatabaseName(getDataBaseName(request));
        this.baseMapper.updateById(dbConfig);
    }

    /**
     * 获取数据库
     *
     * @param request
     * @return
     */
    String getDataBaseName(DBConfigRequest request) {
        DBConnectionUtil.testConnection(request);
        Connection connection = DBConnectionUtil.getConnection(request);
        try {
            return connection.getCatalog();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new DBException(DBExceptionEnum.DB_METADATA_ERROR, "获取数据库名失败");
        } finally {
            DbUtil.close(connection);
        }
    }



    @Override
    public PageResult getPage(DBConfigRequest dbConfigRequest) {
        LambdaQueryWrapper<DbConfig> lambdaQueryWrapper = new LambdaQueryWrapper<DbConfig>()
                .eq(StrUtil.isNotBlank(dbConfigRequest.getDbType()), DbConfig::getDbType, dbConfigRequest.getDbType())
                .and(StrUtil.isNotBlank(dbConfigRequest.getKeyword()),
                        wrapper ->
                                wrapper.like(DbConfig::getDbCode, dbConfigRequest.getKeyword())
                                        .or()
                                        .like(DbConfig::getDbName, dbConfigRequest.getKeyword()));
        Page<DbConfig> page = this.page(PageFactory.defaultPage(dbConfigRequest), lambdaQueryWrapper);
        List<DBConfigDTO> dbConfigDTOList = Convert.toList(DBConfigDTO.class, page.getRecords());
        dbConfigDTOList.forEach(item->{
            item.setPassword("***");
        });
        return PageResultFactory.createPageResult(dbConfigDTOList, page.getTotal(), Convert.toInt(page.getSize()), Convert.toInt(page.getCurrent()));
    }

    @Override
    public List<DBTypeDTO> getDbTypeList() {
        List<DBTypeDTO> dbTypeDTOList = new ArrayList<>();
        DbTypeEnum[] dbTypeEnums = DbTypeEnum.values();
        for (DbTypeEnum dbTypeEnum : dbTypeEnums) {
            DBTypeDTO dbTypeDTO = new DBTypeDTO();
            dbTypeDTO.setCode(dbTypeEnum.getCode());
            dbTypeDTO.setName(dbTypeEnum.getName());
            dbTypeDTO.setDriverName(dbTypeEnum.getDriverName());
            List<DBConfigDTO> list = this.getList(new DBConfigRequest().setDbType(dbTypeEnum.getCode()));
            dbTypeDTO.setCount(list.size());
            dbTypeDTOList.add(dbTypeDTO);
        }
        return dbTypeDTOList;
    }

}
