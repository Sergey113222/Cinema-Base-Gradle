package com.example.service;

import com.example.cinemabasegradle.dto.BrokerRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class EmailServiceTest {
    private BrokerRequestDto brokerRequestDto;
    private JavaMailSender javaMailSender;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        emailService = new EmailService(javaMailSender);

        brokerRequestDto = new BrokerRequestDto();
        brokerRequestDto.setTitle("testFilm");
        brokerRequestDto.setEmail("test@gmail.com");
    }

    @Test
    void sendEmail() {
        emailService.sendEmail(brokerRequestDto);
        ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender, times(1)).send(emailCaptor.capture());
    }
}