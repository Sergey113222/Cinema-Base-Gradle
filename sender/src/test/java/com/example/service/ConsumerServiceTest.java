package com.example.service;

import com.example.cinemabasegradle.dto.RabbitRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ConsumerServiceTest {

    private EmailService emailService;
    private ConsumerService consumerService;
    private RabbitRequestDto rabbitRequestDto;

    @BeforeEach
    void setUp() {
        emailService = mock(EmailService.class);
        consumerService = new ConsumerService(emailService);
        rabbitRequestDto = new RabbitRequestDto();
        rabbitRequestDto.setTitle("testFilm");
        rabbitRequestDto.setEmail("test@gmail.com");
    }

    @Test
    void listen() {
        consumerService.listen(rabbitRequestDto);
        verify(emailService, Mockito.times(1)).sendEmail(any());
    }
}