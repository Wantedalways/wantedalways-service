package com.wantedalways.config.shiro.filter;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.InvalidRequestFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.filter.mgt.FilterChainManager;

import javax.servlet.Filter;
import java.util.Map;

/**
 * 解决shiro高版本对中文路径拦截的问题
 * @author Wantedalways
 */
public class CustomShiroFilterFactoryBean extends ShiroFilterFactoryBean {

    @Override
    protected FilterChainManager createFilterChainManager() {
        FilterChainManager manager = super.createFilterChainManager();

        Map<String, Filter> filterMap = manager.getFilters();
        Filter invalidRequestFilter = filterMap.get(DefaultFilter.invalidRequest.name());
        if (invalidRequestFilter instanceof InvalidRequestFilter) {
            //此处是关键，设置false跳过URL携带中文400，servletPath中文校验
            ((InvalidRequestFilter) invalidRequestFilter).setBlockNonAscii(false);
        }
        return manager;
    }

}
