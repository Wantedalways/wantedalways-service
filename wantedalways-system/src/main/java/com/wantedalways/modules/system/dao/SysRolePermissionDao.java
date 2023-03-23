package com.wantedalways.modules.system.dao;

import com.wantedalways.modules.system.entity.SysRolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
     * 查询用户权限信息
     * @param roleSet 角色集合
     * @return 权限集合
     */
    List<String> selectUserPermissions(@Param("roleSet") Set<String> roleSet);
}
