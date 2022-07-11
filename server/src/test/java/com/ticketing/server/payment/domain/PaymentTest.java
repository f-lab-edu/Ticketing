package com.ticketing.server.payment.domain;

public class PaymentTest {

	public static final Payment PAYMENT_USER_1
		= new Payment(1L, 111L, "탑건: 매버릭", PaymentType.KAKAO_PAY, PaymentStatus.COMPLETED, "2022-0710-4142", 30_000);

}
