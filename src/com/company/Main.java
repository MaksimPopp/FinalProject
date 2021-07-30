package com.company;
import java.util.*;
import java.lang.*;
import java.io.*;


class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String InputString = scanner.nextLine();
        Parser expressionBeforeConverted = new Parser();
        if(Validator.validate(InputString))
        {
            List<String> parseExpression = expressionBeforeConverted.parse(InputString);
            boolean flag = expressionBeforeConverted.flag;
            if (flag) {
                for (String x : parseExpression) System.out.print(x + " ");
                System.out.println();
                System.out.println(Calculator.calculate(parseExpression));
            }
        }else
            System.out.println("Некорректное выражение.");

    }
}