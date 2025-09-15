package com.exmple.dinuk.service.Impl;

import com.exmple.dinuk.dto.PostDTO;
import com.exmple.dinuk.entity.Post;
import com.exmple.dinuk.exception.CustomExceptions;
import com.exmple.dinuk.repo.PostRepo;
import com.exmple.dinuk.service.PostService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.ZoneId;


@Service
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private NotificationImpl notificationService;

    public PostDTO createPost(PostDTO postDTO) {
        if (!userService.UserExist(postDTO.getUsername())) {
            log.error("User does not exist: {}", postDTO.getUsername());
            throw new CustomExceptions.UserDoesNotExistException("User does not exist");
        }
        else{
            Post post= modelMapper.map(postDTO, Post.class);
            postRepo.save(post);
            postDTO.setDate(ZonedDateTime.now(ZoneId.of("Asia/Colombo")));
            postDTO.setMessage("Post created successfully");
            notificationService.notifyNewPost("New Post Created by " + postDTO.getUsername()+" " + "Author by" +" "+
                    postDTO.getAuthor()+" "+"At"+" "+ postDTO.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

            log.info("Post created successfully: {}", postDTO.getTitle());
            return postDTO;

        }
    }

    public List<PostDTO> getAllPosts() {
        List<Post> postList = postRepo.findAllBy();
        List<PostDTO> postDTOList = new ArrayList<>();

//        if (postList.isEmpty()) {
//            log.error("No posts found");
//            throw new CustomExceptions.NoPostsFoundException("No posts found");
//        }

        for (Post post : postList) {
            try {
                post = null;
                PostDTO postDTO = modelMapper.map(post, PostDTO.class);
                ZonedDateTime zdt = post.getDate().toInstant().atZone(ZoneId.of("Asia/Colombo"));
                postDTO.setDate(zdt);
                postDTOList.add(postDTO);
            }catch (Exception e){
                log.error("execption occure",e);
                throw e;
            }
        }


        log.info("Retrieved {} posts", postDTOList.size());

        return postDTOList;
    }


    public List<PostDTO> getPostByUsername(String username) {
      List<Post> postList = postRepo.findPostsByUsername(username);
        return modelMapper.map(postList, new org.modelmapper.TypeToken<List<PostDTO>>() {

        }.getType());
    }

    public boolean PostExist(int id) {
        Optional<Post> post = postRepo.findPostById(id);
        return post.isPresent();
    }

    public PostDTO updatePost(int id, String username, PostDTO PostDTO) {
        if (!userService.UserExist(username)||!PostExist(id)) {
            log.error("User or Post does not exist: User - {}, Post ID - {}", username, id);
            throw new CustomExceptions.UserDoesNotExistException("User or Post does not exist");

        }
        else {
            Post post = modelMapper.map(PostDTO, Post.class);
            Timestamp timestamp = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Colombo")).toInstant());
            postRepo.updatePost(id, username, post.getTitle(), post.getContent(), post.getAuthor(), timestamp);
            PostDTO.setDate(ZonedDateTime.now(ZoneId.of("Asia/Colombo")));
            PostDTO.setMessage("Post updated successfully");
            return PostDTO;
        }
    }

    public PostDTO deletePost(int id) {
        if (!PostExist(id)) {
            log.error("Post does not exist: ID - {}", id);
            throw new CustomExceptions.UserDoesNotExistException("Post does not exist");
        }
        else {
            postRepo.deletePostById(id);
            PostDTO postDTO = new PostDTO();
            postDTO.setDate(ZonedDateTime.now(ZoneId.of("Asia/Colombo")));
            postDTO.setMessage("Post deleted successfully");
            return postDTO;
        }
    }

    public PostDTO getPostById(int id, String username) {
        if (!PostExist(id)) {
            log.error("Post does not exist: ID - {}", id);
            throw new CustomExceptions.UserDoesNotExistException("Post does not exist");
        }
        else {
            Post post = postRepo.findPostById1(id,username).get();
            PostDTO postDTO = modelMapper.map(post, PostDTO.class);
            ZonedDateTime zdt = post.getDate().toInstant().atZone(ZoneId.of("Asia/Colombo"));
            postDTO.setDate(zdt);
            return postDTO;
        }
    }


}
