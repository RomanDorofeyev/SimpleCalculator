package rdproject.calculator;

import java.math.BigDecimal;

public class CalcHelper {
    private static final int MAX_PLAIN_LENGTH = 16;
    private static final String DIVISION_BY_ZERO_RESULT = "0000000000000000";

    private final Calc calc = Calc.getCalc();

    public String calculate(String currentNumber1, String currentNumber2, String currentOperation) {
        BigDecimal num1 = new BigDecimal(currentNumber1);
        BigDecimal num2 = new BigDecimal(currentNumber2);
        BigDecimal result = new BigDecimal("0");
        switch (currentOperation) {
            case "x": result = calc.multiply(num1, num2);
                break;
            case "/": if (Double.parseDouble(currentNumber2) == 0) return DIVISION_BY_ZERO_RESULT;
                result = calc.divide(num1, num2);
                break;
            case "+": result = calc.plus(num1, num2);
                break;
            case "-": result = calc.minus(num1, num2);
                break;
            case "x2": result = calc.x2(num1);
                break;

        }
        if (result.toPlainString().length() <= MAX_PLAIN_LENGTH) return result.toPlainString();
        else return result.toEngineeringString();
    }

    public String simpleOperation(String currentNumber1, String currentOperation) {
        BigDecimal num1 = new BigDecimal(currentNumber1.replaceAll(" ",""));
        BigDecimal result = new BigDecimal("0");

        switch (currentOperation) {
            case "x2": result = calc.x2(num1);
                break;
            case "sqrt": result = calc.sqrt(num1);
                break;
        }
        if (result.toPlainString().length() <= MAX_PLAIN_LENGTH) return result.toPlainString();
        else return result.toEngineeringString();
    }

    public String percOperation(String percent, String currentNumber1) {
        BigDecimal perc = calc.percent(new BigDecimal(percent));
        BigDecimal result = calc.multiply(new BigDecimal(currentNumber1), perc);
        if (result.toPlainString().length() <= MAX_PLAIN_LENGTH) return result.toPlainString();
        else return result.toEngineeringString();
    }
    public String percOperation(String percent) {
        BigDecimal perc = calc.percent(new BigDecimal(percent));
        if (perc.toPlainString().length() <= MAX_PLAIN_LENGTH) return perc.toPlainString();
        else return perc.toEngineeringString();
    }
}
