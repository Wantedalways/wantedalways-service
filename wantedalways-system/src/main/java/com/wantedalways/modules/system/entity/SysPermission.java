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
 * 权限表
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String  id;

    /**
     * 父权限id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 权限名称
     */
    @TableField("name")
    private String name;

    /**
     * 权限编码
     */
    @TableField("code")
    private String code;

    /**
     * 图表
     */
    @TableField("icon")
    private String icon;

    /**
     * 路径
     */
    @TableField("url")
    private String url;

    /**
     * 前端组件
     */
    @TableField("component")
    private String component;

    /**
     * 类型（0，一级菜单；1，子菜单；2，按钮权限）
     */
    @TableField("type")
    private Integer type;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 是否路由菜单（0，否；1，是）
     */
    @TableField("is_route")
    private Boolean route;

    /**
     * 是否缓存（0，否；1，是）
     */
    @TableField("is_cache")
    private Boolean cache;

    /**
     * 状态（0，删除；1，正常；2，禁用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否隐藏（0，否；1，是）
     */
    @TableField("is_hidden")
    private Boolean hidden;

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
