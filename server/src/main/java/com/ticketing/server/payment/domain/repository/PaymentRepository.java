package com.ticketing.server.payment.domain.repository;

import com.ticketing.server.payment.domain.Payment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

	List<Payment> findByUserAlternateId(Long userId);

}
