package com.wantedalways.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 用户账号（对应企业微信）
     */
    @TableField("username")
    private String username;

    /**
     * 姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 工号
     */
    @TableField("work_num")
    private String workNum;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 当前登录部门编码
     */
    @TableField("org_code")
    private Integer orgCode;

    /**
     * 密码盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 性别（0，未定义；1，男；2，女）
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 生日
     */
    @TableField("birthday")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 用户状态（0，已删除；1，正常；2，禁用）
     */
    @TableField("status")
    private Integer status;

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
