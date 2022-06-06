package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.application.response.LoginResponse;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.SignUpDTO;
import javax.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface UserService {

	LoginResponse login(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken);

	User register(@Valid SignUpDTO signUpDto);

	User delete(@Valid DeleteUserDTO deleteUserDto);

	User changePassword(@Valid ChangePasswordDTO changePasswordDto);

}
