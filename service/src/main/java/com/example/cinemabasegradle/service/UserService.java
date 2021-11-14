package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserDto createUser(UserDto userDto);

    List<UserDto> findAllUsers();

    UserDto findUserByName(String username);

    UserDto findUserById(Long id);

    void updateUser(UserDto userDto);

    void deleteUser(Long id);
}
