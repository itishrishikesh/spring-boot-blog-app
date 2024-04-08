package com.hrishi.blog;

import com.hrishi.blog.entity.Role;
import com.hrishi.blog.entity.User;
import com.hrishi.blog.repository.RoleRepository;
import com.hrishi.blog.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SimpleBlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}
}
