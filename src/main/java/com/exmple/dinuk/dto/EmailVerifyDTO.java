package com.exmple.dinuk.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailVerifyDTO {
    @Min(value = 1, message = "OTP is mandatory")
    private int otp;

    @NotBlank(message = "Email is mandatory")
    private String email;

    private String message;
}
