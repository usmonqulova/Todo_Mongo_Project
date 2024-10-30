package uz.pdp.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import uz.pdp.model.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserService {
    private final String PATH = "D:\\dasturlash\\JAVA\\helper\\src\\main\\java\\uz\\pdp\\file\\users.xml";

    public User add(User user) {
        ArrayList<User> users = read();
        users.add(user);
        write(users);
        return user;
    }

    private ArrayList<User> read() {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            File file = new File(PATH);
            if (!file.exists()) {
                return new ArrayList<>(); // Fayl yo'q bo'lsa, bo'sh ro'yxat qaytarish
            }
            return xmlMapper.readValue(file, new TypeReference<ArrayList<User>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void write(ArrayList<User> users) {
        XmlMapper xmlMapper = new XmlMapper();
        try {
            xmlMapper.writeValue(new File(PATH), users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
