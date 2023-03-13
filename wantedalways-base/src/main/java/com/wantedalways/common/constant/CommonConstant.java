package com.wantedalways.common.constant;

/**
 * 公共常量
 * @author Wantedalways
 */
public interface CommonConstant {

    /**
     * JwtToken
     */
    String ACCESS_TOKEN = "Access-Token";

    /**
     * 用户Token缓存KEY前缀
     */
    String PREFIX_USER_TOKEN  = "prefix_user_token_";

    /**
     * 用户状态：删除
     */
    Integer USER_STATUS_DEL = 0;

    /**
     * 用户状态：正常
     */
    Integer USER_STATUS_NORMAL = 1;

    /**
     * 用户状态：禁用
     */
    Integer USER_STATUS_DISABLE = 2;

}
