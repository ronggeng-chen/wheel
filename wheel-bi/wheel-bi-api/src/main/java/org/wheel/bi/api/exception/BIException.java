package org.wheel.sqlparser.api.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import org.wheel.sqlparser.api.constants.BIConstants;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:13
 */
public class BIException extends ServiceException {

    public BIException(AbstractExceptionEnum exception, Object... params) {
        super(BIConstants.MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public BIException(AbstractExceptionEnum exception) {
        super(BIConstants.EXCEPTION_STEP_CODE.BASE, exception);
    }

}