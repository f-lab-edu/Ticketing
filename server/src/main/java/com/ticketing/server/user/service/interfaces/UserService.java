package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.dto.DeleteUser;
import com.ticketing.server.user.service.dto.SignUp;
import java.util.Optional;
import javax.validation.Valid;

public interface UserService {

	Optional<User> register(@Valid SignUp signUpDto);

	Optional<User> delete(@Valid DeleteUser deleteUser);

}
