package com.example.cinemabasegradle.service.impl;

import com.example.cinemabasegradle.LocalDateConverter;
import com.example.cinemabasegradle.model.Profile;
import com.example.cinemabasegradle.model.Role;
import com.example.cinemabasegradle.model.User;
import com.example.cinemabasegradle.service.XmlService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@Qualifier("xStream")
public class XStreamConverter implements XmlService {

    public void marshal(List<User> userList, String filePath) {

        XStream xstream = new XStream(new StaxDriver());
        xstream.registerConverter(new LocalDateConverter());
        xstream.alias("user_list", UserList.class);
        xstream.alias("user", User.class);
        xstream.alias("profile", Profile.class);
        xstream.alias("role", Role.class);

        try (Writer fileWriter = new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8)) {
            xstream.toXML(userList, fileWriter);
        } catch (IOException e) {
            log.error("Cannot write file", e);
        }
    }

    public List<User> unmarshal(String filePath) {
        XStream xstream = new XStream(new StaxDriver());
        xstream.registerConverter(new LocalDateConverter());
        xstream.alias("user_list", UserList.class);
        xstream.alias("user", User.class);
        xstream.alias("profile", Profile.class);
        xstream.alias("roles", Role.class);


        List<User> userList = null;
        try (InputStream in = new FileInputStream(filePath)) {
            userList = (List<User>) xstream.fromXML(in);
        } catch (FileNotFoundException e) {
            log.error("Cannot read file", e);
        } catch (IOException e) {
            log.error("Fail to close stream", e);
        }
        return userList;
    }
}
