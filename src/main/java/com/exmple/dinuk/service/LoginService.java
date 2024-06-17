package com.exmple.dinuk.service;

import com.exmple.dinuk.dto.LoginDTO;

public interface LoginService {
    LoginDTO loginto(LoginDTO loginDTO);
    String getEmail(String username);

}
