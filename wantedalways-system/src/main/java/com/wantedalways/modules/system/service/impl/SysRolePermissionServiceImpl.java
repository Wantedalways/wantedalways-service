package com.wantedalways.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantedalways.common.constant.CacheConstant;
import com.wantedalways.common.util.CommonUtil;
import com.wantedalways.common.util.RedisUtil;
import com.wantedalways.modules.system.dao.SysRolePermissionDao;
import com.wantedalways.modules.system.entity.SysPermission;
import com.wantedalways.modules.system.entity.SysRole;
import com.wantedalways.modules.system.entity.SysRolePermission;
import com.wantedalways.modules.system.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
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
    public Set<SysPermission> getUserPermissions(String username) {
        Set<String> roleSet = (Set<String>) redisUtil.get(CacheConstant.SYS_USER_CACHE_ROLES + "::" + username);
        if (CollectionUtils.isEmpty(roleSet)) {
            return new HashSet<>();
        }
        return new HashSet<>(sysRolePermissionDao.selectUserPermissionsList(roleSet));
    }

    @Override
    public void setForRole(String roleId, List<String> newPermissionIds, List<String> oldPermissionIds) {
        // 新增权限
        List<String> addPermissionIds = CommonUtil.getDifference(newPermissionIds, oldPermissionIds);
        if (CollectionUtils.isNotEmpty(addPermissionIds)) {
            List<SysRolePermission> sysRolePermissionList = new ArrayList<>();
            for (String permissionId : addPermissionIds) {
                SysRolePermission sysRolePermission = new SysRolePermission();
                sysRolePermission.setRoleId(roleId);
                sysRolePermission.setPermissionId(permissionId);
                sysRolePermissionList.add(sysRolePermission);
            }
            saveBatch(sysRolePermissionList);
        }

        // 删除权限
        List<String> deletePermissionIds = CommonUtil.getDifference(oldPermissionIds, newPermissionIds);
        if (CollectionUtils.isNotEmpty(deletePermissionIds)) {
            for (String permissionId : deletePermissionIds) {
                LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysRolePermission::getRoleId, roleId);
                queryWrapper.eq(SysRolePermission::getPermissionId, permissionId);
                remove(queryWrapper);
            }
        }
    }
}
