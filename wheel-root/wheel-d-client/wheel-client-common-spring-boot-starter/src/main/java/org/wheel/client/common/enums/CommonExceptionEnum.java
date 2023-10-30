package org.wheel.client.common.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;

import static org.wheel.client.common.constant.CommConstant.COMMON_EXCEPTION_STEP_CODE;


/**
 *  异常定义类
 *
 * @author aGeng
 * @date 2022/3/9 9:59
 **/
@Getter
public enum CommonExceptionEnum implements AbstractExceptionEnum {


    COMMON_ERROR("01", "操作错误"),

    COMMON_PARAM_ERROR("02", "参数错误")
    ;
    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    CommonExceptionEnum(String errorCode, String userTip) {
        //步数
        this.errorCode = RuleConstants.BUSINESS_ERROR_TYPE_CODE + COMMON_EXCEPTION_STEP_CODE + errorCode;
        this.userTip = userTip;
    }

}
