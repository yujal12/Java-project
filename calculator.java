public class calculator {
    public static String normalizeOperation(String operation) {
        if (operation == null) {
            return "";
        }

        String normalized = operation.trim().toLowerCase();

        return switch (normalized) {
            case "x", "times", "multiply" -> "*";
            case "÷", "/", "divide", "div" -> "/";
            case "+", "plus", "add" -> "+";
            case "-", "minus", "subtract", "sub" -> "-";
            case "%", "mod", "modulus", "percent" -> "%";
            case "^", "power", "pow", "**" -> "^";
            case "sqrt", "square_root", "square root", "root", "√" -> "sqrt";
            case "!", "factorial", "fact" -> "factorial";
            case "sin", "sine" -> "sin";
            case "cos", "cosine" -> "cos";
            case "tan", "tangent" -> "tan";
            case "log10", "log_10", "log", "lg" -> "log10";
            case "ln", "natural_log", "natural log" -> "ln";
            case "q", "quit", "exit" -> "q";
            case "c", "clear", "cls", "reset" -> "clear";
            case "=", "equals", "enter" -> "=";
            default -> normalized;
        };
    }

    public static boolean isBinaryOperator(String operation) {
        return "+-*/%^".contains(operation);
    }

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

    public static double factorial(double number) {
        if (number < 0 || number != Math.floor(number)) {
            throw new IllegalArgumentException("Factorial is only defined for non-negative integers.");
        }

        double result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        return result;
    }

    public static double sin(double number) {
        return Math.sin(number);
    }

    public static double cos(double number) {
        return Math.cos(number);
    }

    public static double tan(double number) {
        return Math.tan(number);
    }

    public static double log10(double number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Logarithm is only defined for positive numbers.");
        }
        return Math.log10(number);
    }

    public static double ln(double number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Natural logarithm is only defined for positive numbers.");
        }
        return Math.log(number);
    }
}
