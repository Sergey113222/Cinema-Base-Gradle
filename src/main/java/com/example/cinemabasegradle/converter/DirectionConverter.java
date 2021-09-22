package com.example.cinemabasegradle.converter;

import org.springframework.data.domain.Sort;

public interface DirectionConverter {

    Sort.Direction replaceStringThroughDirection(String sortDirection);
}
