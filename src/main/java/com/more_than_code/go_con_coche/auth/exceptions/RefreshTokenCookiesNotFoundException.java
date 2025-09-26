package com.more_than_code.go_con_coche.auth.exceptions;

public class RefreshTokenCookiesNotFoundException extends RuntimeException {
    public RefreshTokenCookiesNotFoundException() {
        super("Refresh token cookie missing");
    }
}