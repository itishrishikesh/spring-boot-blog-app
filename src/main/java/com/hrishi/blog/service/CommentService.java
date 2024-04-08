package com.hrishi.blog.service;

import com.hrishi.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getComments(long postId);
    CommentDto getCommentById(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId, CommentDto commentDto);
    void deleteCommentById(long postId, long commentId);
}
