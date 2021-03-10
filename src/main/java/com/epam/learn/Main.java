package com.epam.learn;

import com.epam.learn.calculator.Prefix;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Prefix prefix = new Prefix();
        String value = "2 - 2 * 3";
        System.out.println(prefix.parse(value));
    }
}
