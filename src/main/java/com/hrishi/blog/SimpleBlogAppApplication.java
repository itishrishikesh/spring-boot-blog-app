package com.hrishi.blog;

import com.hrishi.blog.entity.Role;
import com.hrishi.blog.entity.User;
import com.hrishi.blog.repository.RoleRepository;
import com.hrishi.blog.repository.UserRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "Spring Boot Blog App REST APIs Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Ramesh",
						email = "javaguides.net@gmail.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/itishrishikesh/spring-boot-blog-app/blob/main/LICENSE"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/itishrishikesh/spring-boot-blog-app"
		)
)
public class SimpleBlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}
}
