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

    @Min(value = 1, message = "{emailverify.otp.mandatory}")
    private int otp;

    @NotBlank(message = "{emailverify.email.mandatory}")
    private String email;

    private String message;
}
