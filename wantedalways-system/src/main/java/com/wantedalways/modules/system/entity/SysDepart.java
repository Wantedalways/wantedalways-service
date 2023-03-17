package com.wantedalways.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_depart")
public class SysDepart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 部门名称
     */
    @TableField("name")
    private String name;

    /**
     * 父部门id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 企业微信id
     */
    @TableField("work_wx_id")
    private Integer workWxId;

    /**
     * 部门编码
     */
    @TableField("code")
    private String code;

    /**
     * 排序
     */
    @TableField("order")
    private Integer order;

    /**
     * 层级
     */
    @TableField("level")
    private Integer level;

    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 修改人
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

}
