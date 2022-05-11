package org.example;

import java.util.Scanner;

public class Main {
    static Calculator calculator = new Calculator();
    static boolean isWork = true;

    /**
     * Main method
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (isWork) {
            System.out.println("Enter your expression (or \"EXIT\" for exit):");
            String question = scanner.nextLine();
            if (question.equals("EXIT")) {
                System.out.println("Thank you for using program. Bye.");
                isWork = false;
            } else {
                System.out.println(calc(question));
            }
        }
    }

    /**
     * Method for use calculator
     *
     * @param input String from uswer
     * @return result of calculating
     */
    public static String calc(String input) {
        String result = "";
        try {
            result = calculator.work(input);
        } catch (Exception e) {
            e.printStackTrace();
            isWork = false;
        }
        return result;
    }
}
