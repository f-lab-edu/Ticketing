package com.ticketing.server.user.domain;

public interface SequenceGenerator {

	Long generateId();

	long[] parse(long id);

}
