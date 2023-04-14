package com.wantedalways.common.exception;

import com.wantedalways.common.api.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Wantedalways
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public Result<?> handleAuthorizationException(AuthorizationException exception) {
        log.error(exception.getMessage(), exception);
        return Result.error(510, "暂无权限，请联系管理员！");
    }

    @ExceptionHandler({Exception.class})
    public Result<?> handleServiceException(Exception exception) {
        return Result.error(500, "操作失败！");
    }
}
