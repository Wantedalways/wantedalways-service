package com.wantedalways.modules.system.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wantedalways.common.api.vo.Result;
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
    LoginUser getUserByUserId(String userId);

    /**
     * 检验用户有效性
     * @param sysUser 用户对象
     * @return 检验结果
     */
    Result<JSONObject> checkUserIsEffective(SysUser sysUser);

    /**
     * 生成用户登录信息
     * @param sysUser 用户对象
     * @param result 登录结果
     */
    void setUserInfo(SysUser sysUser, Result<JSONObject> result);
}
