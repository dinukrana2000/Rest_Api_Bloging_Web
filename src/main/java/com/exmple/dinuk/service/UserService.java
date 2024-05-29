package com.exmple.dinuk.service;
import com.exmple.dinuk.dto.EmailVerifyDTO;
import com.exmple.dinuk.dto.LoginDTO;
import com.exmple.dinuk.entity.User;
import com.exmple.dinuk.dto.UserDTO;
import com.exmple.dinuk.exception.CustomExceptions;
import com.exmple.dinuk.repo.UserRepo;
import com.exmple.dinuk.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class UserService implements UserServiceInterface {
    @Autowired // injected the repository into service because to depenceny injection
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    public UserDTO sendEmail(String email,int otp){
        UserDTO userDTO = new UserDTO();
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setSubject("Hello User");
            message.setTo(email);
            message.setText("Please use this OTP to verify your email:"+" "+otp);
            message.setFrom("75a7e2001@smtp-brevo.com");


            javaMailSender.send(message);
            userDTO.setMessage2("Verification OTP send Successfully to" +" "+ email);

        }
        catch (Exception e){
            throw new CustomExceptions.EmailNotSentException("Email not sent");
        }
        return userDTO;
    }


    public boolean CheckUserExist(String username,String email) {
        List<User> users= userRepo.existsByUsernameOrEmail(username,email);
        return !users.isEmpty();
    }


    public UserDTO saveUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);

        if (CheckUserExist(userDTO.getUsername(),userDTO.getEmail())){
            throw new CustomExceptions.UserExistsException("Username or email already exists");
        }
        else if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new CustomExceptions.PasswordMismatchException("Password and confirm password do not match");
        }
        else {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword()); // hash the password before saving
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


    public boolean UserExist(String username){
        return userRepo.existsByUsername(username).isPresent();
    }


    public LoginDTO loginto(LoginDTO loginDTO) {
        if(UserExist(loginDTO.getUsername())){
            User user = userRepo.findByUsernameAndPassword(loginDTO.getUsername());
            if(user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
                if(!user.isVerified()){
                    throw new CustomExceptions.UserNotVerifiedException("User is not verified Please verify email first");
                }
                loginDTO.setMessage("Login successful");
                loginDTO.setPassword(null);
                Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());
                String token = jwtTokenProvider.generateToken(authentication);
                loginDTO.setToken(token);
            }
            else{
                throw new CustomExceptions.InvalidPasswordException("Invalid password");
            }
        }
        else{
            throw new CustomExceptions.UserDoesNotExistException("User does not exist");
        }
        return loginDTO;
    }

    public EmailVerifyDTO verifyEmail(EmailVerifyDTO emailVerifyDTO) {
        User user = userRepo.findByEmail(emailVerifyDTO.getEmail());
        if(user != null){
            if(user.isVerified()){
                throw new CustomExceptions.UserNotVerifiedException("Email already verified");
            }
            else if(user.getOtp()==emailVerifyDTO.getOtp()){
                userRepo.verifyEmail(emailVerifyDTO.getEmail());
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

    @Scheduled(fixedRate = 60000)
    public void deleteUnverifiedUsers() {
        userRepo.deleteUnverifiedUsersOlderThanOneMinute(LocalDateTime.now().minusMinutes(1));
    }
}
