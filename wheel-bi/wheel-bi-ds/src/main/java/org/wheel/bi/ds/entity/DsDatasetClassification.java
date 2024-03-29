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
 * 数据集分类
 *
 * @className: DsDatasetClassification
 * @description:
 * @author: aGeng
 * @date: 2024-03-07 23:00:13
 **/
@Getter
@Setter
@Accessors(chain = true)
@TableName("ds_dataset_classification")
public class DsDatasetClassification extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 分类编码
     */
    private String classifCode;

    /**
     * 分类名称
     */
    private String classifName;

    /**
     * 分类描述
     */
    private String classifDescription;

    /**
     * 父节点
     */
    private Long parentId;

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
