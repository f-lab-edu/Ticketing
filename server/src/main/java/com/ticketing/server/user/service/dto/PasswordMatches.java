package com.ticketing.server.user.service.dto;

@FunctionalInterface
public interface PasswordMatches {

	boolean passwordMatches(String password);
}
