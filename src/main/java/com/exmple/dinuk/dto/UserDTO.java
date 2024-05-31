package com.exmple.dinuk.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    @NotBlank(message = "{username.mandatory}")
    @NotEmpty(message = "{username.mandatory}")
    @Size(min = 3, max = 50, message = "{username.size}")
    private String username;

    @NotBlank(message = "{email.mandatory}")
    @NotEmpty(message = "{email.mandatory}")
    @Email(message = "{email.valid}")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "{email.valid}")
    private String email;

    @NotEmpty(message = "{address.mandatory}")
    private String address;

    @NotBlank(message = "{number.mandatory}")
    @NotEmpty(message = "{number.mandatory}")
    @Pattern(regexp = "^\\d{10}$", message = "{number.size}")
    private String number;

    @NotBlank(message = "{password.mandatory}")
    @NotEmpty(message = "{password.mandatory}")
    @Size(min = 6, message = "{password.size}")
    private String password;


    @NotBlank(message = "{password.mandatory}")
    @NotEmpty(message = "{password.mandatory}")
    @Size(min = 6, message = "{password.size}")
    private String confirmPassword;

    private String message;

    private String message2;


    public void setUsername(String username) {
        this.username = username.replaceAll("\\s", "");
    }

    public void setEmail(String email) {

        this.email = email.replaceAll("\\s", "");
    }







}
