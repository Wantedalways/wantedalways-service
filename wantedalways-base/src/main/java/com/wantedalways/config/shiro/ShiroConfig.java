package com.wantedalways.config.shiro;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wantedalways.config.BaseConfig;
import com.wantedalways.config.shiro.filter.CustomShiroFilterFactoryBean;
import com.wantedalways.config.shiro.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import javax.annotation.Resource;
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
    @Qualifier("baseConfig")
    private BaseConfig baseConfig;

    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

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
                    filterChainDefinitionMap.put(url, "anon");
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
    public DefaultWebSecurityManager securityManager(ShiroRealm myRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm);

        // 关闭shiro自带session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        // 自定义redis缓存
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    /**
     * 自定义redis缓存
     */
    public RedisCacheManager redisCacheManager() {
        log.info("1...创建RedisCacheManager");

        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());

        //redis中针对不同用户缓存(此处的id需要对应user实体中的id字段,用于唯一标识)
        redisCacheManager.setPrincipalIdFieldName("id");
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);

        return redisCacheManager;
    }

    /**
     * redis管理器
     * @return
     */
    @Bean
    public IRedisManager redisManager() {

        log.info("2...创建RedisManager，连接Redis..");

        // redis单机支持
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(lettuceConnectionFactory.getHostName());
        redisManager.setPort(lettuceConnectionFactory.getPort());
        redisManager.setDatabase(lettuceConnectionFactory.getDatabase());
        redisManager.setTimeout(0);
        if (!StringUtils.isEmpty(lettuceConnectionFactory.getPassword())) {
            redisManager.setPassword(lettuceConnectionFactory.getPassword());
        }

        return redisManager;
    }

    /**
     * 添加注解支持
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);
        defaultAdvisorAutoProxyCreator.setAdvisorBeanNamePrefix("_no_advisor");
        return defaultAdvisorAutoProxyCreator;
    }
}
