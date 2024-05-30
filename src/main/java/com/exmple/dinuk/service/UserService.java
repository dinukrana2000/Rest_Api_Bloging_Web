package com.exmple.dinuk.service;

import com.exmple.dinuk.dto.EmailVerifyDTO;
import com.exmple.dinuk.dto.UserDTO;

public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    EmailVerifyDTO verifyEmail(EmailVerifyDTO emailVerifyDTO);

}
