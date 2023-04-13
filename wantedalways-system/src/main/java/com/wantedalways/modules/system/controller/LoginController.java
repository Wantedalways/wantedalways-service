package com.wantedalways.modules.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wantedalways.common.api.CommonApi;
import com.wantedalways.common.api.vo.Result;
import com.wantedalways.common.constant.CacheConstant;
import com.wantedalways.common.constant.CommonConstant;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.common.util.CaptchaUtil;
import com.wantedalways.common.util.RedisUtil;
import com.wantedalways.common.util.encryption.Md5Util;
import com.wantedalways.common.util.encryption.PasswordUtil;
import com.wantedalways.config.BaseConfig;
import com.wantedalways.config.shiro.JwtUtil;
import com.wantedalways.modules.system.entity.SysUser;
import com.wantedalways.modules.system.model.SysLoginModel;
import com.wantedalways.modules.system.service.SysUserService;
import com.wantedalways.modules.system.service.impl.SysBaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录前端控制器
 *
 * @author Wantedalways
 */
@Slf4j
@RestController
@RequestMapping("/sys/login")
public class LoginController {

    @Autowired
    private BaseConfig baseConfig;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysBaseServiceImpl sysBaseService;

    /**
     * 获取验证码
     */
    @GetMapping("/getCaptcha")
    public Result<String> getCaptcha(@RequestParam String key) {

        // 生成验证码
        String captcha = RandomUtil.randomString(CommonConstant.BASE_CHECK_CODES, 4);

        // 转小写存入redis
        String lowerCaseCaptcha = captcha.toLowerCase();
        // 加入密钥作为混淆
        String origin = lowerCaseCaptcha + key + baseConfig.getSignatureSecret();
        String realKey = Md5Util.md5Encode(origin, "utf-8");
        redisUtil.set(realKey, lowerCaseCaptcha, 120);

        try {
            // 生成base64字符串
            String base64Code = CaptchaUtil.generate(captcha);
            return Result.success("获取验证码成功！", base64Code);
        } catch (IOException e) {
            return Result.error(500, "获取验证码失败，请联系管理员！");
        }
    }

    /**
     * 账号或手机号登录
     */
    @PostMapping("/accountLogin")
    public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel) {
        Result<JSONObject> result = new Result<>();
        // 校验验证码
        String captcha = sysLoginModel.getCaptcha();
        String lowerCaseCaptcha = captcha.toLowerCase();
        String origin = lowerCaseCaptcha + sysLoginModel.getCheckKey() + baseConfig.getSignatureSecret();
        String realKey = Md5Util.md5Encode(origin, "utf-8");
        String checkCode = (String) redisUtil.get(realKey);
        if (!lowerCaseCaptcha.equals(checkCode)) {
            return result.setError(HttpStatus.PRECONDITION_FAILED.value(), "验证码错误！");
        }

        SysUser sysUser = null;
        String username = null;
        int type = sysLoginModel.getType();
        if (type == 0 ) {
            // 用户名登录
            username = sysLoginModel.getAccount();
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getUsername, username);
            sysUser = sysUserService.getOne(queryWrapper);
        }
        if (type == 1) {
            // 手机号登录
            String phone = sysLoginModel.getAccount();
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getPhone, phone);
            sysUser = sysUserService.getOne(queryWrapper);
            username = sysUser.getUsername();
        }

        // 校验用户有效性
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.getSuccess()) {
            return result;
        }

        // 校验密码
        String userPassword = PasswordUtil.encrypt(username, sysLoginModel.getPassword(), sysUser.getSalt());
        String sysPassword = sysUser.getPassword();
        if (!sysPassword.equals(userPassword)) {
            return result.setError(500, "用户名或密码错误！");
        }

        // 生成登录信息
        sysUserService.setUserInfo(sysUser, result);

        // 删除验证码缓存
        redisUtil.del(realKey);

        return result;
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Result<?> logout(HttpServletRequest request) {
        String token = request.getHeader(CommonConstant.ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return Result.error(500, "退出登录失败！");
        }

        String username = JwtUtil.getUsernameFromToken(token);
        LoginUser loginUser = sysBaseService.getUserByUsername(username);
        if (loginUser != null) {
            // 清除token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            // 清除shiro缓存
            redisUtil.del(CacheConstant.PREFIX_USER_SHIRO_CACHE + loginUser.getId());
            // 清除用户信息缓存
            redisUtil.del(CacheConstant.SYS_USERS_CACHE + "::" + loginUser.getUsername());

            SecurityUtils.getSubject().logout();
            return Result.success("退出登录成功！");
        } else {
            return Result.error(500, "token无效！");
        }
    }
}
