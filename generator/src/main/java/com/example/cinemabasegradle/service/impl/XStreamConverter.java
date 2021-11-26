package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.LocalDateConverter;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.service.XmlService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Qualifier("xStream")
public class XStreamConverter implements XmlService {

    public void marshal(List<User> userList, String filePath) {

        XStream xstream = new XStream(new StaxDriver());
        xstream.registerConverter(new LocalDateConverter());
        xstream.alias("user_list", UserList.class);
        xstream.alias("user", User.class);
        xstream.alias("profile", Profile.class);

        try {
            xstream.toXML(userList, new FileWriter(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> unmarshal(String filePath) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.registerConverter(new LocalDateConverter());
        xstream.alias("user_list", UserList.class);
        xstream.alias("user", User.class);
        xstream.alias("profile", Profile.class);

        InputStream in = null;
        try {
            in = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return (List<User>) xstream.fromXML(in);
    }
}
