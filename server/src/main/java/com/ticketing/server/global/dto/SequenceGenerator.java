package com.ticketing.server.global.dto;

public interface SequenceGenerator {

	Long generateId();

	long[] parse(long id);

}
