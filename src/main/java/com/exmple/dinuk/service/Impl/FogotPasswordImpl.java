package com.exmple.dinuk.service.Impl;

import com.exmple.dinuk.dto.FogotPasswordDTO;
import com.exmple.dinuk.dto.ResetPasswordDTO;
import com.exmple.dinuk.entity.User;
import com.exmple.dinuk.exception.CustomExceptions;
import com.exmple.dinuk.repo.UserRepo;
import com.exmple.dinuk.service.FogotPasswordService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class FogotPasswordImpl implements FogotPasswordService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public FogotPasswordDTO forgotPassword(FogotPasswordDTO fogotPasswordDTO) {
        User user=userRepo.findByEmail(fogotPasswordDTO.getEmail());
        if (user==null){
            throw new CustomExceptions.UserDoesNotExistException("User does not exist");
        }
        else {
            String token = UUID.randomUUID().toString();
            user.setResetPasswordToken(token);
            user.setResetPasswordExpiry(LocalDateTime.now().plusMinutes(10)); // token will expire in 10 minutes
            userRepo.updateResetPasswordToken(token, LocalDateTime.now().plusMinutes(10), fogotPasswordDTO.getEmail());
            String url = "http://localhost:4200/resetpassword?token=" + token;
            sendMail(user.getEmail(),url);
            fogotPasswordDTO.setMessage("Password reset link has been sent to your email");
        }
        return fogotPasswordDTO;
    }
    public FogotPasswordDTO sendMail(String to, String url) {
        FogotPasswordDTO fogotPasswordDTO = new FogotPasswordDTO();
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("dinukrana@gmail.com");
            message.setTo(to);
            message.setSubject("Reset Password");
            message.setText( "Click the following link to reset your password:"+" "+ url);
            javaMailSender.send(message);

        } catch (Exception e) {
            throw new CustomExceptions.EmailNotSentException("Error sending email");

        }
        return fogotPasswordDTO;
    }
    @Override
    public ResetPasswordDTO resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userRepo.findByEmail(resetPasswordDTO.getEmail());
        if (user == null) {
            throw new CustomExceptions.UserDoesNotExistException("User does not exist");
        } else if (user.getResetPasswordToken() == null ) {
            throw new RuntimeException("Token is invalid");
        } else if (user.getResetPasswordExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token has expired");
        } else {
            if (!resetPasswordDTO.getPwd().equals(resetPasswordDTO.getConfirm_pwd())) {
                throw new CustomExceptions.PasswordMismatchException("Password and confirm password do not match");
            } else {
                String newPassword = passwordEncoder.encode(resetPasswordDTO.getPwd());
                userRepo.ResetPassword(newPassword, resetPasswordDTO.getEmail());
                System.out.println(resetPasswordDTO.getEmail() + " " + newPassword)  ;
                user.setPassword(newPassword);
                user.setPassword(newPassword);
                resetPasswordDTO.setPwd(null);
                resetPasswordDTO.setConfirm_pwd(null);
                resetPasswordDTO.setMessage("Password reset successfully");
                user.setResetPasswordToken(null);
                user.setResetPasswordExpiry(null);
            }
        }
        return resetPasswordDTO;
    }



}
