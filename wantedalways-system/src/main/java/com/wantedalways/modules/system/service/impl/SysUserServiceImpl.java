package com.wantedalways.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.modules.system.dao.SysUserDao;
import com.wantedalways.modules.system.entity.SysUser;
import com.wantedalways.modules.system.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Wantedalways
 * @since 2023-03-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUser> implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public LoginUser getEncodeUserInfoByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }

        SysUser sysUser = sysUserDao.selectUserByUserId(userId);
        if (sysUser == null) {
            return null;
        }
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);

        return loginUser;
    }
}
