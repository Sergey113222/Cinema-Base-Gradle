package com.example.cinemabasegradle.service.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import liquibase.pro.packaged.S;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class ProducerRabbitService {
    private final static String HOST_NAME = "localhost";
    private final static String QUEUE_NAME = "cinema-base-queue";



    public void produce(String movieName, Long userId) {

        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST_NAME);

        try (final Connection connection = connectionFactory.newConnection();
             final Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            final String message = "Film " + movieName + " successfully added to favourite ";
            channel.basicPublish(" ", QUEUE_NAME, null, message.getBytes("UTF-8"));

        } catch (IOException e) {
            log.error("Cannot connect ", e);
        } catch (TimeoutException e) {
            log.error("Problem with timeout ", e);
        }
    }
}
