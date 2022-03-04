package com.example.service;


import com.example.cinemabasegradle.dto.BrokerRequestDto;
import com.example.dao.MessageRepository;
import com.example.model.MessageMongo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConsumerKafkaService {
    private final MessageRepository messageRepository;

    @KafkaListener(topics = "cinema", groupId = "cinema_group_id")
    public void consume(BrokerRequestDto brokerRequestDto) {

        MessageMongo messageMongo = new MessageMongo();
        messageMongo.setTitle(brokerRequestDto.getTitle());
        messageMongo.setEmail(brokerRequestDto.getEmail());

        log.info("save email and movie_title from request" + brokerRequestDto);
        messageRepository.insert(messageMongo);
    }
}
