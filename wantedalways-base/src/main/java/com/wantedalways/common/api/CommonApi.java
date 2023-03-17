package com.wantedalways.common.api;

import com.wantedalways.common.system.vo.LoginUser;

import java.util.Set;

/**
 * 通用api接口
 * @author Wantedalways
 */
public interface CommonApi {

    /**
     * 根据用户账号获取登录用户
     * @param username 用户账号
     * @return 登录用户
     */
    LoginUser getUserByUsername(String username);

    /**
     * 查询用户角色信息
     * @param username 用户账号
     * @return 角色集合
     */
    Set<String> getUserRoles(String username);

    /**
     * 查询用户权限信息
     * @param username 用户账号
     * @return 权限集合
     */
    Set<String> getUserPermissions(String username);
}
