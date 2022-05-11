package org.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

class Calculator {
    private final int[] counts = {0, 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private final String[] values = {"", "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
    HashMap<String, Integer> nums = new HashMap<>();

    public Calculator() {
        for (int i = 0; i < this.counts.length; i++) {
            nums.put(values[i], counts[i]);
        }
    }

    /**
     * Main method for calculate to numbers
     *
     * @param question is a String with numbers for calculate
     * @return result of calculating
     */
    public String work(String question) throws Exception {
        String result;
        question = this.editMessage(question);
        String[] expression = question.split(" ");
        boolean isArabic = true;
        if (expression.length != 3) {
            throw new Exception("The expression is incorrect");
        }
        if (Pattern.matches("[IVXLCDMivxlcdm \\Q+-/*\\E]+", question)) {
            isArabic = false;
            this.changeToArabic(expression);
        }
        if (this.checkArray(expression)) {
            result = this.doWork(expression);
            if (!isArabic) {
                result = this.changeToRoman(result);
            }
        } else {
            throw new Exception("The expression is incorrect");
        }
        return result;
    }

    /**
     * Unnecessary method (hasn't been in technical specification) to edit message, if it has too many or too little number of whitespaces
     *
     * @param question is a message from user
     * @return prepared message
     */
    private String editMessage(String question) {
        String[] s = question.split("");
        ArrayList<String> list = new ArrayList<>(Arrays.asList(s));
        for (int i = 0; i <= list.size() - 1; i++) {
            if (list.get(i).equals(" ")) {
                list.remove(i);
                i--;
            }
        }
        for (int i = 0; i < list.size() - 1; i++) {
            if (Pattern.matches("[\\Q+-*/\\E]{1}", list.get(i))) {
                this.addWhiteSpace(list, i);
                i++;
                this.addWhiteSpace(list, i + 1);
            }
        }
        return putTogether(list);
    }

    /**
     * Method for pick together items from ArrayList to String
     *
     * @param list for prepare
     * @return prepared String
     */
    private String putTogether(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * Method for check and put (if it is necessary) the whitespace
     *
     * @param list     is a list of values
     * @param answered is number of position for check
     */
    private void addWhiteSpace(ArrayList<String> list, int answered) {
        if (!list.get(answered).equals(" ")) {
            list.add(answered, " ");
        }
    }

    /**
     * Method for change number to a roman style
     *
     * @param result is a String with the arabic number
     * @return prepared roman number
     */
    private String changeToRoman(String result) throws Exception {
        if (Integer.parseInt(result) <= 0) {
            throw new Exception("Roman system hasn't negative numbers");
        }
        StringBuilder semiresult = new StringBuilder();
        int count = Integer.parseInt(result);
        for (int i = counts.length - 1; i > 0; i--) {
            while (count >= counts[i]) {
                semiresult.append(values[i]);
                count -= counts[i];
            }
        }
        return semiresult.toString();
    }

    /**
     * Main method of calculating
     *
     * @param expression is a 3-parted string with two numbers and one expression
     * @return result of calculating
     */
    /**
     * Main method of calculating
     *
     * @param expression is a 3-parted string with two numbers and one expression
     * @return result of calculating
     */
    private String doWork(String[] expression) throws Exception {
        BigInteger first = new BigInteger(expression[0]);
        BigInteger second = new BigInteger(expression[2]);
        BigInteger result;
        switch (expression[1]) {
            case "+":
                result = first.add(second);
                break;
            case"-":
                result = first.subtract(second);
                break;
            case "/":
                result = first.divide(second);
                break;
            case "*":
                result = first.multiply(second);
                break;
            default:
                throw new Exception("Count exception");
        }
// BigInteger result = switch (expression[1]) {
// case "+" -> first.add(second);
// case "-" -> first.subtract(second);
// case "/" -> first.divide(second);
// case "*" -> first.multiply(second);
// default -> throw new Exception("Count exception");
// };
        return result.toString();
    }

    /**
     * Method for check that it is a really array for work
     *
     * @param expression is an array with values
     * @return is this arrayu is correct
     */
    private boolean checkArray(String[] expression) {
        return Pattern.matches("[\\d]+", expression[0]) && Pattern.matches("[*/+-]{1}", expression[1]) && Pattern.matches("[\\d]+", expression[2]);
    }

    /**
     * Method for change to arabic numbers special parts of array
     *
     * @param expression is an array to change
     */
    private void changeToArabic(String[] expression) {
        expression[0] = String.valueOf(this.changeToArabic(expression[0]));
        expression[2] = String.valueOf(this.changeToArabic(expression[2]));
    }

    /**
     * Is a roman numbers changer
     *
     * @param number is a number for change
     * @return changed number
     */
    private int changeToArabic(String number) {
        number = number.toUpperCase();
        String[] values = number.split("");
        boolean needLast = true;
        int result = 0;
        for (int i = 0; i < values.length - 1; i++) {
            String val = values[i] + values[i + 1];
            if (nums.containsKey(val)) {
                result += nums.get(val);
                i++;
                needLast = false;
            } else {
                result += nums.get(values[i]);
                needLast = true;
            }
        }
        if (needLast) {
            result += nums.get(values[values.length - 1]);
        }
        return result;
    }
}
