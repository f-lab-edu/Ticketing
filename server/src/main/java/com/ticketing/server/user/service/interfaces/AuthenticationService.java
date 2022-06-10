package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.application.response.TokenDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface AuthenticationService {

	TokenDto login(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);

	TokenDto reissueAccessToken(String bearerRefreshToken);

}
