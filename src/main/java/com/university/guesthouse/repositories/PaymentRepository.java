package com.university.guesthouse.repositories;

import com.university.guesthouse.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
