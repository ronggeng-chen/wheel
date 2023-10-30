package org.wheel.datasource.api.enums;

import cn.stylefeng.roses.kernel.rule.constants.RuleConstants;
import cn.stylefeng.roses.kernel.rule.exception.AbstractExceptionEnum;
import lombok.Getter;
import org.wheel.datasource.api.constants.DBConstants;

import static org.wheel.datasource.api.constants.DBConstants.EXCEPTION_STEP_CODE;


/**
 *  异常定义类
 *
 * @author aGeng
 * @date 2022/3/9 9:59
 **/
@Getter
public enum DBExceptionEnum implements AbstractExceptionEnum {


    DB_UPDATE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DBConstants.EXCEPTION_STEP_CODE + "01", "修改错误"),

    DB_ADD_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DBConstants.EXCEPTION_STEP_CODE +"02", "添加错误"),

    DB_DELETE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DBConstants.EXCEPTION_STEP_CODE +"03", "删除错误"),

    DB_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DBConstants.EXCEPTION_STEP_CODE +"04", "异常错误"),

    DB_CONNECTION_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DBConstants.EXCEPTION_STEP_CODE +"05","连接异常"),

    DB_EXECUTE_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DBConstants.EXCEPTION_STEP_CODE +"06","执行SQL错误"),

    DB_SQL_PARSER_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DBConstants.EXCEPTION_STEP_CODE +"07","SQL解析异常"),

    DB_METADATA_ERROR(RuleConstants.BUSINESS_ERROR_TYPE_CODE + DBConstants.EXCEPTION_STEP_CODE +"08","获取源数据错误"),
    ;
    /**
     * 错误编码
     */
    private final String errorCode;

    /**
     * 提示用户信息
     */
    private final String userTip;

    DBExceptionEnum(String errorCode, String userTip) {
        //步数
        this.errorCode = RuleConstants.BUSINESS_ERROR_TYPE_CODE + EXCEPTION_STEP_CODE + errorCode;
        this.userTip = userTip;
    }

}
