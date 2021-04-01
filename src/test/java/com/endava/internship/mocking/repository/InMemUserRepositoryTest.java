package com.endava.internship.mocking.repository;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InMemUserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        this.userRepository = new InMemUserRepository();
    }

    @Test
    @DisplayName("check if user Petr present:")
    void findById_ShouldReturnTrueIfPresent() {
        Optional<User> petr = userRepository.findById(3);

        assertTrue(petr.isPresent());
    }

    @Test
    @DisplayName("check if user Petr not NULL:")
    void findById_ShouldReturn_NotNull() {
        Optional<User> petr = userRepository.findById(3);

        assertEquals("Peter", petr.get().getName());
    }

}