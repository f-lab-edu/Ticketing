package com.ticketing.server.movie.setup;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@Profile(value = {"local"})
@RestController
@RequiredArgsConstructor
public class MovieSetupController {

	private final MovieSetupService movieSetupService;

	@PostConstruct
	public void setup() {
		movieSetupService.init();
	}

}
