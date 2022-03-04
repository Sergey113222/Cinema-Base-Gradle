package com.example.service;

import com.example.cinemabasegradle.dto.BrokerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService {

    private static final String CINEMA_BASE = "CinemaBase";

    private final JavaMailSender javaMailSender;

    void sendEmail(BrokerRequestDto requestDto) {
        String message = "Film " + requestDto.getTitle() + " successfully added to favourite ";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(requestDto.getEmail());

        simpleMailMessage.setSubject(CINEMA_BASE);
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
    }
}