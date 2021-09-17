package controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private Map<String, String> users;

    public Database() {
        users = new HashMap<>();
        loadDatabase();
    }

    private void loadDatabase() {
    }

    public Map<String, String> getUsers() {
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
        if (users.get(user) != null) {
            return users.get(user).equals(pwHash(pass));
        }
        return false;
    }

    public String addUser(String user, String pass) {
        if (user != null || !users.containsKey(user)) {
            users.put(user, pwHash(pass));
            return user;
        }
        return null;
    }
}
