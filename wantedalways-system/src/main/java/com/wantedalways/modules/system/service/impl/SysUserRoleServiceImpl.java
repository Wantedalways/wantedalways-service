package com.wantedalways.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantedalways.modules.system.dao.SysUserRoleDao;
import com.wantedalways.modules.system.entity.SysRole;
import com.wantedalways.modules.system.entity.SysUserRole;
import com.wantedalways.modules.system.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色关系表 服务实现类
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-13
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements SysUserRoleService {

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public List<SysRole> queryRolesByUserId(String userId) {

        return sysUserRoleDao.selectRolesByUserId(userId);
    }
}
