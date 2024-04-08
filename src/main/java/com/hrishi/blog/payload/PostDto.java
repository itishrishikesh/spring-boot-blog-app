package com.hrishi.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    Long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    String title;
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    String description;
    @NotEmpty
    String content;
    Set<CommentDto> commentSet;
}
