package com.hrishi.blog.service;

import com.hrishi.blog.payload.PostDto;
import com.hrishi.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getPosts(int page, int size, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePost(Long id);
    List<PostDto> getPostByCategory(Long id);
}
