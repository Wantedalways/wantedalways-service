package com.wantedalways.config.shiro;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wantedalways.config.BaseConfig;
import com.wantedalways.config.shiro.filter.CustomShiroFilterFactoryBean;
import com.wantedalways.config.shiro.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 * @author Wantedalways
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Autowired
    private BaseConfig baseConfig;

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        CustomShiroFilterFactoryBean shiroFilterFactoryBean = new CustomShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 拦截器排除项
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 读取yml配置文件中的拦截排除配置
        if (baseConfig.getShiro() != null) {
            String excludeUrls = baseConfig.getShiro().getExcludeUrls();
            if (StringUtils.isNotEmpty(excludeUrls)) {
                String[] urls = excludeUrls.split(",");
                for (String url : urls) {
                    filterChainDefinitionMap.put(url, "anno");
                }
            }
        }

        // 添加jwt过滤器
        Map<String, Filter> jwtFilterMap = new HashMap<>(1);
        jwtFilterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(jwtFilterMap);
        filterChainDefinitionMap.put("/**", "jwt");

        // 未授权返回403
        shiroFilterFactoryBean.setLoginUrl("/sys/common/403");
        shiroFilterFactoryBean.setUnauthorizedUrl("/sys/common/403");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean("securityManager")
    public DefaultSecurityManager securityManager() {
        return null;
    }
}
