package com.exmple.dinuk.repo;

import com.exmple.dinuk.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Modifying
    @Query(value = "INSERT INTO user (id,username, email,address,number,password,verified,otp,created_date) VALUES (:id,:username, :email, :address, :number, :password,:verified,:otp,NOW())", nativeQuery = true)
    // atomicity -> do complete task or none
    @Transactional
    void Signup(int id, String username, String email, String address, String number, String password, boolean verified, int otp);


    List<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    @Query("FROM User WHERE username = :username")
    User findByUsername2(String username);

    @Modifying
    @Query("UPDATE User SET verified = true WHERE email = :email")
    @Transactional
    void verifyByEmail(String email);


    User findByEmail(String email);


    @Modifying
    @Transactional
    void deleteByCreatedDateBeforeAndVerifiedFalse(LocalDateTime oneMinuteAgo);}
