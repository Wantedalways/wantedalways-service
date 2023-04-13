package com.wantedalways.common.constant;

/**
 * 缓存常量
 * @author Wantedalways
 */
public interface CacheConstant {

    /**
     * 登录用户信息缓存
     */
    String SYS_USERS_CACHE = "sys:cache:user";

    /**
     * 用户角色列表缓存
     */
    String SYS_USER_CACHE_ROLES = "sys:cache:user:role";

    /**
     * shiro用户缓存
     */
    String PREFIX_USER_SHIRO_CACHE = "shiro:cache:com.wantedalways.config.shiro.ShiroRealm.authorizationCache:";

    /**
     * 字典表缓存
     */
    String SYS_DICT_TABLE_CACHE = "sys:cache:dictTable";
}
