package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.converter.UserMapper;
import com.example.cinemabasegradle.dto.ProfileDto;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.exception.ResourceNotFoundException;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.UserRepository;
import com.example.cinemabasegradle.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles("jdbc")
class UserServiceImplTest {

    private UserMapper userMapper;
    private UserService userService;
    private UserRepository userRepository;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper);

        user = User.builder()
                .id(1L)
                .username("TestUsername2")
                .password("TestPassword2")
                .email("test2@mail.ru")
                .role(Role.ROLE_USER)
                .active(true)
                .profile(Profile.builder()
                        .avatar("xxx")
                        .firstName("Serega")
                        .lastName("Ivanov")
                        .age(20)
                        .language("en")
                        .build())
                .build();
        Profile profile = user.getProfile();
        profile.setUser(user);

        userDto = UserDto.builder()
                .id(1L)
                .username("TestUsername2")
                .password("TestPassword2")
                .email("test2@mail.ru")
                .role(Role.ROLE_USER)
                .profileDto(ProfileDto.builder()
                        .avatar("xxx")
                        .firstName("Serega")
                        .lastName("Ivanov")
                        .age(20)
                        .language("en")
                        .build()).build();
    }

    @Test
    void createUser() {
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toModel(any())).thenReturn(user);
        when(userMapper.toDto(any())).thenReturn(userDto);
        UserDto savedUserDto = userService.createUser(userDto);

        verify(userRepository).save(any());
        assertEquals(userDto.getUsername(), savedUserDto.getUsername());
        assertEquals(userDto.getEmail(), savedUserDto.getEmail());
        assertEquals(userDto.getPassword(), savedUserDto.getPassword());
        assertEquals(userDto.getRole(), savedUserDto.getRole());
        assertEquals(userDto.getProfileDto(), savedUserDto.getProfileDto());
    }

    @Test
    void findUserByName() {
        when(userRepository.findByUsername(userDto.getUsername())).thenReturn(user);
        when(userMapper.toDto(any())).thenReturn(userDto);
        UserDto existUserDto = userService.findUserByName(userDto.getUsername());

        verify(userRepository).findByUsername(any());
        assertEquals(userDto.getId(), existUserDto.getId());
        assertEquals(userDto.getUsername(), existUserDto.getUsername());
        assertEquals(userDto.getEmail(), existUserDto.getEmail());
        assertEquals(userDto.getPassword(), existUserDto.getPassword());
        assertEquals(userDto.getRole(), existUserDto.getRole());
        assertEquals(userDto.getProfileDto(), existUserDto.getProfileDto());
    }

    @Test
    void findAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        when(userRepository.findAll()).thenReturn(userList);
        when(userMapper.toDto(any())).thenReturn(userDto);
        List<UserDto> existedUsers = userService.findAllUsers();
        assertTrue(existedUsers.stream().count() > 0);
    }

    @Test
    void findUserById() {
        when(userRepository.findByIdAndActiveTrue(userDto.getId())).thenReturn(Optional.ofNullable(user));
        when(userMapper.toDto(any())).thenReturn(userDto);
        UserDto existUserDto = userService.findUserById(userDto.getId());

        verify(userRepository).findByIdAndActiveTrue(any());
        assertEquals(userDto.getId(), existUserDto.getId());
        assertEquals(userDto.getUsername(), existUserDto.getUsername());
        assertEquals(userDto.getEmail(), existUserDto.getEmail());
        assertEquals(userDto.getPassword(), existUserDto.getPassword());
        assertEquals(userDto.getRole(), existUserDto.getRole());
        assertEquals(userDto.getProfileDto(), existUserDto.getProfileDto());
    }

    @Test
    void updateUser() {
        when(userRepository.findByIdAndActiveTrue(userDto.getId())).thenReturn(Optional.ofNullable(user));
        when(userMapper.toDto(any())).thenReturn(userDto);
        when(userRepository.save(any())).thenReturn(user);
        userService.updateUser(userDto);
        verify(userRepository).save(any());
    }

    @Test
    void deleteUser() {
        userService.deleteUser(userDto.getId());
        verify(userRepository).deleteUser(userDto.getId());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user doesn't exist.")
    void throwExceptionWhenFindUserById() {
        final long nonExistingId = 12902450235L;

        doReturn(Optional.empty()).when(userRepository).findByIdAndActiveTrue(nonExistingId);
        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.findUserById(nonExistingId));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user doesn't exist.")
    void throwExceptionWhenUpdateUser() {
        final long nonExistingId = 12902450235L;
        userDto.setId(nonExistingId);
        doReturn(Optional.empty()).when(userRepository).findByIdAndActiveTrue(nonExistingId);
        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.updateUser(userDto));
    }
}