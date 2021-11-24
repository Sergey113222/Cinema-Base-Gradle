package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.impl.UserRepositoryJdbcImpl;
import com.example.cinemabasegradle.service.impl.JAXBConverterObjectToXml;
import com.example.cinemabasegradle.service.impl.JAXBConverterXmlToObject;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/xml")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class XmlController {

    private static final String FILE_PATH = "D:\\dev\\cinema-base-gradle\\user1.xml";

    private final UserRepositoryJdbcImpl userRepositoryJdbc;
    private final JAXBConverterObjectToXml jaxbConverterObjectToXml;
    private final JAXBConverterXmlToObject jaxbConverterXmlToObject;

    @PostMapping(value = "/marshalXml")
    public void marshalXml() {
        List<User> userList = userRepositoryJdbc.findAll();
        jaxbConverterObjectToXml.marshal(userList, FILE_PATH);
    }

    @PostMapping(value = "/unmarshalXml")
    public List<User> unmarshalXml() {
        return jaxbConverterXmlToObject.unmarshal(FILE_PATH);
    }
}
