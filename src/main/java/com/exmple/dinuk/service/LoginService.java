package com.exmple.dinuk.service;

import com.exmple.dinuk.dto.LoginDTO;
import com.exmple.dinuk.dto.RefreshTokenDTO;

public interface LoginService {
    LoginDTO loginto(LoginDTO loginDTO);
    String getEmail(String username);

    LoginDTO refreshToken(String refreshTokenHeader, RefreshTokenDTO refreshTokenDTO);

}
