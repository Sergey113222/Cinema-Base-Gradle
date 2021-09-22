package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.converter.UserConverter;
import com.example.cinemabasegradle.dto.AuthenticationResponseDto;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.exception.ErrorMessages;
import com.example.cinemabasegradle.exception.ResourceNotFoundException;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import com.example.cinemabasegradle.security.jwt.JwtTokenProvider;
import com.example.cinemabasegradle.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    @Override
    public AuthenticationResponseDto createUser(UserDto userDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setRole(Role.ROLE_USER);
        User createdUser = userRepository.save(userConverter.toModel(userDto));
        log.info("In createUser - user: {} successfully created", createdUser);

        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
        authenticationResponseDto.setUsername(userDto.getUsername());
        authenticationResponseDto.setToken(jwtTokenProvider.createToken(createdUser.getProfile().getEmail(),
                new ArrayList<>(Collections.singleton(createdUser.getRole()))));
        return authenticationResponseDto;
    }

    @Override
    public UserDto findUserByName(String username) {
        UserDto userDto = userConverter.toDto(userRepository.findByUsername(username));
        log.info("In findUserByName - user: {} successfully found by username: {}", userDto, username);
        return userDto;
    }

    @Override
    public List<UserDto> findAllUsers(Sort.Direction direction, String sortColumn) {
        List<UserDto> userDtoList = userConverter.toDtoList(userRepository.findAll(
                Sort.by(direction, sortColumn)));
        log.info("In findAll - users: {} successfully found", userDtoList.size());
        return userDtoList;
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
        log.info("In findUserById - user: {} successfully found by id: {}", user, id);
        return userConverter.toDto(user);
    }

    @Transactional
    @Override
    public void updateUser(UserDto userDto) {
        Long id = userDto.getId();
        User existed = userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
        existed.setUsername(userDto.getUsername());
        existed.setPassword((userDto.getPassword()));
        log.info("In updateUser - user: {} successfully updated", existed);
        userRepository.save(existed);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
        log.info("In deleteUser successfully deleted by id: {}", id);
    }
}
