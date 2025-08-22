package com.exmple.dinuk.service.Impl;

import com.exmple.dinuk.dto.FogotPasswordDTO;
import com.exmple.dinuk.dto.ResetPasswordDTO;
import com.exmple.dinuk.entity.User;
import com.exmple.dinuk.exception.CustomExceptions;
import com.exmple.dinuk.repo.UserRepo;
import com.exmple.dinuk.service.FogotPasswordService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            log.error("User with email {} does not exist", fogotPasswordDTO.getEmail());
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
        log.info("Password reset link sent to email: {}", fogotPasswordDTO.getEmail());
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
            log.error("Error sending email to {}: {}", to, e.getMessage());
            throw new CustomExceptions.EmailNotSentException("Error sending email");

        }
        return fogotPasswordDTO;
    }
    @Override
    public ResetPasswordDTO resetPassword(ResetPasswordDTO resetPasswordDTO) {
        User user = userRepo.findByEmail(resetPasswordDTO.getEmail());
        if (user == null) {
            log.error("User with email {} does not exist", resetPasswordDTO.getEmail());
            throw new CustomExceptions.UserDoesNotExistException("User does not exist");
        } else if (user.getResetPasswordToken() == null ) {
            log.error("Reset password token is invalid for user with email {}", resetPasswordDTO.getEmail());
            throw new RuntimeException("Token is invalid");
        } else if (user.getResetPasswordExpiry().isBefore(LocalDateTime.now())) {
            log.error("Reset password token has expired for user with email {}", resetPasswordDTO.getEmail());
            throw new RuntimeException("Token has expired");
        } else {
            if (!resetPasswordDTO.getPwd().equals(resetPasswordDTO.getConfirm_pwd())) {
                log.error("Password and confirm password do not match for user with email {}", resetPasswordDTO.getEmail());
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
        log.info("Password reset successfully for user with email: {}", resetPasswordDTO.getEmail());
        return resetPasswordDTO;
    }



}
