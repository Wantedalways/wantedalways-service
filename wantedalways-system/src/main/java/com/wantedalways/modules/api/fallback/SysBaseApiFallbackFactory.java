package com.wantedalways.modules.api.fallback;

import com.wantedalways.modules.api.SysBaseApi;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @author Wantedalways
 */
public class SysBaseApiFallbackFactory implements FallbackFactory<SysBaseApi> {
    @Override
    public SysBaseApi create(Throwable cause) {
        SysBaseApiFallback fallback = new SysBaseApiFallback();
        fallback.setCause(cause);
        return fallback;
    }
}
