package com.wantedalways.config.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
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

/**
 * Jwt工具类
 * @author Wantedalways
 */
public class JwtUtil {

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
    public static LoginUser getLoginUser(String userId, RedisUtil redisUtil) {
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
            // 查询用户信息
            loginUser = commonApi.getUserByName(username);
        }
        return loginUser;
    }
}
