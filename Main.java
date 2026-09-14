import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Calculator");
        System.out.println("Operators: +, -, *, /, %, ^, sqrt");

        while (true) {
            System.out.print("\nEnter an operation (or q to quit): ");
            String operation = scanner.next().toLowerCase();

            if (operation.equals("q") || operation.equals("quit")) {
                break;
            }

            try {
                if (operation.equals("sqrt")) {
                    System.out.print("Enter a number: ");
                    double number = scanner.nextDouble();
                    System.out.println("Result: " + calculator.squareRoot(number));
                    continue;
                }

                if (operation.length() != 1 || !"+-*/%^".contains(operation)) {
                    throw new IllegalArgumentException("Unsupported operation: " + operation);
                }

                System.out.print("Enter first number: ");
                double firstNumber = scanner.nextDouble();

                System.out.print("Enter second number: ");
                double secondNumber = scanner.nextDouble();

                double result = calculator.calculate(firstNumber, operation.charAt(0), secondNumber);
                System.out.println("Result: " + result);
            } catch (IllegalArgumentException exception) {
                System.out.println("Error: " + exception.getMessage());
            }
        }

        scanner.close();
        System.out.println("Calculator closed.");
    }
}
