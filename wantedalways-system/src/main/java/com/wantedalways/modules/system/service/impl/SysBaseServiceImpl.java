package com.wantedalways.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wantedalways.common.api.CommonApi;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.modules.system.dao.SysRolePermissionDao;
import com.wantedalways.modules.system.dao.SysUserRoleDao;
import com.wantedalways.modules.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 通用api接口实现类
 * @author Wantedalways
 */
@Slf4j
@Service
public class SysBaseServiceImpl implements CommonApi {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysRolePermissionDao sysRolePermissionDao;

    @Override
    public LoginUser getUserByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        return sysUserService.getUserByUsername(username);
    }

    @Override
    public Set<String> getUserRoles(String username) {
        List<String> roles = sysUserRoleDao.selectRoleByUsername(username);
        return new HashSet<>(roles);
    }

    @Override
    public Set<String> getUserPermissions(Set<String> roleSet) {
        if (CollectionUtils.isEmpty(roleSet)) {
            return new HashSet<>();
        }
        List<String> permissions = sysRolePermissionDao.selectUserPermissions(roleSet);
        return new HashSet<>(permissions);
    }
}
