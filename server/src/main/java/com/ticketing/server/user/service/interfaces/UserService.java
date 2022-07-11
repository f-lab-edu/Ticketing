package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.SignUpDTO;
import com.ticketing.server.user.service.dto.UserDetailDTO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UserService {

	User register(@Valid SignUpDTO signUpDto);

	User delete(@Valid DeleteUserDTO deleteUserDto);

	User changePassword(@Valid ChangePasswordDTO changePasswordDto);

	UserDetailDTO findDetailByEmail(@NotNull String email);

	User findNotDeletedUserByEmail(@NotNull String email);

}
