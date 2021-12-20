package com.example.service;

import com.example.cinemabasegradle.dto.RabbitRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.mock;

class EmailServiceTest {
    private RabbitRequestDto rabbitRequestDto;
    private JavaMailSender javaMailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        emailService = new EmailService(javaMailSender);

        rabbitRequestDto = new RabbitRequestDto();
        rabbitRequestDto.setTitle("testFilm");
        rabbitRequestDto.setEmail("test@gmail.com");
    }

    @Test
    void sendEmail() {
        emailService.sendEmail(rabbitRequestDto);
    }
}