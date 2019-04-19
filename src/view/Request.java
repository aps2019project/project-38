package view;

import java.util.Scanner;

public class Request {
    private static Scanner scanner = new Scanner(System.in);

    private Request() {}

    public static String getNextRequest() {
        return scanner.nextLine();
    }
}
