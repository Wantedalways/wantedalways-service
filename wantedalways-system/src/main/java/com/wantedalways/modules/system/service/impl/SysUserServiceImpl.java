package com.wantedalways.modules.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantedalways.common.api.vo.Result;
import com.wantedalways.common.constant.CacheConstant;
import com.wantedalways.common.constant.CommonConstant;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.common.util.RedisUtil;
import com.wantedalways.config.shiro.JwtUtil;
import com.wantedalways.modules.system.dao.SysUserDao;
import com.wantedalways.modules.system.entity.SysUser;
import com.wantedalways.modules.system.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Cacheable(cacheNames = CacheConstant.SYS_USERS_CACHE, key="#username")
    public LoginUser getUserByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserDao.selectOne(queryWrapper);
        if (sysUser == null) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);

        return loginUser;
    }

    @Override
    public Result<JSONObject> checkUserIsEffective(SysUser sysUser) {
        // 用户不存在
        if (sysUser == null) {
            return Result.error(500, "该用户不存在，请联系管理员！");
        }
        // 用户删除
        if (CommonConstant.USER_STATUS_DEL.equals(sysUser.getStatus())) {
            return Result.error(500, "该用户已删除，请联系管理员！");
        }
        // 用户禁用
        if (CommonConstant.USER_STATUS_DISABLE.equals(sysUser.getStatus())) {
            return Result.error(500, "该用户已禁用，请联系管理员！");
        }
        return Result.success();
    }

    @Override
    public void setUserInfo(SysUser sysUser, Result<JSONObject> result) {
        JSONObject object = new JSONObject(new LinkedHashMap<>());

        String username = sysUser.getUsername();
        String password = sysUser.getPassword();
        // 生成token
        String token = JwtUtil.sign(username, password);
        object.put("token", token);
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token, JwtUtil.EXPIRE_TIME * 2 / 1000);

        // 生成信息
        object.put("sysUser", sysUser);
        result.setSuccess("登录成功", object);
    }
}
