package com.company;
import java.lang.String;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Validator {
    public static boolean validate(String inputExpression) {
        String expressionToTest=inputExpression;
        Pattern patternForMinusAfterDelimetr = Pattern.compile("(\\(\\-)+");
        Matcher matcherForMinusAfterDelimetr = patternForMinusAfterDelimetr.matcher(expressionToTest);
        if(matcherForMinusAfterDelimetr.find())expressionToTest=expressionToTest.replaceAll("\\(\\-","(0-");

        Pattern patternFunctions = Pattern.compile("pow10\\((\\d+|(\\d+(\\+|\\-|\\/|\\*))+\\d+)\\)" +
               "|cube\\(((\\d+|\\d+\\.\\d+)+|((\\d+|\\d+\\.\\d+)+(\\+|\\-|\\/|\\*))+(\\d+|\\d+\\.\\d+)+)\\)" +
                "|sqrt\\(((\\d+|\\d+\\.\\d+)+|((\\d+|\\d+\\.\\d+)+(\\+|\\-|\\/|\\*))+(\\d+|\\d+\\.\\d+)+)\\)");
        Matcher matcherFunctions = patternFunctions.matcher(expressionToTest);
        while(matcherFunctions.find()){
            expressionToTest=expressionToTest.replaceAll("pow10\\((\\d+|(\\d+(\\+|\\-|\\/|\\*))+\\d+)\\)" +
                    "|cube\\(((\\d+|\\d+\\.\\d+)+|((\\d+|\\d+\\.\\d+)+(\\+|\\-|\\/|\\*))+(\\d+|\\d+\\.\\d+)+)\\)" +
                    "|sqrt\\(((\\d+|\\d+\\.\\d+)+|((\\d+|\\d+\\.\\d+)+(\\+|\\-|\\/|\\*))+(\\d+|\\d+\\.\\d+)+)\\)","1+1");
            matcherFunctions = patternFunctions.matcher(expressionToTest);
        }
        Pattern patternLackOperator = Pattern.compile("(\\)\\()+|(\\d\\()+|(\\)\\d)+");
        Matcher matcherLackOperator = patternLackOperator.matcher(expressionToTest);
        if(matcherLackOperator.find())
            return false;


        Pattern patternExistenceDelimeters = Pattern.compile("\\(.*\\)");
        Matcher matcherExistenceDelimeters = patternExistenceDelimeters.matcher(expressionToTest);
        Pattern patternExistenceOpenDelimeter = Pattern.compile("\\(+");
        Matcher matcherExistenceOpenDelimeter = patternExistenceOpenDelimeter.matcher(expressionToTest);
        Pattern patternExistenceCloseDelimeter = Pattern.compile("\\)+");
        Matcher matcherExistenceCloseDelimeter = patternExistenceCloseDelimeter.matcher(expressionToTest);
        while(matcherExistenceOpenDelimeter.find()){
            if(matcherExistenceCloseDelimeter.find()){
                if(matcherExistenceDelimeters.find()){
                    int i = expressionToTest.indexOf('(');
                    expressionToTest=expressionToTest.substring(0,i)+expressionToTest.substring(i+1);
                    int j = expressionToTest.indexOf(')');
                    expressionToTest=expressionToTest.substring(0,j)+expressionToTest.substring(j+1);
                    System.out.println(expressionToTest);
                }else return false;

            }else return false;

            matcherExistenceDelimeters=patternExistenceDelimeters.matcher(expressionToTest);
            matcherExistenceOpenDelimeter=patternExistenceOpenDelimeter.matcher(expressionToTest);
            matcherExistenceCloseDelimeter=patternExistenceCloseDelimeter.matcher(expressionToTest);
        }
        if(!matcherExistenceOpenDelimeter.find()&&matcherExistenceCloseDelimeter.find())
            return false;
        Pattern patternDefaultExpression = Pattern.compile("^((\\d|\\d\\.\\d)+(\\+|\\-|\\/|\\*))+(\\d|\\d\\.\\d)+$");
        Matcher matcherDefaultExpression = patternDefaultExpression.matcher(expressionToTest);
        if(matcherDefaultExpression.find())
        return true;
        else return false;
    }
}
