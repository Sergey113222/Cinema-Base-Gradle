package com.example.service.impl;

import com.example.cinemabasegradle.dto.UserDto;
import com.example.service.WebUserService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public class WebUserServiceImpl implements WebUserService {

    private final RestTemplate restTemplate;

    @Value("${cinema-base.scheme}")
    private String scheme;

    @Value("${cinema-base.host}")
    private String host;

    @Value("${cinema-base.port}")
    private Integer port;

    @Value("${cinema-base.user.all}")
    private String allUsers;

    @Value("${cinema-base.favourite.count}")
    private String favouriteCount;

    @Override
    public List<UserDto> findAllUsers() {
        URI uri = createURI(allUsers).build().toUri();
        ResponseEntity<List<UserDto>> request = restTemplate
                .exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        return request.getBody();
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
                .port(port)
                .path(path);
    }
}
