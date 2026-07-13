package com.lucaschalita.polltaker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SegurancaConfig {
	
	
	@Configuration
	public class SecurancaConfig {

	    @Bean
	    SecurityFilterChain securityFilterChain(
	            org.springframework.security.config.annotation.web.builders.HttpSecurity http)
	            throws Exception {

	        http
	            .csrf(csrf -> csrf.disable())
	            .authorizeHttpRequests(auth -> auth
	                    .anyRequest().permitAll()
	            )
	            .httpBasic(Customizer.withDefaults());

	        return http.build();
	    }
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
