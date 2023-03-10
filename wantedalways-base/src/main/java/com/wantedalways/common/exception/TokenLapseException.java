package com.wantedalways.common.exception;

import org.apache.shiro.authc.AuthenticationException;

/**
 * token失效异常
 * @author Wantedalways
 */
public class TokenLapseException extends AuthenticationException {

    public TokenLapseException() {
    }

    public TokenLapseException(String message) {
        super(message);
    }
}
