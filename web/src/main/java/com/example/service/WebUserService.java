package com.example.service;

import com.example.cinemabasegradle.dto.UserDto;

import java.util.List;

public interface WebUserService {
    List<UserDto> findAllUsers();

    Long countFavouriteByUserId(Long userId);

}
