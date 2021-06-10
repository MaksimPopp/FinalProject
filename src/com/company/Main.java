package com.company;
import java.util.*;
import java.lang.*;
import java.io.*;

class reversePolishNotation {
    public static Double calc(List<String> outputValue) {
        Deque<Double> stack = new ArrayDeque<Double>();
        for (String x : outputValue) {
            if (x.equals("sqrt")) stack.push(Math.sqrt(stack.pop()));
            else if (x.equals("cube")) {
                Double tmp = stack.pop();
                stack.push(tmp * tmp * tmp);
            }
            else if (x.equals("pow10")) stack.push(Math.pow(10, stack.pop()));
            else if (x.equals("+")) stack.push(stack.pop() + stack.pop());
            else if (x.equals("-")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(a - b);
            }
            else if (x.equals("*")) stack.push(stack.pop() * stack.pop());
            else if (x.equals("/")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(a / b);
            }
            else if (x.equals("u-")) stack.push(-stack.pop());
            else stack.push(Double.valueOf(x));
        }
        return stack.pop();
    }

    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        String InputString = input.nextLine();
        ExpressionParser Value = new ExpressionParser();
        List<String> expression = Value.parse(InputString);
        boolean flag = Value.flag;
        if (flag) {
            for (String x : expression) System.out.print(x + " ");
            System.out.println();
            System.out.println(calc(expression));
        }
    }
}