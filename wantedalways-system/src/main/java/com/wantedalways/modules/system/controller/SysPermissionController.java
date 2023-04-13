package com.wantedalways.modules.system.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.wantedalways.common.api.vo.Result;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.modules.system.entity.SysPermission;
import com.wantedalways.modules.system.service.SysRolePermissionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@RestController
@RequestMapping("/sys/permission")
public class SysPermissionController {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 获取当前登录用户的权限列表，用于生成前端目录
     */
    @GetMapping("/getPermissionList")
    public Result<?> getPermissionList() {
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (ObjectUtils.isEmpty(loginUser)) {
            return Result.error(500, "请重新登录系统！");
        }
        List<SysPermission> permissionList = sysRolePermissionService.getUserPermissions(loginUser.getUsername());
        return Result.success(permissionList);
    }

}
