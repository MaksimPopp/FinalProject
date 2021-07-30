package com.company;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Calculator {
    public static Double calculate(List<String> outputValue) {
    Deque<Double> stack = new ArrayDeque<Double>();
    for (String convertedValue : outputValue){
        switch(convertedValue){
            case "sqrt":stack.push(Math.sqrt(stack.pop()));
                break;
            case "cube":Double tmp = stack.pop();
                stack.push(tmp * tmp * tmp);
                break;
            case "pow10":stack.push(Math.pow(10, stack.pop()));
                break;
            case "+":stack.push(stack.pop() + stack.pop());
                break;
            case "-": Double subtrahend = stack.pop(), minued = stack.pop();
                stack.push(minued - subtrahend);
                break;
            case "*":stack.push(stack.pop() * stack.pop());
                break;
            case "/":Double divider = stack.pop(), dividend = stack.pop();
                stack.push(dividend / divider);
                break;
            case "u-":stack.push(-stack.pop());
                break;
            default:stack.push(Double.valueOf(convertedValue));

        }
    }
    return stack.pop();
}
}
