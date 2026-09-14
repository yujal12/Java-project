public class calculator {
    public static double calculate(double firstNumber, char operator, double secondNumber) {
        return switch (operator) {
            case '+' -> firstNumber + secondNumber;
            case '-' -> firstNumber - secondNumber;
            case '*' -> firstNumber * secondNumber;
            case '/' -> {
                if (secondNumber == 0) {
                    throw new IllegalArgumentException("Cannot divide by zero.");
                }
                yield firstNumber / secondNumber;
            }
            case '%' -> firstNumber * secondNumber / 100;
            case '^' -> Math.pow(firstNumber, secondNumber);
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };
    }

    public static double squareRoot(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate the square root of a negative number.");
        }
        return Math.sqrt(number);
    }
}
