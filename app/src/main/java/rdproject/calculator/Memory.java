package rdproject.calculator;

import android.widget.TextView;

public class Memory {
    private String mem = null;
    private final TextView mainWindow;
    private final MainActivity.ModelView modelView;
    private final TextView memoryView;
    private static final NumberFormatter formatter = NumberFormatter.getFormatter();

    public Memory(MainActivity.ModelView modelView, TextView mainWindow, TextView memoryView) {
        this.mainWindow = mainWindow;
        this.modelView = modelView;
        this.memoryView = memoryView;
    }

    public String getMem() {
        return mem;
    }

    public void plus() {
        mem = mem == null ? "0" : formatter.normalizeNumber(mem);
            CalcHelper calcHelper = new CalcHelper();
            mem = calcHelper.calculate(mem, GettingLastStringOfView(), "+");
        memoryView.setText(formatter.formatNumber(mem));
    }

    public void minus() {
        mem = mem == null ? "0" : formatter.normalizeNumber(mem);
        CalcHelper calcHelper = new CalcHelper();
        mem = calcHelper.calculate(mem, GettingLastStringOfView(), "-");
        memoryView.setText(formatter.formatNumber(mem));
    }

    public void recall() {
        if (mem == null) return;
        try {
            if ( !modelView.isReadyToNum2 && !modelView.justResulted) {
                modelView.reNewView( "\n" + mem );
                return;
            }
            modelView.reNewView(mem);
        } catch (Exception ignored) {
        }
    }
    public void memoryClear() {
        memoryView.setText("");
        mem = null;
    }

    public String GettingLastStringOfView() {
        String text = "";
        try {
            text = mainWindow.getText().toString();

        } catch (Exception ignored) {
        }
        if (text.isEmpty() || text.endsWith("\n") || modelView.isOperationActive()) {
            return "0";
        }
        if (text.contains("\n")) {
            text = text.substring(text.lastIndexOf("\n") + 1); //getting last string of view
        }
        text = formatter.normalizeNumber(text);
        return text;
    }
}
