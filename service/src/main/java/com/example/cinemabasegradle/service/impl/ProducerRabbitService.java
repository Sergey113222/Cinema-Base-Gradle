package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.dto.BrokerRequestDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class ProducerRabbitService {
    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routing-key}")
    private String routingKey;

    private final RabbitTemplate template;

    public void produce(BrokerRequestDto requestDto) {
        log.info("sending message to broker" + requestDto);
        template.convertAndSend(exchange, routingKey, requestDto);
    }
}
