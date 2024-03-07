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
@TableName("ds_datset_field")
public class DsDatsetField extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
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
    @TableField("`status`")
    private String status;

    /**
     * 删除标志
     */
    private String delFlag;

}
