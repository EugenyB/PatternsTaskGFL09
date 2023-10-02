package patterns;

import patterns.customer.Customer;
import patterns.strategy.ConsoleOutputStrategy;
import patterns.strategy.HTMLOutputStrategy;
import patterns.strategy.OutputStrategy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Util {
    private static Scanner scanner;

    public static Scanner getScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
        return scanner;
    }

    public static int userChoice() {
        String input = getScanner().nextLine();
        checkInput(input);
        return Integer.parseInt(input.substring(0, 1));
    }

    private static boolean checkInput(String input) {
        char commandChar = input.charAt(0);
        return commandChar >= '0' && commandChar <= '9';
    }

    public static void printYesOrNo() {
        System.out.println("1. Yes\n2. No");
    }

    public static void saveToFile(Customer customer) {
        write(customer);
    }

    private static void write(Customer customer) {
        try {
            String fileName = customer.getName().toLowerCase().replace(" ", "_");
            OutputStrategy outputStrategy = customer.getOutputStrategy();
            if (outputStrategy instanceof HTMLOutputStrategy) {
                fileName += ".html";
            } else if (outputStrategy instanceof ConsoleOutputStrategy) {
                fileName += ".txt";
            }

            String statement = customer.statement();

            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write(statement);
            fileWriter.close();

            System.out.println("    File " + fileName + " was created successfully.");
        } catch (IOException e) {
            System.out.println("    Error while writing file for customer " + customer.getName());
            throw new RuntimeException(e);
        }
    }

    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
