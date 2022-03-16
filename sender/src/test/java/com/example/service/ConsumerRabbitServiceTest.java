package com.example.service;

import com.example.cinemabasegradle.dto.RabbitRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ConsumerRabbitServiceTest {

    private EmailService emailService;
    private ConsumerRabbitService consumerRabbitService;
    private RabbitRequestDto rabbitRequestDto;

    @BeforeEach
    void setUp() {
        emailService = mock(EmailService.class);
        consumerRabbitService = new ConsumerRabbitService(emailService);
        rabbitRequestDto = new RabbitRequestDto();
        rabbitRequestDto.setTitle("testFilm");
        rabbitRequestDto.setEmail("test@gmail.com");
    }

    @Test
    void listen() {
        consumerRabbitService.listen(rabbitRequestDto);
        verify(emailService, Mockito.times(1)).sendEmail(any());
    }
}