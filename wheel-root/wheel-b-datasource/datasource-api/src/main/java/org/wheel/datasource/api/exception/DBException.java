package org.wheel.datasource.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import org.wheel.datasource.api.constants.DBConstants;

/**
 * 异常
 */
public class DBException extends ServiceException {

    public DBException(String errorCode, String userTip) {
        super(DBConstants.MODULE_NAME, errorCode, userTip);
    }

    public DBException(AbstractExceptionEnum exceptionEnum, String userTip,Object ... param) {
        super(DBConstants.MODULE_NAME, exceptionEnum.getErrorCode(), StrUtil.format(userTip,param));
    }

    public DBException(AbstractExceptionEnum exceptionEnum) {
        super(DBConstants.MODULE_NAME, exceptionEnum);
    }

}
