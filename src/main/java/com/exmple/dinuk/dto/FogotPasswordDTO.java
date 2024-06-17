package com.exmple.dinuk.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FogotPasswordDTO {
    @NotEmpty(message = "{$email.mandatory}")
    @NotBlank
    private String email;

    private String message;


}
