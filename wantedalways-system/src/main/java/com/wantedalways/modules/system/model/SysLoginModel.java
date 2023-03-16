package com.wantedalways.modules.system.model;

import lombok.Data;

/**
 * 登录对象
 * @author Wantedalways
 */
@Data
public class SysLoginModel {

    /**
     * 用户名
     */
    private String userId;

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
