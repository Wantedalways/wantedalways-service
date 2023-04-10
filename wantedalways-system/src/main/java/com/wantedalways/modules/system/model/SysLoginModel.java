package com.wantedalways.modules.system.model;

import lombok.Data;

/**
 * 登录对象
 * @author Wantedalways
 */
@Data
public class SysLoginModel {

    /**
     * 登录类型（0，用户名登录；1，手机号登录）
     */
    private Integer type;

    /**
     * 用户信息
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 验证码key
     */
    private String checkKey;
}
