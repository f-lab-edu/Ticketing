package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.service.dto.TokenDTO;
import com.ticketing.server.user.service.dto.DeleteRefreshTokenDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthenticationService {

	TokenDTO generateTokenDto(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);

	TokenDTO reissueTokenDto(String bearerRefreshToken);

	DeleteRefreshTokenDTO deleteRefreshToken(String email);

}
