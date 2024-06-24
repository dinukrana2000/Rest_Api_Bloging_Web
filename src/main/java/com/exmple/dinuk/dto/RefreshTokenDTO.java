package com.exmple.dinuk.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshTokenDTO {
    @NotEmpty(message = "{login.refreshtoken.mandatory}")
    private String expiredAccessToken;
}
