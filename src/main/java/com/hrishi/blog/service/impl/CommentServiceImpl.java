package com.hrishi.blog.service.impl;

import com.hrishi.blog.entity.Comment;
import com.hrishi.blog.entity.Post;
import com.hrishi.blog.exception.BlogAPIException;
import com.hrishi.blog.exception.ResourceNotFoundException;
import com.hrishi.blog.payload.CommentDto;
import com.hrishi.blog.repository.CommentRepository;
import com.hrishi.blog.repository.PostRepository;
import com.hrishi.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", Long.toString(postId)));
        comment.setPost(post);
        comment = commentRepository.save(comment);
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getComments(long postId) {
        return commentRepository.findByPostId(postId).stream().map(this::mapToDto).toList();
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", Long.toString(postId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", Long.toString(commentId)));
        if (!comment.getPost().getId().equals(postId)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment id isn't associated with post id");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", Long.toString(postId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", Long.toString(commentId)));
        if (!comment.getPost().getId().equals(postId)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment id isn't associated with post id");
        }
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", Long.toString(postId)));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "id", Long.toString(commentId)));
        if (!comment.getPost().getId().equals(postId)) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment id isn't associated with post id");
        }
        commentRepository.deleteById(commentId);
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToComment(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
        return comment;
    }
}
