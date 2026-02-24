package rdproject.calculator;

import java.math.BigDecimal;

public class Calc {
    private static final int DECIMAL_SCALE = 10;
    private static final int PERCENT_DIVISOR = 100;

    private static final Calc calc = new Calc();

    public static Calc getCalc() {
        return calc;
    }

    private Calc() { }

    public BigDecimal plus(BigDecimal a, BigDecimal b) {

        return a.add(b).setScale(DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal minus(BigDecimal a, BigDecimal b) {
        return a.subtract(b).setScale(DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b).setScale(DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        return a.divide(b, DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal x2 (BigDecimal a) {
        return a.multiply(a).setScale(DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
    public BigDecimal percent (BigDecimal a) {
        return a.divide(new BigDecimal(PERCENT_DIVISOR), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }

    public BigDecimal sqrt(BigDecimal num1) {
        double sqrt = num1.doubleValue();
        sqrt = Math.sqrt(sqrt);
        return new BigDecimal(sqrt).setScale(DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
    }
}
