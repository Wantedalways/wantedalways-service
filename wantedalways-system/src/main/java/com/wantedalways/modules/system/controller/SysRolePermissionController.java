package com.wantedalways.modules.system.controller;


import com.alibaba.fastjson.JSONObject;
import com.wantedalways.common.api.vo.Result;
import com.wantedalways.modules.system.entity.SysRolePermission;
import com.wantedalways.modules.system.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色权限关系表 前端控制器
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@RestController
@RequestMapping("/sys/rolePermission")
public class SysRolePermissionController {

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * 为单个角色设置权限
     */
    @PostMapping("/setForRole")
    public Result<?> addForRole(@RequestBody JSONObject rolePermissionModel) {
        String roleId = rolePermissionModel.getString("roleId");
        List<String> newPermissionIds = JSONObject.parseArray(rolePermissionModel.getString("newPermissionIds"), String.class);
        List<String> oldPermissionIds = JSONObject.parseArray(rolePermissionModel.getString("oldPermissionIds"), String.class);

        sysRolePermissionService.setForRole(roleId, newPermissionIds, oldPermissionIds);

        return Result.success("授权成功");
    }
}
