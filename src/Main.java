import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your username:");
        String user = s.nextLine();
        System.out.println("Please enter your password");
        String pass = s.nextLine();
        if (isValidCred(user, pass)) {
            System.out.println("Welcome!");
        } else {
            System.out.println("That username or password does not exist.");
        }
    }

    private static boolean isValidCred(String user, String pass) {
        return user.equals("phamjason") && pass.equals("123");
    }
}
