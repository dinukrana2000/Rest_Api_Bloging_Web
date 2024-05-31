package com.exmple.dinuk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginDTO {

    @NotBlank(message = "{login.username.mandatory}")
    @NotEmpty(message = "{login.username.mandatory}")
    @Size(min = 3, max = 50, message = "{login.username.size}")
    private String username;

    @NotBlank(message = "{login.password.mandatory}")
    @NotEmpty(message = "{login.password.mandatory}")
    @Size(min = 6, message = "{login.password.size}")
    private String password;

    private String message;
    private String token;
}
