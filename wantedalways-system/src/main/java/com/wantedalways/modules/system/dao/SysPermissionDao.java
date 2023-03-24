package com.wantedalways.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wantedalways.modules.system.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@Mapper
public interface SysPermissionDao extends BaseMapper<SysPermission> {

}
