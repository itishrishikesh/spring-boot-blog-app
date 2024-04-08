package com.hrishi.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    @NotEmpty
    @Size(min = 1)
    private String name;
    @Email
    private String email;
    @NotEmpty
    private String body;
}
