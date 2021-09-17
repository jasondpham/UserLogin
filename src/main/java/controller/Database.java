package controller;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {
    private List<User> users;

    public Database() {
        users = new ArrayList<>();
        addUser("phamjason", "123");
        addUser("jasonpham", "234");
        loadDatabase();
    }

    private void loadDatabase() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/users.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            users = Arrays.asList(objectMapper.readValue(br, User[].class));
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return users;
    }

    private String pwHash(String pass) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (Exception ignored) {

        }
        return null;
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public boolean isValidCred(String user, String pass) {
        for (User curr : users) {
            if (curr.getUsername().equals(user)) {
                return pwHash(pass).equals(curr.getPassword());
            }
        }
        return false;
    }

    public String addUser(String user, String pass) {
        if (user != null) {
            for (User curr : users) {
                if (curr.getUsername().equals(user)) {
                    return null;
                }
            }
            users.add(new User(user, pwHash(pass)));
            ObjectMapper mapper = new ObjectMapper();
            mapper.writer(new DefaultPrettyPrinter());
            try {
                mapper.writeValue(new File("resources/users.json"), users);
            } catch (Exception ignored) {

            }
        }
        return null;
    }
}
