package com.company;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Parser {
    public static String operators = "+-*/";
    public static String delimiters = "()" + operators;
    public static boolean flag = true;

    public static boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    public static boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    public static boolean isFunction(String token) {
        if (token.equals("sqrt") || token.equals("cube") || token.equals("pow10")) return true;
        return false;
    }

    public static int operatorPriority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        return 4;
    }

    public static List<String> parse(String inputExpression) {
        Pattern patternConvertedMinus = Pattern.compile("(\\(\\-)+");
        Matcher matcherConvertedMinus = patternConvertedMinus.matcher(inputExpression);
        if(matcherConvertedMinus.find())inputExpression=inputExpression.replaceAll("\\(\\-","(0-");

        List<String> outputExpression = new ArrayList<String>();
        Deque<String> valueStackRPN = new ArrayDeque<String>();
        StringTokenizer tokenizerForInputValue = new StringTokenizer(inputExpression, delimiters, true);
        String tokenForProcessing1 = "";
        String tokenForProcessing2 = "";

        while (tokenizerForInputValue.hasMoreTokens()) {
            tokenForProcessing2 = tokenizerForInputValue.nextToken();
            if (isFunction(tokenForProcessing2)) valueStackRPN.push(tokenForProcessing2);
             else if (isDelimiter(tokenForProcessing2)) {
                     if (tokenForProcessing2.equals("(")) valueStackRPN.push(tokenForProcessing2);
                      else if (tokenForProcessing2.equals(")")) {
                       while (!valueStackRPN.peek().equals("(")) {
                           outputExpression.add(valueStackRPN.pop());
                       }
                       valueStackRPN.pop();
                        if (!valueStackRPN.isEmpty() && isFunction(valueStackRPN.peek())) outputExpression.add(valueStackRPN.pop());
                      } else {
                          if (tokenForProcessing1.equals("-") && (tokenForProcessing1.equals("") || (isDelimiter(tokenForProcessing1) && !tokenForProcessing1.equals(")")))) {
                           tokenForProcessing2 = "u-";
                          } else {
                              while (!valueStackRPN.isEmpty() && (operatorPriority(tokenForProcessing2) <= operatorPriority(valueStackRPN.peek()))) {
                                  outputExpression.add(valueStackRPN.pop());
                              }
                           }
                           valueStackRPN.push(tokenForProcessing2);
                       }
             } else {
                outputExpression.add(tokenForProcessing2);
             }
            tokenForProcessing1 = tokenForProcessing2;
        }
            while (!valueStackRPN.isEmpty()) {
            if (isOperator(valueStackRPN.peek())) outputExpression.add(valueStackRPN.pop());
            }
        return outputExpression;
    }
}
