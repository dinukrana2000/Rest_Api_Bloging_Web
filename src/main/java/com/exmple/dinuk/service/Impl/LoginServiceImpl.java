package com.exmple.dinuk.service.Impl;

import com.exmple.dinuk.dto.LoginDTO;
import com.exmple.dinuk.dto.RefreshTokenDTO;
import com.exmple.dinuk.entity.User;
import com.exmple.dinuk.exception.CustomExceptions;
import com.exmple.dinuk.repo.UserRepo;
import com.exmple.dinuk.service.LoginService;
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
public class LoginServiceImpl implements LoginService {
    @Autowired // injected the repository into service because to depenceny injection
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public boolean UserExist(String username){
        return userRepo.findByUsername(username).isPresent();
    }

    public LoginDTO loginto(LoginDTO loginDTO) {
        if(UserExist(loginDTO.getUsername())){
            User user = userRepo.findByUsername2(loginDTO.getUsername());
            if(user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())){
                if(!user.isVerified()){
                    throw new CustomExceptions.UserNotVerifiedException("User is not verified Please verify email first");
                }
                loginDTO.setMessage("Login successful");
                loginDTO.setPassword(null);
                Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());
                String token = jwtTokenProvider.generateToken(authentication);
                String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());
                loginDTO.setRefreshToken(refreshToken);
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

    public String getEmail(String username){
           User user = userRepo.findByUsername2(username);
            if(user != null){
                return user.getEmail();
            }
            else{
                throw new CustomExceptions.UserDoesNotExistException("User does not exist");
            }

    }


    public LoginDTO refreshToken(String refreshTokenHeader, RefreshTokenDTO refreshTokenDTO) {
        String refreshToken = refreshTokenHeader.replace("Bearer ", "");

        if (jwtTokenProvider.validateToken(refreshToken)) {
            String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
            String newAccessToken = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
            //new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()) is used to create a new authentication object using username ,password and array list in here only need username for generate access token
            String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);
            return new LoginDTO(username, null, "Token refreshed successfully", newAccessToken, newRefreshToken);
        } else {
            throw new CustomExceptions.InvalidJwtTokenException("Invalid refresh token", null);
        }
    }
}
