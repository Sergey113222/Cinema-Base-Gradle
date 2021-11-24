package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.model.User;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

@Component
public class JAXBConverterXmlToObject {

    public List<User> unmarshal(String filePath) {

        JAXBContext jaxbContext = null;
        try {

            // Normal JAXB RI
            jaxbContext = JAXBContext.newInstance(UserList.class);

            File file = new File(filePath);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            UserList userList = (UserList) jaxbUnmarshaller.unmarshal(file);

            return userList.getUserList();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
