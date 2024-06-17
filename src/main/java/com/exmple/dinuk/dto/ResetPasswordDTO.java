package com.exmple.dinuk.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordDTO {
    @NotEmpty(message = "{$email.mandatory}")
    private String email;

    @NotBlank(message = "{password.mandatory}")
    @NotEmpty(message = "{password.mandatory}")
    @Size(min = 6, message = "{password.size}")
    private String pwd;

    @NotBlank(message = "{password.mandatory}")
    @NotEmpty(message = "{password.mandatory}")
    @Size(min = 6, message = "{password.size}")
    private String confirm_pwd;

    private String message;
}
