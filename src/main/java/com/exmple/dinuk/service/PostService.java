package com.exmple.dinuk.service;

import com.exmple.dinuk.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPosts();
    List<PostDTO> getPostByUsername(String username);
    PostDTO updatePost(int id, String username, PostDTO PostDTO);
    PostDTO deletePost(int id);
}
