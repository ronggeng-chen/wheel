package org.wheel.bi.ds.entity;

import cn.stylefeng.roses.kernel.db.api.pojo.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;


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
@TableName("ds_dataset")
public class DsDataset extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    @TableField("`status`")
    private String status;

    /**
     * 删除标志
     */
    private String delFlag;


}
