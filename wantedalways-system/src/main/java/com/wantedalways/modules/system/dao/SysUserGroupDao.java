package com.wantedalways.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wantedalways.modules.system.entity.SysUserGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户用户组关系表 Mapper 接口
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@Mapper
public interface SysUserGroupDao extends BaseMapper<SysUserGroup> {

}
