package org.wheel.bi.api.pojo.ds.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 数据集字段
 *
 * @className: DsDatsetField
 * @description:
 * @author: aGeng
 * @date: 2024-03-07 23:00:13
 **/
@Getter
@Setter
@Accessors(chain = true)

public class DsDatsetFieldRequest extends BaseRequest {


    /**
     * 主键
     */
    private Long id;

    /**
     * 字段编码
     */
    private String fieldCode;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * SQL片段
     */
    private String sqlSegment;

    /**
     * 描述
     */
    private String fieldDescription;

    /**
     * 排序
     */
    private BigDecimal sort;

    /**
     * 状态
     */
    private String status;

    /**
     * 删除标志
     */
    private String delFlag;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private Long updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;
}
