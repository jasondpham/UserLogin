package controller;

import controller.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Main {
    private static Database db = new Database();
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your username:");
        String user = s.nextLine();
        System.out.println("Please enter your password:");
        String pass = s.nextLine();
        if (db.isValidCred(user, pass)) {
            System.out.println("Welcome!");
        } else {
            System.out.println("That username or password does not exist.");
        }
        s.close();
    }

}
