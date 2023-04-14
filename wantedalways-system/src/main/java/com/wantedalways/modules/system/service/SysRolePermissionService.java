package com.wantedalways.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wantedalways.modules.system.entity.SysPermission;
import com.wantedalways.modules.system.entity.SysRolePermission;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色权限关系表 服务类
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 获取当前登录用户的权限列表
     * @param username 当前登录用户的用户名
     * @return 权限列表
     */
    Set<SysPermission> getUserPermissions(String username);

    /**
     * 为单个角色设置权限（对比差异，完成增删）
     * @param roleId 角色id
     * @param newPermissionIds 新权限id列表
     * @param oldPermissionIds 旧权限id列表
     */
    void setForRole(String roleId, List<String> newPermissionIds, List<String> oldPermissionIds);
}
