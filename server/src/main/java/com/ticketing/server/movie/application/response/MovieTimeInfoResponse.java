package com.ticketing.server.movie.application.response;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieTimeInfoResponse {

	@ApiModelProperty(value = "영화 시간표 ID")
	private Long movieTimeId;

	@ApiModelProperty(value = "상영관 번호")
	private Integer theaterNumber;

	@ApiModelProperty(value = "회차")
	private Integer round;

	@ApiModelProperty(value = "시작 시간")
	private LocalDateTime startAt;

	@ApiModelProperty(value = "종료 시간")
	private LocalDateTime endAt;


}
