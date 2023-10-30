package org.wheel.datasource.api.util;

import cn.hutool.db.DbUtil;
import lombok.extern.slf4j.Slf4j;
import org.wheel.datasource.api.enums.DBExceptionEnum;
import org.wheel.datasource.api.exception.DBException;
import org.wheel.datasource.api.request.DBConfigRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

@Slf4j
public class DBConnectionUtil {

    /**
     * 测试连接
     *
     * @param
     * @return
     */
    public static boolean getTestConnection(DBConfigRequest request) {
        Connection conn = null;
        try {
            Class.forName(request.getDriverClassName());
            conn = DriverManager.getConnection(request.getUrl(), request.getUsername(), request.getPassword());
        } catch (Exception e) {
            return false;
        } finally {
            DbUtil.close(conn);
        }
        return true;
    }

    /**
     * 测试连接
     *
     * @param
     * @return
     */
    public static void testConnection(DBConfigRequest request) {
        Connection conn = null;
        try {
            Class.forName(request.getDriverClassName());
            conn = DriverManager.getConnection(request.getUrl(), request.getUsername(), request.getPassword());
            conn.setSchema(request.getSchemaName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new DBException(DBExceptionEnum.DB_CONNECTION_ERROR, "连接失败!{}", e.getMessage());
        } finally {
            DbUtil.close(conn);
        }
    }

    /**
     * 测试连接
     *
     * @param
     * @return
     */
    public static Connection getConnection(DBConfigRequest request) {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.setProperty("user", request.getUsername());
            props.setProperty("password", request.getPassword());
            props.setProperty("useInformationSchema", "true");
            Class.forName(request.getDriverClassName());
            conn = DriverManager.getConnection(request.getUrl(), props);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return conn;
    }

    private static Connection resetConnection(DBConfigRequest request){
        Connection conn = null;
        Long second=1000l;
        for(int i=1;i<=3;i++){
            try {
                Properties props = new Properties();
                props.setProperty("username", request.getUsername());
                props.setProperty("password", request.getPassword());
                props.setProperty("useInformationSchema", "true");
                Class.forName(request.getDriverClassName());
                conn = DriverManager.getConnection(request.getUrl(), props);
                log.info("重试链接成功【{}】次",1);
                return conn;
            } catch (Exception exception) {
                exception.printStackTrace();
                log.info("重试链接失败【{}】次",1);
                try {
                    Thread.sleep(second);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if(conn==null){
            throw new DBException(DBExceptionEnum.DB_CONNECTION_ERROR, "测试连接重试失败!");
        }
        return conn;
    }


}
