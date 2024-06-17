package com.exmple.dinuk.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity //its like database table
@NoArgsConstructor
@AllArgsConstructor
@Data //getters and setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String address;
    private String number;
    private String password;
    boolean verified = false;
    int otp;
    private String resetPasswordToken="";
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime resetPasswordExpiry;
}
