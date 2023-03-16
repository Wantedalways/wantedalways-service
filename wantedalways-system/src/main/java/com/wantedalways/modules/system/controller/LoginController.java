package com.wantedalways.modules.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wantedalways.common.api.vo.Result;
import com.wantedalways.common.constant.CommonConstant;
import com.wantedalways.common.util.CaptchaUtil;
import com.wantedalways.common.util.RedisUtil;
import com.wantedalways.common.util.SpringContextUtil;
import com.wantedalways.common.util.encryption.Md5Util;
import com.wantedalways.common.util.encryption.PasswordUtil;
import com.wantedalways.config.BaseConfig;
import com.wantedalways.modules.system.entity.SysUser;
import com.wantedalways.modules.system.model.SysLoginModel;
import com.wantedalways.modules.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 获取验证码
     */
    @GetMapping("/getCaptcha")
    public Result<String> getCaptcha(String key) {

        // 生成验证码
        String captcha = RandomUtil.randomString(CommonConstant.BASE_CHECK_CODES, 4);

        // 转小写存入redis
        String lowerCaseCaptcha = captcha.toLowerCase();
        // 加入密钥作为混淆
        String origin = lowerCaseCaptcha + key + baseConfig.getSignatureSecret();
        String realKey = Md5Util.md5Encode(origin, "utf-8");
        redisUtil.set(realKey, lowerCaseCaptcha, 60);

        try {
            // 生成base64字符串
            String base64Code = CaptchaUtil.generate(captcha);
            return Result.success("获取验证码成功！", base64Code);
        } catch (IOException e) {
            return Result.error(500, "获取验证码失败，请联系管理员！");
        }
    }

    /**
     * 账号登录
     */
    @PostMapping("/idLogin")
    public Result<JSONObject> idLogin(@RequestBody SysLoginModel sysLoginModel) {
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

        String userId = sysLoginModel.getUserId();
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserId, userId);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        // 校验用户有效性
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.getSuccess()) {
            return result;
        }
        // 校验密码
        String userPassword = PasswordUtil.encrypt(userId, sysLoginModel.getPassword(), sysUser.getSalt());
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
}
