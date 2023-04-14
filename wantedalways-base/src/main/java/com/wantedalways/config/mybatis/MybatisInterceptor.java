package com.wantedalways.config.mybatis;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.wantedalways.common.system.vo.LoginUser;
import com.wantedalways.common.util.CommonUtil;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;

/**
 * @author Wantedalways
 */
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取sql执行类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        Object parameter = invocation.getArgs()[1];
        if (parameter == null) {
            return invocation.proceed();
        }

        // 新增数据
        if (SqlCommandType.INSERT == sqlCommandType) {
            Field[] fields = CommonUtil.getAllFields(parameter);
            writeInto(parameter, "create");
        }

        // 修改数据
        if (SqlCommandType.UPDATE == sqlCommandType) {
            // 批量修改
            if (parameter instanceof MapperMethod.ParamMap) {
                MapperMethod.ParamMap<?> p = (MapperMethod.ParamMap<?>) parameter;
                String et = "et";
                if (p.containsKey(et)) {
                    parameter = p.get(et);
                } else {
                    parameter = p.get("param1");
                }
                if (parameter == null) {
                    return invocation.proceed();
                }
            }
            writeInto(parameter, "update");
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    /**
     * 获取登录用户
     */
    private LoginUser getLoginUser() {

        return (LoginUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 写入time和by
     */
    private void writeInto(Object parameter, String type) {
        LoginUser loginUser = getLoginUser();

        Field[] fields = CommonUtil.getAllFields(parameter);
        for (Field field : fields) {
            // time
            try {
                if ((type + "Time").equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(parameter, new Date());
                    field.setAccessible(false);
                }
                // by
                if ((type + "By").equals(field.getName())) {
                    field.setAccessible(true);
                    field.set(parameter, loginUser.getId());
                    field.setAccessible(false);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
