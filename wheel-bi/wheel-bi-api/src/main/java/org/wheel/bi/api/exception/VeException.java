package org.wheel.bi.api.exception;

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
public class VeException extends ServiceException {

    public VeException(AbstractExceptionEnum exception, Object... params) {
        super(BIConstants.MODULE_NAME, exception.getErrorCode(), StrUtil.format(exception.getUserTip(), params));
    }

    public VeException(AbstractExceptionEnum exception) {
        super(BIConstants.EXCEPTION_STEP_CODE.CFG, exception);
    }

}