package com.exmple.dinuk.controller;

import com.exmple.dinuk.dto.EmailVerifyDTO;
import com.exmple.dinuk.dto.UserDTO;
import com.exmple.dinuk.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class SignupController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public UserDTO SignUser(@Valid @RequestBody UserDTO userDTO){
        return  userService.saveUser(userDTO);

    }
    @PutMapping("/verifyEmail")
    public EmailVerifyDTO verifyEmail(@Valid @RequestBody EmailVerifyDTO emailVerifyDTO){
        return userService.verifyEmail(emailVerifyDTO);
    }

}
