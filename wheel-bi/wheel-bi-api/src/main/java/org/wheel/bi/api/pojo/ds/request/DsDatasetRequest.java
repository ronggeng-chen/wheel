package org.wheel.bi.api.pojo.ds.request;

import cn.stylefeng.roses.kernel.rule.pojo.request.BaseRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 数据集
 *
 * @className: DsDataset
 * @description:
 * @author: aGeng
 * @date: 2024-03-07 23:00:13
 **/
@Getter
@Setter
@Accessors(chain = true)

public class DsDatasetRequest extends BaseRequest {


    /**
     * 主键
     */
    private Long id;

    /**
     * 数据集编码
     */
    private String datsetCode;

    /**
     * 数据集名称
     */
    private String datsetName;

    /**
     * 数据集类型
     */
    private String datsetType;

    /**
     * 数据库编码
     */
    private String dbCode;

    /**
     * 数据集配置
     */
    private String datsetConfig;

    /**
     * 数据集描述
     */
    private String datsetDescription;

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
