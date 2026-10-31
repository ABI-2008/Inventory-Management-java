package inventorymanagement.app;

import java.util.Scanner;

public final class Validation {
    private Validation() { }

    public static String required(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("Input cannot be empty.");
        }
    }

    public static int nonNegativeInt(Scanner scanner, String prompt, boolean allowZero) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                if ((allowZero && value >= 0) || (!allowZero && value > 0)) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println(allowZero ? "Enter a non-negative whole number." : "Enter a positive whole number.");
        }
    }

    public static double nonNegativeDouble(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= 0) return value;
            } catch (NumberFormatException ignored) { }
            System.out.println("Enter a non-negative number.");
        }
    }

    public static int menuChoice(Scanner scanner) {
        try {
            System.out.print("Enter your choice: ");
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException exception) {
            System.out.println("Invalid choice.");
            return -1;
        }
    }
}
