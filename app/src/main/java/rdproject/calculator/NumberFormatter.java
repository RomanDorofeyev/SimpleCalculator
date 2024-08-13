package rdproject.calculator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class NumberFormatter {
    private static NumberFormatter formatter = new NumberFormatter(14);

    public static NumberFormatter getFormatter() {
        return formatter;
    }

    private int requiredLength;

    public void setRequiredLength(int requiredLength) {
        this.requiredLength = requiredLength;
        formatter = new NumberFormatter(requiredLength);
    }

    private final DecimalFormat decimalFormat;

    public NumberFormatter(int requiredLength) {
        this.requiredLength = requiredLength;
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("#,###.###############", symbols);
        decimalFormat.setParseBigDecimal(true);
    }

    public boolean longerThan(String number, int digits) {
        return number.length() > digits;
    }

    public String normalizeNumber(String number) {
        if (number.contains("+")) return number;
        BigDecimal bd = new BigDecimal("0");
        try {
            bd = (BigDecimal) decimalFormat.parse(number);
        } catch (ParseException e) {
            // do nothing
        }
        return bd.toString();
    }

    // problems with negative numbers
    public String formatNumber(String number) {
        String engineeringStr;
        boolean negative = number.startsWith("-");
        if (number.contains("+")) {        // this just in case we get engineering number here
            number = new BigDecimal(number).toPlainString();
        }

        if (!longerThan(number, requiredLength)) {
            return decimalFormat.format(new BigDecimal(number));
        }
        if (number.contains(".") && number.indexOf(".") < requiredLength) {
            String string = decimalFormat.format(new BigDecimal(number));
            return string.substring(0, requiredLength);
        }
        if (!negative) {
            engineeringStr = number.charAt(0) + "." + number.substring(1, requiredLength - 5);
            int exp = number.contains(".") ? number.indexOf(".") - 1 : number.length() - 1;
            engineeringStr += "E+" + exp;
        } else {
            engineeringStr = number.substring(0, 2) + "." + number.substring(2, requiredLength - 5);
            int exp = number.contains(".") ? number.indexOf(".") - 2 : number.length() - 2;
            engineeringStr += "E+" + exp;
        }
        return engineeringStr;
    }
}
