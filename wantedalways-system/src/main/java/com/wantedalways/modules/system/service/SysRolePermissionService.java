package com.wantedalways.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wantedalways.modules.system.entity.SysPermission;
import com.wantedalways.modules.system.entity.SysRolePermission;

import java.util.List;

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
    List<SysPermission> getUserPermissions(String username);
}
