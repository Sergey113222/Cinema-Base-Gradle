package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.dto.BrokerRequestDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class ProducerKafkaService {

    @Value("${spring.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, BrokerRequestDto> kafkaTemplateObject;

    public void produceObject(BrokerRequestDto brokerRequestDto) {
        log.info("sending message to broker" + brokerRequestDto);
        kafkaTemplateObject.send(topic, brokerRequestDto);
    }
}