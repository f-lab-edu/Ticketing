package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.SignUpDTO;
import javax.validation.Valid;

public interface UserService {

	User register(@Valid SignUpDTO signUpDto);

	User delete(@Valid DeleteUserDTO deleteUser);

	User modifyPassword(@Valid ChangePasswordDTO changePassword);

}
