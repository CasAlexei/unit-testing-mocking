package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.model.Privilege;
import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import com.endava.internship.mocking.repository.PaymentRepository;
import com.endava.internship.mocking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    ValidationService validationService;

    @InjectMocks
    PaymentService tested;

    private Payment payment;
    @BeforeEach
    void setUp() {
        payment = new Payment(1, 10.0, "user ");
    }

    @Test
    void createPayment() {
        User user = new User(1, "Alex", Status.ACTIVE, singletonList(Privilege.DELETE));

        ArgumentCaptor<Payment> captor = ArgumentCaptor.forClass(Payment.class);
        tested.createPayment(1, 10.0);

        Mockito.verify(paymentRepository).save(captor.capture());

        assertThat(captor.getValue().getAmount()).isEqualTo(10.0);
        assertThat(captor.getValue().getMessage()).isEqualTo("Payment from user Alex");
        assertThat(captor.getValue().getUserId()).isEqualTo(1);

    }

    @Test
    void editMessage() {
        UUID uuid = UUID.randomUUID();

        when(paymentRepository.editMessage(uuid, "gkasjdkgjakdsgjkdsag")).thenReturn(payment);

        Payment result = tested.editPaymentMessage(uuid, "gkasjdkgjakdsgjkdsag");

        assertThat(result).isEqualTo(payment);

        verify(paymentRepository).editMessage(uuid, "gkasjdkgjakdsgjkdsag");

        verifyNoMoreInteractions(paymentRepository);
    }


    @Test
    void getAllByAmountExceeding() {

        Payment payment1 = new Payment(1, 11.0, "user ");

        List<Payment> list = new ArrayList<>();
        list.add(payment);
        list.add(payment1);

        when(paymentRepository.findAll()).thenReturn(list);

        List<Payment> listPayment = tested.getAllByAmountExceeding(5.0);

        assertThat(listPayment).containsExactlyInAnyOrder(payment, payment1);
    }

    @Test
    void testGroupBy(){
        User user1 = new User(1, "Alex", Status.ACTIVE, asList(Privilege.UPDATE, Privilege.CREATE, Privilege.DELETE));
        User user2 = new User(2, "Tatiana", Status.INACTIVE, asList(Privilege.UPDATE, Privilege.DELETE));
        User user3 = new User(3, "Petr", Status.ACTIVE, singletonList(Privilege.DELETE));

        List<Integer> userIdsForTest = asList(1, 2, 3);

        Map<Privilege, List<User>> resultMap = new HashMap<>();
        resultMap.put(Privilege.CREATE, singletonList(user1));
        resultMap.put(Privilege.UPDATE, asList(user1, user2));
        resultMap.put(Privilege.DELETE, asList(user1, user2, user3));



        when(userRepository.findById(1)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(Optional.of(user2));
        when(userRepository.findById(3)).thenReturn(Optional.of(user3));

        Map<Privilege, List<User>> mapFromService =  tested.groupBy(userIdsForTest);

        assertThat(mapFromService).containsAllEntriesOf(resultMap);
    }
}
