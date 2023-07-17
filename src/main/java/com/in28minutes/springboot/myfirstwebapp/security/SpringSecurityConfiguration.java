package com.in28minutes.springboot.myfirstwebapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SpringSecurityConfiguration {

	@Bean
	InMemoryUserDetailsManager createUserDetailsManager() {

		UserDetails uerDetails1 = createNewUser("we", "3750");
		UserDetails uerDetails2 = createNewUser("kim", "3750");
		UserDetails uerDetails3 = createNewUser("sam", "3750");

		return new InMemoryUserDetailsManager(uerDetails1, uerDetails2, uerDetails3);

	}
//	@Bean
//	public InMemoryUserDetailsManager createUserDetailsManager() {
//		
//		UserDetails uerDetails = User.withDefaultPasswordEncoder()
//				.username("we")
//				.password("3750")
//				.roles("USER","ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(uerDetails);
//		
//	}

	private UserDetails createNewUser(String username, String password) {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);

		UserDetails uerDetails = User.builder().passwordEncoder(passwordEncoder).username(username).password(password)
				.roles("USER", "ADMIN").build();
		return uerDetails;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated());
		http.formLogin(withDefaults());
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		return http.build();
	}
}
