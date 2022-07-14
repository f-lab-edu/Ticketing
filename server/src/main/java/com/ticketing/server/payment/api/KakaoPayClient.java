package com.ticketing.server.payment.api;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

import com.ticketing.server.payment.api.dto.requset.KakaoPayApproveRequest;
import com.ticketing.server.payment.api.dto.requset.KakaoPayReadyRequest;
import com.ticketing.server.payment.api.dto.response.KakaoPayApproveResponse;
import com.ticketing.server.payment.api.dto.response.KakaoPayReadyResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoPay", url = "https://kapi.kakao.com/v1", path = "/payment",
	configuration = CoreFeignConfiguration.class)
public interface KakaoPayClient {

	@PostMapping(value = "/ready", consumes = APPLICATION_FORM_URLENCODED_VALUE)
	@Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
	KakaoPayReadyResponse ready(@RequestHeader(value = "Authorization") String authorization, KakaoPayReadyRequest request);

	@PostMapping(value = "/approve", consumes = APPLICATION_FORM_URLENCODED_VALUE)
	@Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
	KakaoPayApproveResponse approve(@RequestHeader(value = "Authorization") String authorization, KakaoPayApproveRequest request);

}
