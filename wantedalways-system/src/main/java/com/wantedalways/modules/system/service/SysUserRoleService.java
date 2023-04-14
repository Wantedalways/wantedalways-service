package com.wantedalways.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wantedalways.modules.system.entity.SysRole;
import com.wantedalways.modules.system.entity.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务类
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-13
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户id查询权限列表
     * @param userId 用户id
     * @return 用户权限列表
     */
    List<SysRole> queryRolesByUserId(String userId);
}
