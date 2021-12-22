package com.example.service;


import com.example.cinemabasegradle.dto.RabbitRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerService {

    private final EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(RabbitRequestDto rabbitRequestDto) {
        log.info("sending email from request" + rabbitRequestDto);
        emailService.sendEmail(rabbitRequestDto);
    }
}