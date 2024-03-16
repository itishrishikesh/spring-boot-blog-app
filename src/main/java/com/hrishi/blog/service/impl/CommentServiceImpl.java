package com.hrishi.blog.service.impl;

import com.hrishi.blog.entity.Comment;
import com.hrishi.blog.entity.Post;
import com.hrishi.blog.exception.ResourceNotFoundException;
import com.hrishi.blog.payload.CommentDto;
import com.hrishi.blog.repository.CommentRepository;
import com.hrishi.blog.repository.PostRepository;
import com.hrishi.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToComment(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "id", Long.toString(postId)));
        comment.setPost(post);
        commentRepository.save(comment);
        return mapToDto(comment);
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return commentDto;
    }

    private Comment mapToComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        return comment;
    }
}
