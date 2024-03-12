package com.hrishi.blog.service;

import com.hrishi.blog.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    List<PostDto> getPosts();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);
}
