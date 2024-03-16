package com.hrishi.blog.service;

import com.hrishi.blog.payload.CommentDto;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
}
