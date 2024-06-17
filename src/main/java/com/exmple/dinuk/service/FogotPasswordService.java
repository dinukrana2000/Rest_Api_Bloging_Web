package com.exmple.dinuk.service;

import com.exmple.dinuk.dto.FogotPasswordDTO;
import com.exmple.dinuk.dto.ResetPasswordDTO;

public interface FogotPasswordService {
FogotPasswordDTO forgotPassword(FogotPasswordDTO fogotPasswordDTO);
ResetPasswordDTO resetPassword(ResetPasswordDTO resetPasswordDTO);

}
