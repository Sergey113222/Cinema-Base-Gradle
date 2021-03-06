package com.example.cinemabasegradle.controller;

import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.repository.impl.UserRepositoryJdbcImpl;
import com.example.cinemabasegradle.service.XmlService;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/xml")
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class XmlController {

    private static final String FILE_PATH = "user3.xml";


    private final XmlService xmlService;
    private final UserRepositoryJdbcImpl userRepositoryJdbc;

    public XmlController(@Qualifier("jaxb") XmlService xmlService, UserRepositoryJdbcImpl userRepositoryJdbc) {
        this.xmlService = xmlService;
        this.userRepositoryJdbc = userRepositoryJdbc;
    }

    @PostMapping(value = "/marshalXml")
    public void marshalXml() {
        List<User> userList = userRepositoryJdbc.findAll();
        xmlService.marshal(userList, FILE_PATH);
    }

    @PostMapping(value = "/unmarshalXml")
    public List<User> unmarshalXml() {
        return xmlService.unmarshal(FILE_PATH);
    }
}
