package com.example.dao;

import com.example.model.MessageMongo;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MessageRepository extends MongoRepository<MessageMongo, String> {
}
