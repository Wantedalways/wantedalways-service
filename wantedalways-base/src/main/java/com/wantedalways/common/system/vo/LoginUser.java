package com.wantedalways.common.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 在线用户信息类
 * @author Wantedalways
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUser {

    /**
     * 主键id
     */
    private String id;

    /**
     * 用户名（对应企业微信）
     */
    private String userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 工号
     */
    private String workNum;

    /**
     * 密码
     */
    private String password;

    /**
     * 当前登录部门(企业微信部门id)
     */
    private Integer orgCode;

    /**
     * 职务
     */
    private String position;

    /**
     * 性别（0，未定义；1，男；2，女）
     */
    private Integer gender;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 用户状态（0，已删除；1，正常；2，禁用）
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
