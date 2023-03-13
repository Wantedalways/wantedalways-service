package com.wantedalways.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.modules.system.entity.SysUser;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-02
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取加密后的登录用户信息
     * @param userId 用户账号
     * @return 登录用户信息
     */
    LoginUser getEncodeUserInfoByUserId(String userId);
}
