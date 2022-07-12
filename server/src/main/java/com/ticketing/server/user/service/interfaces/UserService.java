package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.domain.ChangeGradeDTO;
import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.dto.ChangedPasswordUserDTO;
import com.ticketing.server.user.service.dto.DeletedUserDTO;
import com.ticketing.server.user.service.dto.ChangePasswordDTO;
import com.ticketing.server.user.service.dto.CreatedUserDTO;
import com.ticketing.server.user.service.dto.DeleteUserDTO;
import com.ticketing.server.user.service.dto.SignUpDTO;
import com.ticketing.server.user.service.dto.UserDetailDTO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

	CreatedUserDTO register(@Valid SignUpDTO signUpDto);

	DeletedUserDTO delete(@Valid DeleteUserDTO deleteUserDto);

	ChangedPasswordUserDTO changePassword(@Valid ChangePasswordDTO changePasswordDto);

	@Transactional
	ChangeGradeDTO changeGrade(@NotNull String email, @NotNull UserGrade grade);

	UserDetailDTO findDetailByEmail(@NotNull String email);

	User findNotDeletedUserByEmail(@NotNull String email);

}
