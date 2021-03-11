package com.epam.learn.calculator.logic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Deque;

public enum Operator {
    ADD(1, "+") {
        @Override
        public void evaluate(Deque<BigDecimal> numbers, Deque<Operator> operatorStack) {
            BigDecimal right = numbers.pop();
            BigDecimal left = numbers.pop();
            numbers.push(left.add(right));
        }
    },
    SUBTRACT(2, "-") {
        @Override
        public void evaluate(Deque<BigDecimal> numbers, Deque<Operator> operatorStack) {
            BigDecimal right = numbers.pop();
            BigDecimal left = numbers.pop();
            numbers.push(left.subtract(right));
        }
    },
    MULTIPLY(3, "*") {
        @Override
        public void evaluate(Deque<BigDecimal> numbers, Deque<Operator> operatorStack) {
            BigDecimal right = numbers.pop();
            BigDecimal left = numbers.pop();
            numbers.push(left.multiply(right));
        }
    },
    DIVIDE(4, "/") {
        @Override
        public void evaluate(Deque<BigDecimal> numbers, Deque<Operator> operatorStack) {
            BigDecimal right = numbers.pop();
            BigDecimal left = numbers.pop();
            numbers.push(left.divide(right, 2, RoundingMode.HALF_UP));
        }
    },
    OPEN_BRACKET(Integer.MIN_VALUE, "(") {
        @Override
        public void evaluate(Deque<BigDecimal> numbers, Deque<Operator> operatorStack) {
            operatorStack.push(this);
        }
    },
    CLOSE_BRACKET(Integer.MAX_VALUE, ")") {
        @Override
        public void evaluate(Deque<BigDecimal> numbers, Deque<Operator> operatorStack) {
            while (!operatorStack.isEmpty() && operatorStack.peek() != OPEN_BRACKET) {
                operatorStack.pop().evaluate(numbers, operatorStack);
            }
            if (operatorStack.isEmpty()) {
                //no open bracket found!
                throw new IllegalStateException("closing bracket requires earlier matching opening bracket");
            }
            operatorStack.pop();
        }
    },
    NIL(0, "") {
        @Override
        public void evaluate(Deque<BigDecimal> numbers, Deque<Operator> operatorStack) {
            throw new IllegalStateException("trying to evaluate invalid operator");
        }
    };

    private final int precedence;
    private final String sign;

    Operator(int precedence, String sign) {
        this.precedence = precedence;
        this.sign = sign;
    }

    public static Operator from(String value) {
        for (Operator operator : Operator.values()) {
            if (operator.sign.equalsIgnoreCase(value)) {
                return operator;
            }
        }
        return NIL;
    }

    public boolean isHigherPrecedence(Operator other) {
        return this.precedence <= other.precedence;
    }

    public abstract void evaluate(Deque<BigDecimal> numbers, Deque<Operator> operatorStack);
}

