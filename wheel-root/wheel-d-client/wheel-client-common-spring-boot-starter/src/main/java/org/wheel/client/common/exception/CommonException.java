package org.wheel.client.common.exception;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import cn.stylefeng.roses.kernel.rule.exception.base.ServiceException;
import org.wheel.client.common.constant.CommConstant;

/**
 * 异常
 */
public class CommonException extends ServiceException {

    public CommonException(String errorCode, String userTip) {
        super(CommConstant.COMMON_MODULE_NAME, errorCode, userTip);
    }

    public CommonException(AbstractExceptionEnum exceptionEnum, String userTip, Object ... param) {
        super(CommConstant.COMMON_MODULE_NAME, exceptionEnum.getErrorCode(), StrUtil.format(userTip,param));
    }

    public CommonException(AbstractExceptionEnum exceptionEnum) {
        super(CommConstant.COMMON_MODULE_NAME, exceptionEnum);
    }

}
