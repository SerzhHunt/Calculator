package com.epam.learn.calculator;

import com.epam.learn.calculator.logic.Operator;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

public class Calculator {
    private static volatile Calculator instance;

    private Calculator() {
    }

    public static Calculator getInstance() {
        Calculator localInstance = instance;
        if (localInstance == null) {
            synchronized (Calculator.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Calculator();
                }
            }
        }
        return localInstance;
    }

    private static final String ANY_WHITESPACE_CHARACTER = "\\s+";
    private final Deque<Operator> operatorStack = new ArrayDeque<>();
    private final Deque<BigDecimal> outputStack = new ArrayDeque<>();

    public BigDecimal evaluateExpression(String infix) {
        String replace = infix.replace("(-", "(0-");

        for (String currentToken : replace.split(ANY_WHITESPACE_CHARACTER)) {
            Operator currentOperator = Operator.from(currentToken);

            if (currentOperator == Operator.NIL) { //number (or error from unknown operator)
                outputStack.push(BigDecimal.valueOf(Double.parseDouble(currentToken)));
            } else {
                while (!operatorStack.isEmpty() && outputStack.size() >= 2 &&
                        !operatorStack.peek().isHigherPrecedence(currentOperator)) {

                    Operator higherPrecedenceOperator = operatorStack.pop();
                    higherPrecedenceOperator.evaluate(outputStack, operatorStack);
                }
                operatorStack.push(currentOperator);
            }
        }
        while (!operatorStack.isEmpty()) {
            operatorStack.pop().evaluate(outputStack, operatorStack);
        }
        return outputStack.pop();
    }
}
