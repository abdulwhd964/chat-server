/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:8:45 AM
 */
package com.chatserver.config;

import com.chatserver.handler.CustomAuthenticationSuccessHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityConfig {

	static final String[] AUTH_WHITE_LIST = { "/h2-console/**", "/swagger-ui/index.html/**", "/login",
			"/v3/api-docs/**", "/swagger-ui/**", "/webjars/**", "/swagger-resources/**" };

	@Autowired
	CustomAuthenticationSuccessHandler authenticationSuccessHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(authorize) -> authorize.requestMatchers(AUTH_WHITE_LIST).permitAll().anyRequest().authenticated())
				.csrf((csrf) -> csrf.disable()).httpBasic(Customizer.withDefaults())
				.headers((headers) -> headers.frameOptions((frame) -> frame.sameOrigin())).formLogin(form -> {
					Customizer.withDefaults();
					form.successHandler(authenticationSuccessHandler);
				});
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
