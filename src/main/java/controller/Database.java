package controller;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Database {
    private List<User> users;
    private Connection connection;

    /**
     * Initializes the database
     */
    public Database() {
        users = new ArrayList<>();
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "java", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDatabase() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/users.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            users = Arrays.asList(objectMapper.readValue(br, User[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        String search = "select * from userinfo where username=?";
        try {
            PreparedStatement query = connection.prepareStatement(search);
            query.setString(1, user);
            ResultSet users = query.executeQuery();
            users.next();
            return Objects.equals(pwHash(pass), users.getString(3));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addUser(String user, String pass) {
        try {
            Statement insertStatement = connection.createStatement();
            String query = "select count(*) from userinfo where username=";
            query += "'" + user + "'";
            ResultSet users = insertStatement.executeQuery(query);
            users.next();
            if (users.getInt(1) == 0) {
                String insert = "insert into userinfo values (NULL,?, ?)";
                insertStatement = connection.prepareStatement(insert);
                ((PreparedStatement) insertStatement).setString(1, user);
                ((PreparedStatement) insertStatement).setString(2, pwHash(pass));
                ((PreparedStatement) insertStatement).executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
