package com.wantedalways.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wantedalways.modules.system.entity.SysUserPost;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户职位关系表 Mapper 接口
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@Mapper
public interface SysUserPostDao extends BaseMapper<SysUserPost> {

}
