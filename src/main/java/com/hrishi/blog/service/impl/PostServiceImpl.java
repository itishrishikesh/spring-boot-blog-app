package com.hrishi.blog.service.impl;

import com.hrishi.blog.entity.Post;
import com.hrishi.blog.exception.ResourceNotFoundException;
import com.hrishi.blog.payload.PostDto;
import com.hrishi.blog.repository.PostRepository;
import com.hrishi.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = postRepository.save(fromPostDtoToPost(postDto));
        return fromPostToPostDto(post);
    }

    @Override
    public List<PostDto> getPosts() {
        return postRepository.findAll().stream().map(this::fromPostToPostDto).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        return fromPostToPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return fromPostToPostDto(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        postRepository.delete(post);
    }

    private Post fromPostDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    private PostDto fromPostToPostDto(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getContent());
    }
}
