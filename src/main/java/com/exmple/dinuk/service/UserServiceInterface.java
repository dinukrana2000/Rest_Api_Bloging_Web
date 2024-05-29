package com.exmple.dinuk.service;

import com.exmple.dinuk.dto.EmailVerifyDTO;
import com.exmple.dinuk.dto.LoginDTO;
import com.exmple.dinuk.dto.UserDTO;

public interface UserServiceInterface {
    UserDTO saveUser(UserDTO userDTO);
    EmailVerifyDTO verifyEmail(EmailVerifyDTO emailVerifyDTO);
    LoginDTO loginto(LoginDTO loginDTO);
}
