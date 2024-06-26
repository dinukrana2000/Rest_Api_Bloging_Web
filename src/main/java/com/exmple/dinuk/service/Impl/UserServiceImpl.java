package com.exmple.dinuk.service.Impl;
import com.exmple.dinuk.dto.EmailVerifyDTO;
import com.exmple.dinuk.entity.User;
import com.exmple.dinuk.dto.UserDTO;
import com.exmple.dinuk.exception.CustomExceptions;
import com.exmple.dinuk.repo.UserRepo;
import com.exmple.dinuk.service.UserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired // injected the repository into service because to depenceny injection
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSender javaMailSender;


    public UserDTO saveUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        if (CheckUserExist(userDTO.getUsername(),userDTO.getEmail())){
            throw new CustomExceptions.UserExistsException("Username or email already exists");
        }
        else if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new CustomExceptions.PasswordMismatchException("Password and confirm password do not match");
        }
        else {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            Random random = new Random();
            int otp=100000+ random.nextInt(900000);
            userRepo.Signup(user.getId(), userDTO.getUsername(), userDTO.getEmail(), userDTO.getAddress(), userDTO.getNumber(), encodedPassword, user.isVerified(),otp);
            userDTO.setPassword(encodedPassword);
            userDTO.setPassword(null);//null the password before sending to client
            userDTO.setConfirmPassword(null);
            userDTO.setMessage("User created successfully");
            UserDTO emailResponse=sendEmail(userDTO.getEmail(),otp);
            userDTO.setMessage2(emailResponse.getMessage2());
        }

        return userDTO;
    }

    public UserDTO sendEmail(String email,int otp){
        UserDTO userDTO = new UserDTO();
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setSubject("Hello User");
            message.setTo(email);
            message.setText("Please use this OTP to verify your email:"+" "+otp);
            message.setFrom("dinukrana@gmail.com");


            javaMailSender.send(message);
            userDTO.setMessage2("Verification OTP send Successfully to" +" "+ email);

        }
        catch (Exception e){
            throw new CustomExceptions.EmailNotSentException("Email not sent");
        }
        return userDTO;
    }


    public EmailVerifyDTO verifyEmail(EmailVerifyDTO emailVerifyDTO) {
        User user = userRepo.findByEmail(emailVerifyDTO.getEmail());
        if(user != null){
            if(user.isVerified()){
                throw new CustomExceptions.UserNotVerifiedException("Email already verified");
            }
            else if(user.getOtp()==emailVerifyDTO.getOtp()){
                userRepo.verifyByEmail(emailVerifyDTO.getEmail());
                emailVerifyDTO.setMessage("Email verified successfully");
                emailVerifyDTO.setOtp(0);
            }
            else{
                throw new CustomExceptions.InvalidOTPException("Invalid OTP");
            }
        }
        else{
            throw new CustomExceptions.UserDoesNotExistException("Email does not exist");
        }
        return emailVerifyDTO;
    }


    public boolean UserExist(String username){
        return userRepo.findByUsername(username).isPresent();
    }

    public boolean CheckUserExist(String username,String email) {
        List<User> users= userRepo.findByUsernameOrEmail(username,email);
        return !users.isEmpty();
    }


    @Scheduled(fixedRate = 60000)
    public void deleteUnverifiedUsers() {
        userRepo.deleteByCreatedDateBeforeAndVerifiedFalse(LocalDateTime.now().minusMinutes(1));
    }
}
