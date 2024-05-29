package com.exmple.dinuk.service;

import com.exmple.dinuk.dto.PostDTO;
import com.exmple.dinuk.entity.Post;
import com.exmple.dinuk.exception.CustomExceptions;
import com.exmple.dinuk.repo.PostRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService implements PostServiceInterface{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserService userService;
    public PostDTO createPost(PostDTO postDTO) {
        if (!userService.UserExist(postDTO.getUsername())) {
            throw new CustomExceptions.UserDoesNotExistException("User does not exist");
        }
        else{
            Post post= modelMapper.map(postDTO, Post.class);
            postRepo.posted(post.getId(),post.getUsername(),post.getDate(),post.getTitle(),post.getContent(),post.getAuthor());
            postDTO.setMessage("Post created successfully");
            return postDTO;
        }
    }

    public List<PostDTO> getAllPosts() {
        List<Post> postList = postRepo.getAllpost();
        return modelMapper.map(postList, new org.modelmapper.TypeToken<List<PostDTO>>() {
        }.getType());
    }


    public List<PostDTO> getPostByUsername(String username) {
      List<Post> postList = postRepo.getPostByUsername(username);
        return modelMapper.map(postList, new org.modelmapper.TypeToken<List<PostDTO>>() {

        }.getType());
    }

    public boolean PostExist(int id) {
        Optional<Post> post = postRepo.findByPostId(id);
        return post.isPresent();
    }

    public PostDTO updatePost(int id, String username, PostDTO PostDTO) {
        if (!userService.UserExist(username)||!PostExist(id)) {
            throw new CustomExceptions.UserDoesNotExistException("User or Post does not exist");

        }
        else {
            Post post = modelMapper.map(PostDTO, Post.class);
            postRepo.updatePost(id, username, post.getTitle(), post.getContent(), post.getAuthor(), post.getDate());
            PostDTO.setMessage("Post updated successfully");
            return PostDTO;
        }
    }

    public PostDTO deletePost(int id) {
        if (!PostExist(id)) {
            throw new CustomExceptions.UserDoesNotExistException("Post does not exist");
        }
        else {
            postRepo.deletePost(id);
            PostDTO postDTO = new PostDTO();
            postDTO.setMessage("Post deleted successfully");
            return postDTO;
        }
    }
}
