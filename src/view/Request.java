package view;

import java.util.Scanner;

public abstract class Request {
    private static Scanner scanner = new Scanner(System.in);

    public static String getNextRequest() {
        return scanner.nextLine();
    }
}
