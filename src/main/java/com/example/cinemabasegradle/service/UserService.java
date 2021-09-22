package com.example.cinemabasegradle.service;

import com.example.cinemabasegradle.dto.AuthenticationResponseDto;
import com.example.cinemabasegradle.dto.UserDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    AuthenticationResponseDto createUser(UserDto userDto);

    List<UserDto> findAllUsers(Sort.Direction direction, String sortColumn);

    UserDto findUserByName(String username);

    UserDto findUserById(Long id);

    void updateUser(UserDto userDto);

    void deleteUser(Long id);
}
