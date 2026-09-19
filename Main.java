import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main extends JFrame {
    private final JTextField display;
    private String currentValue = "0";
    private String pendingOperator = "";
    private double storedValue = 0;
    private boolean isTypingNewNumber = true;

    public Main() {
        super("Calculator");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 520);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(28, 31, 36));

        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("SansSerif", Font.BOLD, 30));
        display.setBackground(new Color(245, 247, 250));
        display.setForeground(new Color(30, 30, 30));
        display.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 8, 8));
        buttonPanel.setBackground(new Color(28, 31, 36));
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
            button.setBackground(getButtonColor(text));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            button.addActionListener(new ButtonHandler(text));
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        display.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char key = e.getKeyChar();
                if (Character.isDigit(key) || key == '.') {
                    appendNumber(String.valueOf(key));
                    e.consume();
                } else if (key == '+' || key == '-' || key == '*' || key == '/' || key == '%') {
                    String op = switch (key) {
                        case '+' -> "+";
                        case '-' -> "-";
                        case '*' -> "×";
                        case '/' -> "÷";
                        case '%' -> "%";
                        default -> "";
                    };
                    if (!op.isEmpty()) {
                        applyOperator(op);
                        e.consume();
                    }
                } else if (key == '\n' || key == '=') {
                    if (!pendingOperator.isEmpty()) {
                        calculateResult();
                    }
                    e.consume();
                } else if (key == 'c' || key == 'C') {
                    clearAll();
                    e.consume();
                } else if (key == '\b') {
                    deleteLastDigit();
                    e.consume();
                }
            }
        });
    }

    private Color getButtonColor(String text) {
        if (text.matches("[0-9]|\\.")) {
            return new Color(77, 83, 92);
        }
        if (text.equals("C") || text.equals("⌫")) {
            return new Color(220, 91, 70);
        }
        if (text.equals("√") || text.equals("%") || text.equals("÷") || text.equals("×") || text.equals("-") || text.equals("+") || text.equals("=")) {
            return new Color(54, 148, 214);
        }
        return new Color(89, 95, 105);
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
