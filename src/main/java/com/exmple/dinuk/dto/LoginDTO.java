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
    @NotBlank(message = "Username is empty")
    @NotEmpty(message = "Username is empty")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is empty")
    @NotEmpty(message = "Password is empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    private String message;
    private String token;

}
