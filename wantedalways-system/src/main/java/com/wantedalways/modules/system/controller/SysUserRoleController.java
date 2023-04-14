package com.wantedalways.modules.system.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wantedalways.common.api.vo.Result;
import com.wantedalways.modules.system.entity.SysRole;
import com.wantedalways.modules.system.entity.SysUserRole;
import com.wantedalways.modules.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 前端控制器
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-13
 */
@RestController
@RequestMapping("/sys/userRole")
public class SysUserRoleController {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 查询用户角色
     */
    @GetMapping("/getRolesByUserId")
    public Result<?> getRolesByUserId(@RequestParam("userId") String userId) {
        List<SysRole> roleList = sysUserRoleService.queryRolesByUserId(userId);
        if (CollectionUtils.isEmpty(roleList)) {
            return Result.fail("未查询到该用户的相关角色信息。");
        }
        return Result.success(roleList);
    }

}
