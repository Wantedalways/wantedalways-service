package com.wantedalways.modules.api.fallback;

import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.modules.api.SysBaseApi;
import lombok.Setter;

import java.util.Set;

/**
 * @author Wantedalways
 */
public class SysBaseApiFallback implements SysBaseApi {

    @Setter
    private Throwable cause;

    @Override
    public LoginUser getUserByUsername(String username) {
        return null;
    }

    @Override
    public Set<String> getUserRoles(String username) {
        return null;
    }

    @Override
    public Set<String> getUserPermissions(Set<String> roleSet) {
        return null;
    }
}
