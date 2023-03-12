package com.wantedalways.config.shiro;

import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.common.util.RedisUtil;
import com.wantedalways.config.vo.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户鉴权和授权
 * @author Wantedalways
 */
@Slf4j
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Lazy
    @Resource
    private RedisUtil redisUtil;

    /**
     * 限定只处理JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 权限认证，触发权限验证时调用该方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("认证权限...");

        String userId = null;
        if (principalCollection != null) {

        }

        log.info("权限认证成功！");
        return null;
    }

    /**
     * 身份认证，登陆时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("认证身份...");

        String token = (String) authenticationToken.getCredentials();
        if (token == null) {
            throw new AuthenticationException("token为空！");
        }

        // 校验token有效性
        LoginUser loginUser = null;
        try {
            loginUser = this.checkUserTokenIsEffect(token);
        } catch (AuthenticationException e) {
            JwtUtil.responseError(SpringContextUtils.getHttpServletResponse(),401,e.getMessage());
            e.printStackTrace();
            return null;
        }
        return new SimpleAuthenticationInfo(loginUser, token, getName());


        return null;
    }

    /**
     * 检验token有效性
     */
    private LoginUser checkUserTokenIsEffect(String token) {
        // 获取userId
        String userId = JwtUtil.getUserIdFromToken(token);
        if (userId == null) {
            throw new AuthenticationException("token无效！");
        }

        log.debug("token有效性验证...");
        LoginUser loginUser = JwtUtil.getLoginUser(userId, redisUtil);


    }
}
