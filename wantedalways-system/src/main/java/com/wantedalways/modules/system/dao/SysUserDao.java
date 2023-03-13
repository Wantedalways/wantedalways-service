package com.wantedalways.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wantedalways.modules.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-02
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {

    /**
     * 获取加密后的登录用户信息
     * @param userId 用户账号
     * @return 登录用户信息
     */
    SysUser selectUserByUserId(@Param("userId") String userId);
}
