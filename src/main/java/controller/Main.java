package controller;

import controller.Database;

import java.util.Scanner;

public class Main {
    private static Database db = new Database();
    public static void main(String[] args) {
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
