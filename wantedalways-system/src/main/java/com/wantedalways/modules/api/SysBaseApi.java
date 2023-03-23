package com.wantedalways.modules.api;

import com.wantedalways.common.api.CommonApi;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.modules.api.fallback.SysBaseApiFallbackFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author Wantedalways
 */
@Component
@FeignClient(contextId = "sysBaseApi", value = "wantedalways-system", fallbackFactory = SysBaseApiFallbackFactory.class)
@ConditionalOnMissingClass("com.wantedalways.modules.system.service.impl.SysBaseServiceImpl")
public interface SysBaseApi extends CommonApi {

    /**
     * 根据用户账号查询用户信息
     * @param username 用户账号
     * @return 登录用户信息
     */
    @Override
    @GetMapping("/sys/api/getUserByUsername")
    LoginUser getUserByUsername(@RequestParam("username") String username);

    /**
     * 查询用户角色信息
     * @param username 用户账号
     * @return 角色集合
     */
    @Override
    @GetMapping("/sys/api/getUserRoles")
    Set<String> getUserRoles(@RequestParam("username") String username);

    /**
     * 查询用户权限信息
     * @param roleSet 角色集合
     * @return 权限集合
     */
    @Override
    Set<String> getUserPermissions(@RequestBody Set<String> roleSet);
}
