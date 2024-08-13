package rdproject.calculator;

import android.view.View;

/**
 * Created by Ro on 15.08.2017.
 */

public class ButtonListener implements View.OnClickListener, View.OnLongClickListener {
    private final MainActivity.ModelView modelView;
    private final MainActivity context;
    private final Memory m;

    public ButtonListener(MainActivity.ModelView modelView, MainActivity context) {
        this.modelView = modelView;
        this.context = context;
        m = new Memory(modelView, context.getMainWindow(), context.getMemoryView());
    }

    @Override
    public void onClick(View v) {
        modelView.setAlignmentEnd();
        context.getMainWindow().setHint("");
        switch (v.getId()) {
            case (R.id.c):
                modelView.clearView();
                break;
            case (R.id.m_plus):
                m.plus();
                break;
            case (R.id.m_minus):
                m.minus();
                break;
            case (R.id.m_r):
                m.recall();
                break;
            case (R.id.stepback):
                modelView.stepBack();
                break;
            case (R.id.button1):
                modelView.reNewView("1");
                break;
            case (R.id.button2):
                modelView.reNewView("2");
                break;
            case (R.id.button3):
                modelView.reNewView("3");
                break;
            case (R.id.button4):
                modelView.reNewView("4");
                break;
            case (R.id.button5):
                modelView.reNewView("5");
                break;
            case (R.id.button6):
                modelView.reNewView("6");
                break;
            case (R.id.button7):
                modelView.reNewView("7");
                break;
            case (R.id.button8):
                modelView.reNewView("8");
                break;
            case (R.id.button9):
                modelView.reNewView("9");
                break;
            case (R.id.button0):
                modelView.reNewView("0");
                break;
            case (R.id.button_point):
                modelView.reNewView(".");
                break;
            case (R.id.button_percent):
                modelView.percent();
                break;
            case (R.id.devide):
                modelView.initOperation("/");
                break;
            case (R.id.produce):
                modelView.initOperation("x");
                break;
            case (R.id.minus):
                modelView.initOperation("-");
                break;
            case (R.id.plus):
                modelView.initOperation("+");
                break;
            case (R.id.plus_minus):
                modelView.plusMinus();
                break;
            case (R.id.x2):
                modelView.simple("x2");
                break;
            case (R.id.sqrt):
                modelView.simple("sqrt");
                break;
            case (R.id.calculate):
                modelView.produceResult();

        }

    }

    @Override
    public boolean onLongClick(View v) {
        if (m.getMem() != null) {
            m.memoryClear();
            context.showToast("Memory successfully cleared");
            return true;
        }
        context.showToast("Memory is empty");
        return true;
    }
}
