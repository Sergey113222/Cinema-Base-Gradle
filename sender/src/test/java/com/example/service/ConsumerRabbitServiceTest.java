package com.example.service;

import com.example.cinemabasegradle.dto.BrokerRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ConsumerRabbitServiceTest {

    private EmailService emailService;
    private ConsumerRabbitService consumerRabbitService;
    private BrokerRequestDto brokerRequestDto;

    @BeforeEach
    void setUp() {
        emailService = mock(EmailService.class);
        consumerRabbitService = new ConsumerRabbitService(emailService);
        brokerRequestDto = new BrokerRequestDto();
        brokerRequestDto.setTitle("testFilm");
        brokerRequestDto.setEmail("test@gmail.com");
    }

    @Test
    void listen() {
        consumerRabbitService.listen(brokerRequestDto);
        verify(emailService, Mockito.times(1)).sendEmail(any());
    }
}