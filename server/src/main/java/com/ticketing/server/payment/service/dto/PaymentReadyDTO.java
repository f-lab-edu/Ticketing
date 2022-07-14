package com.ticketing.server.payment.service.dto;

import com.ticketing.server.payment.api.dto.response.KakaoPayReadyResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentReadyDTO {

	// 결제 고유 번호
	private String tid;

	// 요청 클라이언트가 모바일 앱일 경우
	private String nextRedirectAppUrl;

	// 요청 클라이언트가 모바일 웹일 경우
	private String nextRedirectMobileUrl;

	// 요청 클라이언트가 PC 웹일 경우
	private String nextRedirectPcUrl;

	// 카카오페이 결제 화면으로 이동하는 Android 앱 스킴
	private String androidAppScheme;

	// 카카오페이 결제 화면으로 이동하는 IOS 앱 스킴
	private String iosAppScheme;

	public PaymentReadyDTO(KakaoPayReadyResponse response) {
		this(
			response.getTid(),
			response.getNextRedirectAppUrl(),
			response.getNextRedirectMobileUrl(),
			response.getNextRedirectPcUrl(),
			response.getAndroidAppScheme(),
			response.getIosAppScheme()
		);
	}
}
