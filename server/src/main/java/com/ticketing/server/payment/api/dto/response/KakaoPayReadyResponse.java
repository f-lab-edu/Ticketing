package com.ticketing.server.payment.api.dto.response;

import com.ticketing.server.payment.api.dto.SnakeCaseStrategy;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KakaoPayReadyResponse extends SnakeCaseStrategy {

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

	// 결제 준비 요청 시간
	private LocalDateTime createAt;

}
