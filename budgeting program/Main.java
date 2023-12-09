import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.io.FileWriter;
import java.io.IOException;

public class ExpenseTrackerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Double> expenses = new HashMap<>();
        Map<String, String> expenseCategories = new HashMap<>();

        System.out.println("Welcome to ExpenseTracker!");

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Record an Expense");
            System.out.println("2. Categorize Expenses");
            System.out.println("3. View Expenses");
            System.out.println("4. Set a Reminder");
            System.out.println("5. Generate a Report");
            System.out.println("6. Export Data");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter the expense description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter the expense amount: ");
                    double amount = scanner.nextDouble();
                    expenses.put(description, amount);
                    System.out.println("Expense recorded.");
                    break;

                case 2:
                    System.out.print("Enter the description of the expense to categorize: ");
                    String expenseToCategorize = scanner.nextLine();
                    if (expenses.containsKey(expenseToCategorize)) {
                        System.out.print("Enter the category for this expense: ");
                        String category = scanner.nextLine();
                        expenseCategories.put(expenseToCategorize, category);
                        System.out.println("Expense categorized.");
                    } else {
                        System.out.println("Expense not found.");
                    }
                    break;

                case 3:
                    viewExpenses(expenses, expenseCategories);
                    break;

                case 4:
                    System.out.print("Enter a reminder message: ");
                    String reminder = scanner.nextLine();
                    System.out.print("Set a time for the reminder in seconds (e.g., 10): ");
                    long reminderTimeInSeconds = scanner.nextLong();
                    setReminder(reminder, reminderTimeInSeconds);
                    System.out.println("Reminder set.");
                    break;

                case 5:
                    generateReport(expenses, expenseCategories);
                    break;

                case 6:
                    exportData(expenses, expenseCategories);
                    break;

                case 7:
                    System.out.println("Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private static void viewExpenses(Map<String, Double> expenses, Map<String, String> expenseCategories) {
        System.out.println("Expenses:");
        for (Map.Entry<String, Double> entry : expenses.entrySet()) {
            String expenseDescription = entry.getKey();
            double expenseAmount = entry.getValue();
            String category = expenseCategories.getOrDefault(expenseDescription, "Uncategorized");
            System.out.println(expenseDescription + " - $" + expenseAmount + " (" + category + ")");
        }
    }

    private static void setReminder(String message, long reminderTimeInSeconds) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Notification: " + message);
            }
        }, reminderTimeInSeconds * 1000); // Convert seconds to milliseconds
    }

    private static void generateReport(Map<String, Double> expenses, Map<String, String> expenseCategories) {
        // Simple report generation.
        System.out.println("Expense Report:");
        for (Map.Entry<String, Double> entry : expenses.entrySet()) {
            String expenseDescription = entry.getKey();
            double expenseAmount = entry.getValue();
            String category = expenseCategories.getOrDefault(expenseDescription, "Uncategorized");
            System.out.println(expenseDescription + " - $" + expenseAmount + " (" + category + ")");
        }
    }

    private static void exportData(Map<String, Double> expenses, Map<String, String> expenseCategories) {
        try {
            FileWriter fileWriter = new FileWriter("expense_data.txt");
            for (Map.Entry<String, Double> entry : expenses.entrySet()) {
                String expenseDescription = entry.getKey();
                double expenseAmount = entry.getValue();
                String category = expenseCategories.getOrDefault(expenseDescription, "Uncategorized");
                String line = expenseDescription + " - $" + expenseAmount + " (" + category + ")\n";
                fileWriter.write(line);
            }
            fileWriter.close();
            System.out.println("Data exported to 'expense_data.txt'.");
        } catch (IOException e) {
            System.err.println("Error exporting data: " + e.getMessage());
        }
    }
}
