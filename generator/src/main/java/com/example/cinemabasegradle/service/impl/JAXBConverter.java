package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.service.XmlService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

@Service
@Qualifier("jaxb")
public class JAXBConverter implements XmlService {

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
