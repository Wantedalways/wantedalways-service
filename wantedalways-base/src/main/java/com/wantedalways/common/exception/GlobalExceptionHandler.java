package com.wantedalways.common.exception;

import com.wantedalways.common.api.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Wantedalways
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TokenLapseException.class)
    public Result<?> handleTokenLapseException(TokenLapseException e) {
        log.error(e.getMessage(), e);
        return Result.error(401, "token失效，请重新登录！", e);
    }
}
