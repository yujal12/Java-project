import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first number: ");
        double firstNumber = scanner.nextDouble();

        System.out.print("Enter an operator (+, -, *, /): ");
        char operator = scanner.next().charAt(0);

        System.out.print("Enter second number: ");
        double secondNumber = scanner.nextDouble();

        try {
            double result = calculator.calculate(firstNumber, operator, secondNumber);
            System.out.println("Result: " + result);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
        }

        scanner.close();
    }
}
