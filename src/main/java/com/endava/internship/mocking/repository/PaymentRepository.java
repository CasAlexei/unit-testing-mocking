package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Privilege;
import com.endava.internship.mocking.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Optional<Payment> findById(UUID paymentId);

    List<Payment> findAll();

    Payment save(Payment payment);

    Payment editMessage(UUID paymentId, String message);

}
