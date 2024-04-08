package com.hrishi.blog.payload;

import com.hrishi.blog.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    String name;
    String email;
    Set<Role> roles;
    String password;
    String username;
}
