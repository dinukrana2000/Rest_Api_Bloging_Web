package com.exmple.dinuk.controller;

import com.exmple.dinuk.dto.LoginDTO;
import com.exmple.dinuk.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/user")
@CrossOrigin
public class LoginController {
    @Autowired
    private LoginService loginService;
    @PostMapping(value = "/login")
    public LoginDTO loginUser(@Valid @RequestBody LoginDTO loginDTO){

        return loginService.loginto(loginDTO);
    }
}
