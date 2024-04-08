package com.hrishi.blog.service;

import com.hrishi.blog.payload.LoginDto;
import com.hrishi.blog.payload.RegisterDto;

public interface AuthService {
    public String login(LoginDto loginDto);
    public String register(RegisterDto registerDto);
}
