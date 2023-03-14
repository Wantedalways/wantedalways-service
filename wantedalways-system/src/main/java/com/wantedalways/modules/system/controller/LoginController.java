package com.wantedalways.modules.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.wantedalways.common.api.vo.Result;
import com.wantedalways.common.util.RandImageUtil;
import com.wantedalways.common.util.RedisUtil;
import com.wantedalways.common.util.encryption.Md5Util;
import com.wantedalways.config.BaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 登录前端控制器
 *
 * @author Wantedalways
 */
@Slf4j
@RestController
@RequestMapping("/sys")
public class LoginController {

    @Autowired
    private BaseConfig baseConfig;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/getCaptcha")
    public Result<String> getCaptcha(String key) {

        // 生成验证码
        String baseCheckCodes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String code = RandomUtil.randomString(baseCheckCodes, 4);

        // 转小写存入redis
        String lowerCaseCode = code.toLowerCase();
        // 加入密钥作为混淆
        String origin = lowerCaseCode + key + baseConfig.getSignatureSecret();
        String realKey = Md5Util.md5Encode(origin, "utf-8");
        redisUtil.set(realKey, lowerCaseCode, 60);
        log.info("获取验证码，Redis key = {}，checkCode = {}", realKey, code);

        try {
            // 生成base64字符串
            String base64Code = RandImageUtil.generate(code);
            return Result.success("获取验证码成功！", base64Code);
        } catch (IOException e) {
            return Result.error(500, "获取验证码失败，请联系管理员！");
        }
    }
}
