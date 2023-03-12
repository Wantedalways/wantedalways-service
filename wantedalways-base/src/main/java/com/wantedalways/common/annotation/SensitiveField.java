package com.wantedalways.common.annotation;

import com.wantedalways.common.enums.SensitiveEnum;

import java.lang.annotation.*;

/**
 * 敏感数据注解
 * @author Wantedalways
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SensitiveField {

    /**
     * 不同类型处理方式处理不同
     */
    SensitiveEnum type() default SensitiveEnum.ENCODE;
}
