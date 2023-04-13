package com.wantedalways.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wantedalways.modules.system.entity.SysPermission;
import com.wantedalways.modules.system.entity.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色权限关系表 Mapper 接口
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@Mapper
public interface SysRolePermissionDao extends BaseMapper<SysRolePermission> {

    /**
     * 查询用户权限编码信息
     * @param roleSet 角色编码集合
     * @return 权限集合
     */
    List<String> selectUserPermissions(@Param("roleSet") Set<String> roleSet);

    /**
     * 查询用户权限列表
     * @param roleSet 角色编码集合
     * @return 权限列表
     */
    List<SysPermission> selectUserPermissionsList(Set<String> roleSet);
}
