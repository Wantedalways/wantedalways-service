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
     * @param userId 用户账号
     * @return 登录用户
     */
    LoginUser getUserByUserId(String userId);

    /**
     * 查询用户角色信息
     * @param userId 用户账号
     * @return 角色集合
     */
    Set<String> getUserRoles(String userId);

    /**
     * 查询用户权限信息
     * @param userId 用户账号
     * @return 权限集合
     */
    Set<String> getUserPermissions(String userId);
}
