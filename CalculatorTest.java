public class CalculatorTest {
    private static void assertAlmostEquals(String label, double actual, double expected, double tolerance) {
        double difference = Math.abs(actual - expected);
        if (difference > tolerance) {
            throw new AssertionError(label + " failed: expected " + expected + " but got " + actual + " (diff=" + difference + ")");
        }
    }

    private static void assertEquals(String label, double actual, double expected) {
        if (actual != expected) {
            throw new AssertionError(label + " failed: expected " + expected + " but got " + actual);
        }
    }

    public static void main(String[] args) {
        assertEquals("Addition", calculator.calculate(2, '+', 3), 5.0);
        assertEquals("Subtraction", calculator.calculate(10, '-', 4), 6.0);
        assertEquals("Multiplication", calculator.calculate(4, '*', 5), 20.0);
        assertEquals("Division", calculator.calculate(20, '/', 4), 5.0);
        assertEquals("Percentage", calculator.calculate(200, '%', 10), 20.0);
        assertEquals("Power", calculator.calculate(2, '^', 3), 8.0);
        assertEquals("Square root", calculator.squareRoot(9), 3.0);
        assertEquals("Factorial", calculator.factorial(5), 120.0);
        assertAlmostEquals("Sine", calculator.sin(0), 0.0, 1e-9);
        assertAlmostEquals("Cosine", calculator.cos(0), 1.0, 1e-9);
        assertAlmostEquals("Tangent", calculator.tan(0), 0.0, 1e-9);
        assertAlmostEquals("Log10", calculator.log10(100), 2.0, 1e-9);
        assertAlmostEquals("Natural log", calculator.ln(1), 0.0, 1e-9);

        System.out.println("All calculator tests passed.");
    }
}
