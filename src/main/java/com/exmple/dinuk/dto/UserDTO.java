package com.exmple.dinuk.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    @NotBlank(message = "Username is mandatory")
    @NotEmpty(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is mandatory")
    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Number is mandatory")
    @NotEmpty(message = "Number is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    private String number;

    @NotBlank(message = "Password is mandatory")
    @NotEmpty(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;


    @NotBlank(message = "Password is mandatory")
    @NotEmpty(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
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
