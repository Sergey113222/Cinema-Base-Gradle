package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.converter.UserMapper;
import com.example.cinemabasegradle.dto.UserDto;
import com.example.cinemabasegradle.exception.ErrorMessages;
import com.example.cinemabasegradle.exception.ResourceNotFoundException;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.RoleRepository;
import com.example.cinemabasegradle.repository.UserRepository;
import com.example.cinemabasegradle.service.UserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class UserServiceImpl implements UserService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String MESSAGE = "Can not save user with ROLE_ADMIN";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        User user = userMapper.toModel(userDto);
        user.setActive(true);

        List<Role> roles = user.getRoles();
        List<Role> rolesFromDB = new ArrayList<>();
        for (Role role : roles) {
            if (!role.getName().contains(ROLE_ADMIN)) {
                rolesFromDB.add(roleRepository.findByName(role.getName()));
            } else {
                throw new IllegalArgumentException(MESSAGE);
            }
        }
        user.setRoles(rolesFromDB);
        Profile profile = user.getProfile();
        profile.setUser(user);

        User createdUser = userRepository.save(user);
        log.info("In createUser - user: {} successfully created", createdUser);
        return userDto;
    }

    @Override
    public UserDto findUserByName(String username) {
        UserDto userDto = userMapper.toDto(userRepository.findByUsername(username));
        log.info("In findUserByName - user: {} successfully found by username: {}", userDto, username);
        return userDto;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserDto> userDtoList = userRepository.findAll().stream().map(userMapper::toDto)
                .collect(Collectors.toList());
        log.info("In findAll - users: {} successfully found", userDtoList.size());
        return userDtoList;
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepository.findByIdAndActiveTrue(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
        log.info("In findUserById - user: {} successfully found by id: {}", user, id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDto userDto) {
        Long id = userDto.getId();
        User existed = userRepository.findByIdAndActiveTrue(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.RESOURCE_NOT_FOUND, id)));
        existed.setUsername(userDto.getUsername());
        existed.setPassword((userDto.getPassword()));
        existed.setEmail(userDto.getEmail());

        Profile profile = new Profile();
        profile.setId(existed.getProfile().getId());
        profile.setAvatar(userDto.getProfileDto().getAvatar());
        profile.setFirstName(userDto.getProfileDto().getFirstName());
        profile.setLastName(userDto.getProfileDto().getLastName());
        profile.setAge(userDto.getProfileDto().getAge());
        profile.setLanguage(userDto.getProfileDto().getLanguage());
        profile.setCreated(existed.getCreated());
        profile.setUpdated(existed.getUpdated());
        profile.setUser(existed);
        existed.setProfile(profile);
        log.info("In updateUser - user: {} successfully updated", existed);
        userRepository.save(existed);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
        log.info("In deleteUser successfully deleted by id: {}", id);
    }
}
