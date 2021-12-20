package com.example.service;

import com.example.cinemabasegradle.dto.RabbitRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage .class);
        verify(javaMailSender, times(1)).send(emailCaptor.capture());
    }
}