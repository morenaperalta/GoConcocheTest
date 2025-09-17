package com.more_than_code.go_con_coche.auth.exceptions;

import com.more_than_code.go_con_coche.global.AppException;

public class RefreshTokenCookiesNotFoundException extends AppException {
    public RefreshTokenCookiesNotFoundException() {
        super("Refresh token cookie missing");
    }
}