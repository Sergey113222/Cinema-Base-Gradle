package com.example.cinemabasegradle;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateConverter extends AbstractSingleValueConverter {

    @Override
    public boolean canConvert(Class type) {
        return type.equals(LocalDate.class);
    }

    @Override
    public Object fromString(String dtz) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dtz, formatter);
    }
}
