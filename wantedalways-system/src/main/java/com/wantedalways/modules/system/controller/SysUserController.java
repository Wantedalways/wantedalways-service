package com.wantedalways.modules.system.controller;


import cn.hutool.core.util.RandomUtil;
import com.wantedalways.common.api.vo.Result;
import com.wantedalways.common.constant.CommonConstant;
import com.wantedalways.common.util.encryption.PasswordUtil;
import com.wantedalways.modules.system.entity.SysUser;
import com.wantedalways.modules.system.service.SysUserService;
import com.wantedalways.modules.system.vo.SysUserVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/add")
    public Result<?> add(@RequestBody SysUserVo sysUserVo) {
        SysUser user = sysUserVo.getSysUser();
        // 密码加盐
        String salt = RandomUtil.randomString(CommonConstant.BASE_CHECK_CODES, 8);
        String encodePassword = PasswordUtil.encrypt(user.getUsername(), user.getPassword(), salt);

        user.setPassword(encodePassword);
        user.setSalt(salt);
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        user.setCreateTime(new Date());

        sysUserService.save(user);

        return Result.success("添加成功！");
    }

    @GetMapping("/test")
    @RequiresRoles("test")
    public String test() {
        return "success";
    }

}
