package com.exmple.dinuk.service;

import com.exmple.dinuk.dto.LoginDTO;
import com.exmple.dinuk.entity.User;
import com.exmple.dinuk.exception.CustomExceptions;
import com.exmple.dinuk.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.exmple.dinuk.security.JwtTokenProvider;
import java.util.ArrayList;

@Service
@Transactional
public class LoginServiceImpl implements LoginService{
    @Autowired // injected the repository into service because to depenceny injection
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public boolean UserExist(String username){
        return userRepo.existsByUsername(username).isPresent();
    }

    public LoginDTO loginto(LoginDTO loginDTO) {
        if(UserExist(loginDTO.getUsername())){
            User user = userRepo.findByUsername(loginDTO.getUsername());
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
}
