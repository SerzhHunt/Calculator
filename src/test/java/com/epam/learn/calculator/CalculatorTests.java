package com.epam.learn.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTests {
    Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = Calculator.getInstance();
    }

    @Test
    public void simpleExpressionTest() {
        assertEquals(BigDecimal.valueOf(15.0), calculator.evaluateExpression("5 + 4 - 2 + 8"));
        assertEquals(BigDecimal.valueOf(10.67), calculator.evaluateExpression("7 + 2 / 3 + 1 * 3"));
        assertEquals(BigDecimal.valueOf(5.7993), calculator.evaluateExpression("3.21 + 4.11 * 2 / 3.2 "));
        assertEquals(BigDecimal.valueOf(0.1).stripTrailingZeros(),
                calculator.evaluateExpression("4.2 + 2 * 3 / 3 - 6.1").stripTrailingZeros());
    }

    @Test
    public void testWithBrackets() {
        assertEquals(BigDecimal.valueOf(5.0).stripTrailingZeros(),
                calculator.evaluateExpression("( 5 + 5 ) / 2").stripTrailingZeros());
        assertEquals(BigDecimal.valueOf(12.0).stripTrailingZeros(),
                calculator.evaluateExpression("(    -2 ) - ( ( -4 ) * 3.5 )").stripTrailingZeros());
    }

    @Test
    public void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calculator.evaluateExpression("100 / 0"));
    }
}
