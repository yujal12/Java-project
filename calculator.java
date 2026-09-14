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
            default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
        };
    }
}
