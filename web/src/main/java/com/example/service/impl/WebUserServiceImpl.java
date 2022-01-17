package com.example.service.impl;

import com.example.cinemabasegradle.dto.UserDto;
import com.example.service.WebUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebUserServiceImpl implements WebUserService {

    private final RestTemplate restTemplate;

    @Value("${cinema-base.scheme}")
    private String scheme;

    @Value("${cinema-base.host}")
    private String host;

    @Value("${cinema-base.user.all}")
    private String allUsers;

    @Value("${cinema-base.favourite.count}")
    private String favouriteCount;

    @Override
    public List<UserDto> findAllUsers() {

        URI uri = createURI(allUsers).build().toUri();
        UserDto[] request = restTemplate.getForObject(uri, UserDto[].class);
        return Arrays.asList(request);
    }

    @Override
    public Long countFavouriteByUserId(@PathVariable("user_id") Long id) {
        URI uri = createURI(favouriteCount + id).build().toUri();
        return restTemplate.getForObject(uri, Long.class);
    }

    private UriComponentsBuilder createURI(String path) {
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(8080)
                .path(path);
    }
}
