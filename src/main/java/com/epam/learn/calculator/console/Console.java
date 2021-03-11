package com.epam.learn.calculator.console;

import com.epam.learn.calculator.Calculator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class Console implements Runnable {

    @Override
    public void run() {
        Calculator calculator = Calculator.getInstance();
        Logger logger = LogManager.getLogger();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            logger.log(Level.INFO, "Enter your expression. Use space to separate number and operands");

            BigDecimal answer = calculator.evaluateExpression(reader.readLine());

            logger.log(Level.INFO, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
