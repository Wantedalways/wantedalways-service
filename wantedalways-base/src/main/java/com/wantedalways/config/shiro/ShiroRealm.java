package com.wantedalways.config.shiro;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wantedalways.common.api.CommonApi;
import com.wantedalways.common.constant.CommonConstant;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.common.util.RedisUtil;
import com.wantedalways.common.util.SpringContextUtil;
import com.wantedalways.config.shiro.vo.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

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

    @Lazy
    @Resource
    private CommonApi commonApi;

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

        String username = null;
        if (principalCollection != null) {
            LoginUser sysUser = (LoginUser) principalCollection.getPrimaryPrincipal();
            username = sysUser.getUsername();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        log.info("权限认证成功！");

        // 设置用户角色集合
        Set<String> roleSet = commonApi.getUserRoles(username);
        info.setRoles(roleSet);

        // 设置用户权限集合
        Set<String> permissionSet = commonApi.getUserPermissions(username);
        info.addStringPermissions(permissionSet);
        return info;
    }

    /**
     * 身份认证，登陆时调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("认证身份...");

        String token = (String) authenticationToken.getCredentials();
        if (token == null) {
            log.error("身份认证失败，token为空！");
            throw new AuthenticationException("token为空！");
        }

        // 校验token有效性
        LoginUser loginUser;
        try {
            loginUser = this.checkUserTokenIsEffect(token);
        } catch (AuthenticationException e) {
            JwtUtil.responseError(SpringContextUtil.getHttpServletResponse(),401,e.getMessage());
            e.printStackTrace();
            return null;
        }
        return new SimpleAuthenticationInfo(loginUser, token, getName());
    }

    /**
     * 检验token有效性
     */
    private LoginUser checkUserTokenIsEffect(String token) throws AuthenticationException {
        // 获取username
        String username = JwtUtil.getUsernameFromToken(token);
        if (username == null) {
            throw new AuthenticationException("token无效！");
        }

        log.debug("token有效性验证...");
        LoginUser loginUser = JwtUtil.getLoginUser(username, redisUtil, commonApi);
        // 判断用户是否存在
        if (loginUser == null) {
            throw new AuthenticationException("当前用户不存在！");
        }
        // 判断用户状态
        if (CommonConstant.USER_STATUS_DISABLE.equals(loginUser.getStatus())) {
            throw new AuthenticationException("当前用户已禁用，请联系管理员！");
        }
        // 检验token是否失效
        if (!jwtTokenRefresh(token, username, loginUser.getPassword())) {
            throw new AuthenticationException("token失效，请重新登陆！");
        }

        return loginUser;
    }

    /**
     * 检验token是否失效，并刷新生命周期（实现用户维持在线功能）
     * 1.用户登陆成功时将token保存至redis，缓存时间为Jwt有效时间的两倍
     * 2.用户请求时判断token超过有效期，但redis中仍存在相应的缓存，则说明用户在线操作，则重新生成token并覆盖缓存
     * 3.redis中找不到相应缓存，则说明用户长时间未操作，返回token失效
     */
    private boolean jwtTokenRefresh(String token, String username, String password) {
        // 获取redis缓存token
        String cacheToken = String.valueOf(redisUtil.get(CommonConstant.PREFIX_USER_TOKEN + token));
        if (StringUtils.isNotEmpty(cacheToken)) {
            // 检验token有效性
            if (!JwtUtil.verify(cacheToken, username, password)) {
                // token失效则重新生成
                String newAuthorization = JwtUtil.sign(username, password);
                // 设置超时时间
                redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, newAuthorization);
                redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
                log.info("用户在线操作，已更新token："+ token);
            }
            // 在线操作
            return true;
        }

        return false;
    }

    /**
     * 清除当前用户的权限认证缓存
     */
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
}
