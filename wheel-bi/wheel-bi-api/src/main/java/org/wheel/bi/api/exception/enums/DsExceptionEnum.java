package org.wheel.bi.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wheel.bi.api.constants.DsConstants;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:15
 */
@Getter
@AllArgsConstructor
public enum DsExceptionEnum implements AbstractExceptionEnum {

    /**
     * 异常错误
     */
    DEFAULT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DsConstants.EXCEPTION_STEP_CODE.DATASET + "01", "异常错误;{}"),
    /**
     * 参数错误
     */
    PARAM_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DsConstants.EXCEPTION_STEP_CODE.DATASET + "02", "参数错误;{}"),

    DATASET_01(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DsConstants.EXCEPTION_STEP_CODE.DATASET + "03", "无效数据集类型;{}");


    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;
}
