import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Main {

    enum Operation {
        ADDITION("+"), SUBTRACTION("-"), MULTIPLICATION("*"), DIVISION("/");
        final String operator;

        Operation(String operator) {
            this.operator = operator;
        }

        public String getOperation() {
            return operator;
        }

        public static Operation getByOperators(String oper) {
            for (Operation operation : values()) {
                if (operation.getOperation().equals(oper)) {
                    return operation;
                }
            }
            throw new IllegalArgumentException("No enum");
        }
    }

    private static final String regexGlobalPattern = "\\b([1-9]|10)([+-/*])([1-9]|10)\\b";
    private static final Pattern numbersPattern = Pattern.compile("(\\d+)(\\D+)(\\d+)");
    private static final String regexNumber = "[1-9]|10";
    private static final String regexOperator = "[+-/*]";
    private int first;
    private int second;
    Operation operation;

    public static void main(String[] args) {

        new Main().calc(front());
    }

    private static String front() {

        System.out.println("Input");

        String str;
        try (Scanner scanner = new Scanner(System.in)) {
            str = scanner.nextLine();
        }
        return str;
    }

    private void validator(String input) {

        Matcher matcher = numbersPattern.matcher(input);

        String numFirst = "";
        String numSecond = "";
        String operator = "";

        while (matcher.find()) {
            numFirst = matcher.group(1);
            numSecond = matcher.group(3);
            operator = matcher.group(2);
        }

        if (!(numFirst.matches(regexNumber) && numSecond.matches(regexNumber))) {
            System.out.println("Output:");
            throw new PatternSyntaxException("Ошибка в номере", regexNumber, -1);
        }
        if (!operator.matches(regexOperator)) {
            System.out.println("Output:");
            throw new PatternSyntaxException("Ошибка в операторе", regexOperator, -1);
        }
        if (!input.matches(regexGlobalPattern)) {
            System.out.println("Output:");
            throw new IllegalArgumentException("Ошибка в строке");
        }

        first = Integer.parseInt(numFirst);
        second = Integer.parseInt(numSecond);
        operation = Operation.getByOperators(operator);
    }


    private void calc(String input) {

        validator(input);

        int result = 0;
        switch (operation) {
            case ADDITION -> result = first + second;
            case SUBTRACTION -> result = first - second;
            case MULTIPLICATION -> result = first * second;
            case DIVISION -> result = first / second;
        }
        System.out.println("Output: \n" + result);
    }
}