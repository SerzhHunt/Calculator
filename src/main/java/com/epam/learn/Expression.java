package com.epam.learn;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

public class Expression {
//    private static final String ANY_WHITESPACE_CHARACTER = "\\s";
    private final Deque<Operator> operatorStack = new ArrayDeque<>();
    private final Deque<BigDecimal> outputStack = new ArrayDeque<>();

    public BigDecimal evaluateExpression(String infix) {
        String replace = infix.replace("(-", "(0-");

        for (String currentToken : replace.split(" ")) {
            Operator currentOperator = Operator.from(currentToken);

            if (currentOperator == Operator.NIL) { //number (or error from unknown operator?)
                outputStack.push(BigDecimal.valueOf(Double.parseDouble(currentToken)));
            } else {

                while (!operatorStack.isEmpty() && outputStack.size()>=2 &&
                        !operatorStack.peek().isHigherPrecedence(currentOperator)) {

                    Operator higherPrecedenceOperator = operatorStack.pop();
                    higherPrecedenceOperator.evaluate(outputStack, operatorStack);
                }
                operatorStack.push(currentOperator);
            }
        }
        while (!operatorStack.isEmpty()&&outputStack.size()>=2) {
            operatorStack.pop().evaluate(outputStack, operatorStack);
        }
        return outputStack.pop();
    }
}
