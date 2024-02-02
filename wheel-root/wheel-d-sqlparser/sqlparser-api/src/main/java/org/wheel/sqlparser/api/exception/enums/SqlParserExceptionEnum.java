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
public enum SqlParserExceptionEnum implements AbstractExceptionEnum {

    /**
     * 异常错误
     */
    B101001(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SqlParserConstants.EXCEPTION_STEP_CODE.BASE + "01", "异常错误"),

    SQL_PARSER_PARAM_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SqlParserConstants.EXCEPTION_STEP_CODE.BASE + "02", "SQL Parser 参数错误: {}"),

    SQL_PARSER_TABLE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SqlParserConstants.EXCEPTION_STEP_CODE.BASE + "03", "SQL Parser 解析源表失败: {}"),

    SQL_PARSER_SQL_INJECTION(RuleConstants.BUSINESS_ERROR_TYPE_CODE + SqlParserConstants.EXCEPTION_STEP_CODE.BASE + "04", "SQL Parser SQL存在注入风险: {}");

    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;
}
