package com.endava.internship.mocking.service;

import java.util.*;
import java.util.stream.Collectors;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Privilege;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

public class PaymentService {

    private UserRepository userRepository;
    private PaymentRepository paymentRepository;
    private ValidationService validationService;

    public PaymentService(UserRepository userRepository, PaymentRepository paymentRepository,
                          ValidationService validationService) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.validationService = validationService;
    }

    public Payment createPayment(Integer userId, Double amount) {
        validationService.validateUserId(userId);
        validationService.validateAmount(amount);

        final User user = userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        validationService.validateUser(user);

        final String paymentMessage = "Payment from user " + user.getName();
        final Payment payment = new Payment(user.getId(), amount, paymentMessage);
        return paymentRepository.save(payment);
    }

    public Payment editPaymentMessage(UUID paymentId, String newMessage) {
        validationService.validatePaymentId(paymentId);
        validationService.validateMessage(newMessage);

        return paymentRepository.editMessage(paymentId, newMessage);
    }

    public List<Payment> getAllByAmountExceeding(Double amount) {
        return paymentRepository.findAll()
            .stream()
            .filter(payment -> payment.getAmount() > amount)
            .collect(Collectors.toList());
    }

    public Map<Privilege, List<User>> groupBy(List<Integer> userIds) {

        List<User> users = userIds.stream().map(userRepository::findById).map(Optional::get).collect(Collectors.toList());

        return users.stream()
            .flatMap(user -> user.getPrivileges().stream().map(privilege ->
                {
                    Map<Privilege, User> map = new HashMap<>();
                    map.put(privilege, user);
                    return map.entrySet();
                }
            ))
            .flatMap(Set::stream)
            .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, Collectors.toList())));
    }
}
