package org.wheel.datasource.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import org.wheel.datasource.api.dto.DBConfigDTO;
import org.wheel.datasource.api.dto.DBFieldDTO;
import org.wheel.datasource.api.dto.DBTableDTO;
import org.wheel.datasource.api.enums.DBExceptionEnum;
import org.wheel.datasource.api.exception.DBException;
import org.wheel.datasource.api.request.DBConfigRequest;
import org.wheel.datasource.api.request.DBMetaDataRequest;
import org.wheel.datasource.api.util.DBMetaData;
import org.wheel.datasource.business.service.DBConfigService;
import org.wheel.datasource.business.service.DBMetaDataService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DBMetaDataServiceImpl implements DBMetaDataService {

    @Resource
    DBConfigService dbConfigService;
    //######################################API########################################

    private DBConfigRequest getConfigRequest(DBMetaDataRequest dbMetaDataRequest) {
        DBConfigRequest configRequest = new DBConfigRequest();
        configRequest.setDbId(dbMetaDataRequest.getDbId());
        configRequest.setDbCode(dbMetaDataRequest.getDbCode());
        DBConfigDTO dbConfig = dbConfigService.getConfig(configRequest);
        if (ObjectUtil.isEmpty(dbConfig)) {
            throw new DBException(DBExceptionEnum.DB_METADATA_ERROR, "找不到数据源配置");
        }
        return BeanUtil.copyProperties(dbConfig, DBConfigRequest.class);
    }

    @Override
    public List<String> getDatabaseList(DBMetaDataRequest dbMetaDataRequest) throws Exception {
        List<String> databaseList ;
        DBConfigRequest configRequest = getConfigRequest(dbMetaDataRequest);
        databaseList = DBMetaData.getDBMetaDataFactory(configRequest).getDataBaseList();
        return databaseList;
    }

    @Override
    public List<String> getSchemaList(DBMetaDataRequest dbMetaDataRequest) throws Exception {
        List<String> schemaList = new ArrayList<>();
        DBConfigRequest configRequest = getConfigRequest(dbMetaDataRequest);
        schemaList = DBMetaData.getDBMetaDataFactory(configRequest).getSchemaList(dbMetaDataRequest.getDatabaseName());
        return schemaList;
    }

    @Override
    public List<DBTableDTO> getTableList(DBMetaDataRequest dbMetaDataRequest) throws Exception {
        List<DBTableDTO> tableDTOS = new ArrayList<>();
        DBConfigRequest configRequest = getConfigRequest(dbMetaDataRequest);
        String[] types;
        if (ObjectUtil.isEmpty(dbMetaDataRequest.getIsQueryView())) {
            types = new String[]{"TABLE", "VIEW"};
        } else if (dbMetaDataRequest.getIsQueryView()) {
            types = new String[]{"VIEW"};
        } else {
            types = new String[]{"TABLE"};
        }
        tableDTOS = DBMetaData.getDBMetaDataFactory(configRequest)
                .getAllTableList(dbMetaDataRequest.getDatabaseName(), dbMetaDataRequest.getSchemaName(), dbMetaDataRequest.getTableCode(), types);
        return tableDTOS;
    }

    @Override
    public List<DBFieldDTO> getFieldList(DBMetaDataRequest dbMetaDataRequest) throws Exception {
        List<DBFieldDTO> dbFieldDTOS = new ArrayList<>();
        DBConfigRequest configRequest = getConfigRequest(dbMetaDataRequest);
        dbFieldDTOS = DBMetaData.getDBMetaDataFactory(configRequest)
                .getFieldList(dbMetaDataRequest.getDatabaseName(), dbMetaDataRequest.getSchemaName(), dbMetaDataRequest.getTableCode());
        return dbFieldDTOS;
    }
    //######################################API########################################
}
