package com.exmple.dinuk.repo;

import com.exmple.dinuk.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface PostRepo extends JpaRepository<Post,Integer> {


    List<Post> findAllBy() ;


    List<Post> findPostsByUsername(String username);

    @Modifying
    @Query(value = "UPDATE Post SET title=:title, content=:content, author=:author, date=:date WHERE id=:id AND username=:username")
    @Transactional
    void updatePost(int id, String username, String title, String content, String author, Timestamp date);


    Optional<Post> findPostById(int id);


    @Modifying
    @Transactional
    void deletePostById(int id);

}
