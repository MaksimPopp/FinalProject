package com.company;

import java.util.*;

public class ExpressionParser {
    private static String operators = "+-*/";
    private static String delimiters = "() " + operators;
    public static boolean flag = true;
    private static boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    private static boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private static boolean isFunction(String token) {
        if (token.equals("sqrt") || token.equals("cube") || token.equals("pow10")) return true;
        return false;
    }

    private static int priority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        return 4;
    }

    public static List<String> parse(String inputValue) {
        List<String> outputValue = new ArrayList<String>();
        Deque<String> stack = new ArrayDeque<String>();
        StringTokenizer tokenizer = new StringTokenizer(inputValue, delimiters, true);
        String s1 = "";
        String s2 = "";
        String s3;
        while (tokenizer.hasMoreTokens()) {
            s3 =  s2;
            s2 = tokenizer.nextToken();
            if ( isOperator(s2) && isOperator(s3)) {
                System.out.println("Некорректное выражение.");
                flag = false;
                return outputValue;
            }

            if (!tokenizer.hasMoreTokens() && isOperator(s2)) {
                System.out.println("Некорректное выражение.");
                flag = false;
                return outputValue;
            }

            if (s2.equals(" ")) continue;
            if (isFunction(s2)) stack.push(s2);
            else if (isDelimiter(s2)) {
                if (s2.equals("(")) stack.push(s2);
                else if (s2.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        outputValue.add(stack.pop());
                        if (stack.isEmpty()) {
                            System.out.println("Скобки не согласованы.");
                            flag = false;
                            return outputValue;
                        }
                    }
                    stack.pop();
                    if (!stack.isEmpty() && isFunction(stack.peek())) {
                        outputValue.add(stack.pop());
                    }
                }
                else {
                    if (s1.equals("-") && (s1.equals("") || (isDelimiter(s1)  && !s1.equals(")")))) {
                        s2 = "u-";
                    }
                    else {
                        while (!stack.isEmpty() && (priority(s2) <= priority(stack.peek()))) {
                            outputValue.add(stack.pop());
                        }

                    }
                    stack.push(s2);
                }

            }

            else {
                outputValue.add(s2);
            }
            s1 = s2;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) outputValue.add(stack.pop());
            else {
                System.out.println("Скобки не согласованы.");
                flag = false;
                return outputValue;
            }
        }
        return outputValue;
    }
}
