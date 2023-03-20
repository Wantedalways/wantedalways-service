package com.wantedalways.modules.api.controller;

import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.modules.system.service.impl.SysBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 对外服务接口
 * @author Wantedalways
 */
@RestController
@RequestMapping("/sys/api")
public class SysBaseApiController {

    @Autowired
    private SysBaseServiceImpl sysBaseService;

    /**
     * 根据用户账号查询用户信息
     * @param username 用户账号
     * @return 登录用户信息
     */
    @GetMapping("/getUserByUsername")
    public LoginUser getUserByUsername(@RequestParam("username") String username) {
        return sysBaseService.getUserByUsername(username);
    }


}
