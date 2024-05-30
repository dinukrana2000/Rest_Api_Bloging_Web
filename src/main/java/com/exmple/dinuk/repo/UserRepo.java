package com.exmple.dinuk.repo;

import com.exmple.dinuk.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {

    @Modifying
    @Query(value = "INSERT INTO user (id,username, email,address,number,password,verified,otp,created_date) VALUES (:id,:username, :email, :address, :number, :password,:verified,:otp,NOW())", nativeQuery = true)
    // atomicity -> do complete task or none
    @Transactional
    void Signup(int id, String username, String email, String address, String number, String password, boolean verified, int otp);

    @Query("FROM User WHERE username = :username OR email=:email")
    List<User> existsByUsernameOrEmail(String username, String email);

    @Query("FROM User WHERE username = :username")
    Optional<User> existsByUsername(String username);

    @Query("FROM User WHERE username = :username")
    User findByUsername(String username);

    @Modifying
    @Query("UPDATE User SET verified = true WHERE email = :email")
    @Transactional
    void verifyEmail(String email);

    @Query("FROM User WHERE email = :email")
    User findByEmail(String email);


    @Modifying
    @Query("DELETE FROM User WHERE createdDate < :oneMinuteAgo AND verified = false")
    @Transactional
    void deleteUnverifiedUsersOlderThanOneMinute(@Param("oneMinuteAgo") LocalDateTime oneMinuteAgo);
}
