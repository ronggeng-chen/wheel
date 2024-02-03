package org.wheel.sqlparser.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import org.wheel.sqlparser.api.constants.SqlParserConstants;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:13
 */
public class SqlParserCubeException extends ServiceException {

    public SqlParserCubeException(AbstractExceptionEnum exception, Object... params) {
        super(SqlParserConstants.MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public SqlParserCubeException(AbstractExceptionEnum exception) {
        super(SqlParserConstants.EXCEPTION_STEP_CODE.CUBE, exception);
    }

}