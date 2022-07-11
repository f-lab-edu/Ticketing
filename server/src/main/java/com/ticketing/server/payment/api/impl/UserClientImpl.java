package com.ticketing.server.payment.api.impl;

import com.ticketing.server.payment.api.UserClient;
import com.ticketing.server.payment.api.dto.response.UserDetailResponse;
import com.ticketing.server.user.service.dto.UserDetailDTO;
import com.ticketing.server.user.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

	private final UserService userService;

	@Override
	public UserDetailResponse detail() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails details = (UserDetails) authentication.getPrincipal();

		UserDetailDTO userDetail = userService.findDetailByEmail(details.getUsername());
		return new UserDetailResponse(userDetail);
	}

}
