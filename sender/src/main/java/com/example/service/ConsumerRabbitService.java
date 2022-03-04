package com.example.service;


import com.example.cinemabasegradle.dto.BrokerRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerRabbitService {

    private final EmailService emailService;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void listen(BrokerRequestDto brokerRequestDto) {
        log.info("sending email from request" + brokerRequestDto);
        emailService.sendEmail(brokerRequestDto);
    }
}