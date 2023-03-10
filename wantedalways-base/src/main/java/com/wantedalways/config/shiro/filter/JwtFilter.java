package com.wantedalways.config.shiro.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wantedalways.common.constant.CommonConstant;
import com.wantedalways.common.exception.TokenLapseException;
import com.wantedalways.config.vo.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT过滤器
 *
 * @author Wantedalways
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * JWT登录认证，判断请求是否携带token并转交Realm处理
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws TokenLapseException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        // 获取token
        String token = servletRequest.getHeader(CommonConstant.ACCESS_TOKEN);
        JwtToken jwtToken = new JwtToken(token);

        // 转交Realm完成认证，认证错误则抛出异常
        getSubject(request, response).login(jwtToken);

        return true;
    }

    /**
     * 跨域支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, httpServletRequest.getHeader(HttpHeaders.ORIGIN));
        // 允许客户端请求方法
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,OPTIONS,PUT,DELETE");
        String requestHeaders = httpServletRequest.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
        // 允许客户端提交的Header
        if (StringUtils.isNotEmpty(requestHeaders)) {
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders);
        }
        // 允许客户端携带凭证信息(是否允许发送Cookie)
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");

        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (RequestMethod.OPTIONS.name().equalsIgnoreCase(httpServletRequest.getMethod())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
