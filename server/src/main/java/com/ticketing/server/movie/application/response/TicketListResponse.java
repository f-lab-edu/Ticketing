package com.ticketing.server.movie.application.response;

import com.ticketing.server.movie.service.dto.TicketDTO;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TicketListResponse {

	@ApiModelProperty(value = "티켓 목록")
	private List<TicketDTO> ticketDtos;

}
