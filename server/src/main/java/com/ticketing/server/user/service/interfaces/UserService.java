package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.dto.SignUp;
import javax.validation.Valid;

public interface UserService {

	User register(@Valid SignUp signUpDto);

}
