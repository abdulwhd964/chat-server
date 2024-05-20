/**
 * Author: Abdul Wahid
 * Date:18/05/2024
 * Time:10:41 AM
 */
package com.chatserver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.chatserver.entity.User;
import com.chatserver.exception.UserNotFoundException;
import com.chatserver.repository.UserRepository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements UserDetailsService {
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		log.info("Load User By UserName invoked");
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException(String.format("Username: %s not found", username)));

		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword()).build();
	}

}
