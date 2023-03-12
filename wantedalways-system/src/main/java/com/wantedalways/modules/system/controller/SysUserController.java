package com.wantedalways.modules.system.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-02
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @GetMapping("/test")
    public String test() {
        return "success";
    }

}
