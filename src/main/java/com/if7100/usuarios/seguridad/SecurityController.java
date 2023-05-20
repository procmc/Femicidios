package com.if7100.usuarios.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityController {
	
/*
	@Bean 
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/login").permitAll();
					auth.anyRequest().authenticated();
				})
				.formLogin()
				   .loginPage("/login")
				   .permitAll()
			    .and()
			     .sessionManagement()
			        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
			        .invalidSessionUrl("/login")
			        .maximumSessions(1)
			        .expiredUrl("/login")
			        .sessionRegistry(sessionRegistry())
			     .and()
			        .sessionFixation()
			        .migrateSession()
			     .and()
			    .build();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}*/
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		//return NoOpPasswordEncoder.getInstance();
	}
}
