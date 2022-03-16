package com.example.service;


import com.example.cinemabasegradle.dto.KafkaRequestDto;
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
    public void consume(KafkaRequestDto kafkaRequestDto) {

        MessageMongo messageMongo = new MessageMongo();
        messageMongo.setTitle(kafkaRequestDto.getTitle());
        messageMongo.setEmail(kafkaRequestDto.getEmail());

        log.info("save email and movie_title from request" + kafkaRequestDto);
        messageRepository.insert(messageMongo);
    }
}
