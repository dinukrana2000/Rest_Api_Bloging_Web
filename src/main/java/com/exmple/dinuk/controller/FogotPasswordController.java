package com.exmple.dinuk.controller;

import com.exmple.dinuk.dto.FogotPasswordDTO;
import com.exmple.dinuk.dto.ResetPasswordDTO;
import com.exmple.dinuk.service.FogotPasswordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/user")
@CrossOrigin
public class FogotPasswordController {
    @Autowired
    private FogotPasswordService FogotPasswordService;
    @PutMapping(value = "/forgotPassword")
    public FogotPasswordDTO forgotPassword(@Valid @RequestBody FogotPasswordDTO fogotPasswordDTO){

        return FogotPasswordService.forgotPassword(fogotPasswordDTO);

    }
    @PutMapping(value = "/resetPassword")
    public ResetPasswordDTO resetPassword(@Valid @RequestBody ResetPasswordDTO resetPasswordDTO){

        return FogotPasswordService.resetPassword(resetPasswordDTO);

    }
}
