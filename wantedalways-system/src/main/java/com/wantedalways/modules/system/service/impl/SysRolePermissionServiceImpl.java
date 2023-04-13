package com.wantedalways.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantedalways.common.constant.CacheConstant;
import com.wantedalways.common.util.RedisUtil;
import com.wantedalways.modules.system.dao.SysRolePermissionDao;
import com.wantedalways.modules.system.entity.SysPermission;
import com.wantedalways.modules.system.entity.SysRole;
import com.wantedalways.modules.system.entity.SysRolePermission;
import com.wantedalways.modules.system.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色权限关系表 服务实现类
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-17
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionDao, SysRolePermission> implements SysRolePermissionService {

    @Autowired
    private SysRolePermissionDao sysRolePermissionDao;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public List<SysPermission> getUserPermissions(String username) {
        Set<String> roleSet = (Set<String>) redisUtil.get(CacheConstant.SYS_USER_CACHE_ROLES + "::" + username);
        if (CollectionUtils.isEmpty(roleSet)) {
            return new ArrayList<>();
        }
        return sysRolePermissionDao.selectUserPermissionsList(roleSet);
    }
}
