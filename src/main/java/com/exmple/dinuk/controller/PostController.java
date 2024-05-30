package com.exmple.dinuk.controller;

import com.exmple.dinuk.dto.PostDTO;
import com.exmple.dinuk.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/post")
@CrossOrigin
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping("/create")
    public PostDTO createPost(@Valid @RequestBody PostDTO postDTO){

        return postService.createPost(postDTO);
    }

    @GetMapping(value = "/getAll")
    public List<PostDTO> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping(value = "/getByUsername/{username}")
    public List<PostDTO> getPostByUsername(@PathVariable String username){
        return postService.getPostByUsername(username);
    }

    @PutMapping(value = "/updateByidAndusername/{id}/{username}")
    public PostDTO updatePost(@PathVariable int id,@PathVariable String username,@RequestBody PostDTO postDTO){
        return postService.updatePost(id,username,postDTO);
    }

    @DeleteMapping(value = "/deletePostByid/{id}")
    public PostDTO deletePost(@PathVariable int id){
        return postService.deletePost(id);
    }
}
