package com.ticketing.server.user.service;

import static com.ticketing.server.global.exception.ErrorCode.EMAIL_NOT_FOUND;

import com.ticketing.server.global.exception.TicketingException;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.domain.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmailAndIsDeletedFalse(email)
			.map(this::createUserDetails)
			.orElseThrow(() -> new TicketingException(EMAIL_NOT_FOUND));
	}

	private UserDetails createUserDetails(User user) {
		SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getGrade().name());

		return new org.springframework.security.core.userdetails.User(
			user.getEmail()
			, user.getPassword()
			, Collections.singleton(grantedAuthority)
		);
	}
}
