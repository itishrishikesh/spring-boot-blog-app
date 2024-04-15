package com.hrishi.blog.service.impl;

import com.hrishi.blog.entity.Category;
import com.hrishi.blog.entity.Post;
import com.hrishi.blog.exception.ResourceNotFoundException;
import com.hrishi.blog.payload.PostDto;
import com.hrishi.blog.payload.PostResponse;
import com.hrishi.blog.repository.CategoryRepository;
import com.hrishi.blog.repository.PostRepository;
import com.hrishi.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("category", "id", postDto.getCategoryId().toString()));

        Post post = fromPostDtoToPost(postDto);

        post.setCategory(category);

        post = postRepository.save(post);

        return fromPostToPostDto(post);
    }

    @Override
    public PostResponse getPosts(int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<PostDto> postDtos = posts.stream().map(this::fromPostToPostDto).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPage(page);
        postResponse.setSize(size);
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        return fromPostToPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("category", "id", postDto.getCategoryId().toString()));
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        return fromPostToPostDto(postRepository.save(post));
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id.toString()));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Long id) {
        List<Post> posts = postRepository.findByCategoryId(id);
        return posts.stream().map(this::fromPostToPostDto).toList();
    }

    private Post fromPostDtoToPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }

    private PostDto fromPostToPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }
}
