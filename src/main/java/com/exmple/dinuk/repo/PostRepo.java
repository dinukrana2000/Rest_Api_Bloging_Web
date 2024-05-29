package com.exmple.dinuk.repo;

import com.exmple.dinuk.dto.PostDTO;
import com.exmple.dinuk.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PostRepo extends JpaRepository<Post,Integer> {
    @Modifying
    @Query(value = "INSERT INTO post (id,username, date, title, content, author) VALUES (:id,:username, :date, :title, :content, :author)", nativeQuery = true)
    @Transactional
    void posted(int id, String username, Date date, String title, String content, String author );

    @Query(value = "SELECT * FROM post", nativeQuery = true)
    List<Post> getAllpost();

    @Query(value = "SELECT * FROM post WHERE username=?1", nativeQuery = true)
    List<Post> getPostByUsername(String username);

    @Modifying
    @Query(value = "UPDATE post SET title=?3,content=?4,author=?5,date=?6 WHERE id=?1 AND username=?2", nativeQuery = true)
    @Transactional
    void updatePost(int id, String username, String title, String content, String author, Date date);

    @Query(value="SELECT * FROM post WHERE id=:id" ,nativeQuery = true)
    Optional<Post> findByPostId(int id);


    @Modifying
    @Query(value = "DELETE FROM post WHERE id=:id", nativeQuery = true)
    @Transactional
    void deletePost(int id);

}
