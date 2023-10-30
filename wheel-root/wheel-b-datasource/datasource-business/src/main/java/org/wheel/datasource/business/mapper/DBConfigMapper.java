package org.wheel.datasource.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.wheel.datasource.business.po.DbConfig;

import java.util.List;
import java.util.Map;

public interface DBConfigMapper extends BaseMapper<DbConfig> {

    @Select("SELECT" +
            "    fieldCode     = A.name," +
            "     fieldRemark   = isnull(G.[value],'') FROM syscolumns A" +
            " Left Join systypes B On A.xusertype=B.xusertype" +
            " Inner Join sysobjects D On A.id=D.id  and D.xtype='U' and  D.name<>'dtproperties'" +
            " Left Join syscomments E on A.cdefault=E.id" +
            " Left Join sys.extended_properties  G on A.id=G.major_id and A.colid=G.minor_id" +
            " Left Join sys.extended_properties F On D.id=F.major_id and F.minor_id=0" +
            " where d.name='#{tableCode}'" +
            " Order By A.id,A.colorder")
    List<Map<String,String>> getRemark(String tableCode);

}
