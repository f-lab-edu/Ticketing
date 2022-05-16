package com.ticketing.server.user.service.interfaces;

import com.ticketing.server.user.domain.User;
import com.ticketing.server.user.service.dto.SignUp;

public interface UserService {

	User register(SignUp signUpDto);

}
