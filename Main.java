import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private final JTextField display;
    private String currentValue = "0";
    private String pendingOperator = "";
    private double storedValue = 0;
    private boolean isTypingNewNumber = true;

    public Main() {
        super("Java Calculator App");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 520);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("SansSerif", Font.BOLD, 30));
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] buttons = {
                "C", "⌫", "√", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "0", ".", "%", "="
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("SansSerif", Font.BOLD, 22));
            button.setFocusPainted(false);
            button.addActionListener(new ButtonHandler(text));
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void updateDisplay() {
        display.setText(currentValue);
    }

    private void appendNumber(String digit) {
        if (isTypingNewNumber) {
            currentValue = digit.equals(".") ? "0." : digit;
            isTypingNewNumber = false;
        } else {
            if (digit.equals(".")) {
                if (currentValue.contains(".")) {
                    return;
                }
                currentValue += ".";
            } else {
                currentValue += digit;
            }
        }
        updateDisplay();
    }

    private void applyOperator(String operator) {
        try {
            if (!pendingOperator.isEmpty() && !isTypingNewNumber) {
                calculateResult();
            }

            storedValue = Double.parseDouble(currentValue);
            pendingOperator = operator;
            isTypingNewNumber = true;
        } catch (NumberFormatException e) {
            currentValue = "Error";
            updateDisplay();
        }
    }

    private void calculateResult() {
        try {
            double current = Double.parseDouble(currentValue);
            double result;

            switch (pendingOperator) {
                case "+" -> result = storedValue + current;
                case "-" -> result = storedValue - current;
                case "×" -> result = storedValue * current;
                case "÷" -> {
                    if (current == 0) {
                        throw new IllegalArgumentException("Cannot divide by zero.");
                    }
                    result = storedValue / current;
                }
                case "%" -> result = (storedValue * current) / 100;
                default -> result = current;
            }

            currentValue = formatNumber(result);
            updateDisplay();
            pendingOperator = "";
            isTypingNewNumber = true;
        } catch (IllegalArgumentException e) {
            currentValue = "Error";
            updateDisplay();
            pendingOperator = "";
            isTypingNewNumber = true;
        }
    }

    private void handleSquareRoot() {
        try {
            double value = Double.parseDouble(currentValue);
            currentValue = formatNumber(calculator.squareRoot(value));
            updateDisplay();
            isTypingNewNumber = true;
        } catch (NumberFormatException e) {
            currentValue = "Error";
            updateDisplay();
        }
    }

    private void handlePercent() {
        try {
            double value = Double.parseDouble(currentValue);
            currentValue = formatNumber(value / 100);
            updateDisplay();
        } catch (NumberFormatException e) {
            currentValue = "Error";
            updateDisplay();
        }
    }

    private void clearAll() {
        currentValue = "0";
        storedValue = 0;
        pendingOperator = "";
        isTypingNewNumber = true;
        updateDisplay();
    }

    private void deleteLastDigit() {
        if (isTypingNewNumber) {
            currentValue = "0";
        } else if (currentValue.length() > 1) {
            currentValue = currentValue.substring(0, currentValue.length() - 1);
        } else {
            currentValue = "0";
            isTypingNewNumber = true;
        }
        updateDisplay();
    }

    private String formatNumber(double value) {
        if (Math.abs(value - Math.rint(value)) < 1e-9) {
            return String.format("%.0f", value);
        }
        return String.valueOf(value);
    }

    private class ButtonHandler implements ActionListener {
        private final String text;

        public ButtonHandler(String text) {
            this.text = text;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (text) {
                case "C" -> clearAll();
                case "⌫" -> deleteLastDigit();
                case "√" -> handleSquareRoot();
                case "%" -> handlePercent();
                case "=" -> {
                    if (!pendingOperator.isEmpty()) {
                        calculateResult();
                    }
                }
                case "+", "-", "×", "÷" -> applyOperator(text);
                default -> {
                    if (text.matches("[0-9]")) {
                        appendNumber(text);
                    } else if (text.equals(".")) {
                        appendNumber(".");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.out.println("Calculator app cannot run in a headless environment.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}
