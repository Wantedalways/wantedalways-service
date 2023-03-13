package com.wantedalways.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wantedalways.modules.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 Mapper 接口
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-13
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {

    /**
     * 查询用户角色信息
     * @param userId 用户账号
     * @return 角色集合
     */
    List<String> selectRoleByUserId(@Param("userId") String userId);
}
