package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.model.User;

import java.util.List;

public interface XmlService {

    void marshal(List<User> userList, String filePath);

    List<User> unmarshal(String filePath);
}
