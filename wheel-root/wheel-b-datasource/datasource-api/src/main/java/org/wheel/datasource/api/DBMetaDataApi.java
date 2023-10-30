package org.wheel.datasource.api;

import org.wheel.datasource.api.dto.DBFieldDTO;
import org.wheel.datasource.api.dto.DBTableDTO;
import org.wheel.datasource.api.request.DBMetaDataRequest;

import java.util.List;

public interface DBMetaDataApi {
    /**
     * 获取数据库列表
     *
     * @param dbMetaDataRequest{ dbId,dbCode
     *                           }
     * @return
     */
    List<String> getDatabaseList(DBMetaDataRequest dbMetaDataRequest) throws Exception;

    /**
     * 获取模式名
     *
     * @param dbMetaDataRequest{ dbId,dbCode,databaseName
     *                           }
     * @return
     */
    List<String> getSchemaList(DBMetaDataRequest dbMetaDataRequest) throws Exception;

    /**
     * 获取表
     *
     * @param dbMetaDataRequest{ dbId,dbCode,databaseName,schemaName(非必填，默认为实例默认模式),tableCode(模糊筛选),isQueryView(null:所有,true:只查询视图，false：只查询表)
     *                           }
     * @return
     */
    List<DBTableDTO> getTableList(DBMetaDataRequest dbMetaDataRequest) throws Exception;

    /**
     * 获取列
     *
     * @param dbMetaDataRequest{ dbId,dbCode,databaseName,schemaName(非必填，默认为实例默认模式),tableCode
     *                           }
     * @return
     */
    List<DBFieldDTO> getFieldList(DBMetaDataRequest dbMetaDataRequest) throws Exception;
}
