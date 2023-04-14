package com.wantedalways.modules.system.controller;


import com.wantedalways.common.api.vo.Result;
import com.wantedalways.modules.system.entity.SysRole;
import com.wantedalways.modules.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 新增角色
     */
    @PostMapping ("/add")
    public Result<?> add(@RequestBody SysRole sysRole) {

        sysRoleService.save(sysRole);
        return Result.success("添加成功！");
    }

}
