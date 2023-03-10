package com.wantedalways.config.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 获取yml配置文件中拦截排除项
 * @author Wantedalways
 */
@Setter
@Getter
public class ShiroVo {

    private String excludeUrls = "";
}
