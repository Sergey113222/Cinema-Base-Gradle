package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.dto.RabbitRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ProducerRabbitServiceTest {

    private ProducerRabbitService producerRabbitService;
    private RabbitRequestDto rabbitRequestDto;
    private RabbitTemplate rabbitTemplate;

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        producerRabbitService = new ProducerRabbitService(rabbitTemplate);
        ReflectionTestUtils.setField(producerRabbitService, "exchange", "exchange");
        ReflectionTestUtils.setField(producerRabbitService, "routingKey", "routing-key");
        rabbitRequestDto = new RabbitRequestDto();
        rabbitRequestDto.setTitle("testFilm");
        rabbitRequestDto.setEmail("test@gmail.com");
    }

    @Test
    void produce() {
        producerRabbitService.produce(rabbitRequestDto);
        verify(rabbitTemplate, Mockito.times(1)).convertAndSend("exchange", "routing-key", rabbitRequestDto);
    }
}