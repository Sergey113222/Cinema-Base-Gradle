package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.model.User;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;

@Component
public class JAXBConverterObjectToXml {

    public void marshal(List<User> userList, String filePath) {

        JAXBContext jaxbContext = null;

        try {

            jaxbContext = JAXBContext.newInstance(UserList.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            UserList users = new UserList();
            users.setUserList(userList);

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // output to a xml file
            jaxbMarshaller.marshal(users, new File(filePath));

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
