package org.wheel.sqlparser.api.exception.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.wheel.sqlparser.api.constants.SqlParserConstants;

/**
 * @author cheng
 * @version 1.0
 * @description:
 * @date 2023/10/30 23:15
 */
@Getter
@AllArgsConstructor
public enum SqlParserDatasetFileExceptionEnum implements AbstractExceptionEnum {

    /**
     * 异常错误
     */
    DEFAULT(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SqlParserConstants.EXCEPTION_STEP_CODE.DATASET_FILE + "01", "异常错误");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;
}
