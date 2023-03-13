package com.wantedalways.config.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantedalways.common.api.CommonApi;
import com.wantedalways.common.api.vo.Result;
import com.wantedalways.common.constant.CacheConstant;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.common.util.RedisUtil;
import com.wantedalways.common.util.SensitiveInfoUtil;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Jwt工具类
 * @author Wantedalways
 */
public class JwtUtil {

    /**
     * Token有效期为1小时（Token在redis中缓存时间为两倍
     */
    public static final long EXPIRE_TIME = 60 * 60 * 1000;

    /**
     * 返回token验证错误
     */
    public static void responseError(ServletResponse response, Integer code, String message) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        OutputStream os = null;
        try {
            os = httpServletResponse.getOutputStream();
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setStatus(code);
            os.write(new ObjectMapper().writeValueAsString(Result.error(code, message)).getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     */
    public static String getUserIdFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取登录用户
     * @return
     */
    public static LoginUser getLoginUser(String userId, RedisUtil redisUtil, CommonApi commonApi) {
        LoginUser loginUser = null;
        String loginUserKey = CacheConstant.SYS_USERS_CACHE + "::" + userId;
        // 通过redis获取缓存用户，防止system服务问题影响到微服务间调用
        if (redisUtil.hasKey(loginUserKey)) {
            loginUser = (LoginUser) redisUtil.get(loginUserKey);
            //解密用户
            try {
                SensitiveInfoUtil.handlerObject(loginUser, false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            // 从数据库查询用户信息
            loginUser = commonApi.getUserByUserId(userId);
        }
        return loginUser;
    }

    /**
     * 检验token
     */
    public static boolean verify(String token, String userId, String password) {
        // 根据密码生成JWT效验器
        Algorithm algorithm = Algorithm.HMAC256(password);
        JWTVerifier verifier = JWT.require(algorithm).withClaim("userId", userId).build();
        // 效验TOKEN
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    /**
     * 生成加密后token
     */
    public static String sign(String userId, String password) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(password);
        // 附带username信息
        return JWT.create().withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
    }
}
