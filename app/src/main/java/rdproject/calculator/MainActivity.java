package rdproject.calculator;

import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView mainWindow;
    private TextView memoryView;
    private Button clear, mPlus, mMinus, mRecall, backSpace, one, two, three, four, five, six, seven, eight,
            nine, zero, point, percent, divide, multiply, minus, plus, plusMinus, x2, sqrt, ravno;
    private Toast toast;
    private static final NumberFormatter formatter = NumberFormatter.getFormatter();
    private static long back_pressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.background_main));
            getWindow().getDecorView().setSystemUiVisibility(getWindow().getDecorView().getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Objects.requireNonNull(getSupportActionBar()).hide();

        mainWindow = findViewById(R.id.textView);
        mainWindow.setMovementMethod(new ScrollingMovementMethod());
        mainWindow.setText("");
        mainWindow.setHint("Version " + BuildConfig.VERSION_NAME);
        memoryView = findViewById(R.id.memoryView);
        createAllButtons();
        ButtonListener listener = new ButtonListener(new ModelView(), this);
        List<Button> buttonList = initButtonList();
        for (Button button : buttonList) {
            button.setOnClickListener(listener);
        }
        clear.setOnLongClickListener(listener);
    }

    private void createAllButtons() {
        clear = findViewById(R.id.c);
        mPlus = findViewById(R.id.m_plus);
        mMinus = findViewById(R.id.m_minus);
        mRecall = findViewById(R.id.m_r);
        backSpace = findViewById(R.id.stepback);
        one = findViewById(R.id.button1);
        two = findViewById(R.id.button2);
        three = findViewById(R.id.button3);
        four = findViewById(R.id.button4);
        five = findViewById(R.id.button5);
        six = findViewById(R.id.button6);
        seven = findViewById(R.id.button7);
        eight = findViewById(R.id.button8);
        nine = findViewById(R.id.button9);
        zero = findViewById(R.id.button0);
        point = findViewById(R.id.button_point);
        percent = findViewById(R.id.button_percent);
        divide = findViewById(R.id.devide);
        multiply = findViewById(R.id.produce);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        plusMinus = findViewById(R.id.plus_minus);
        x2 = findViewById(R.id.x2);
        sqrt = findViewById(R.id.sqrt);
        ravno = findViewById(R.id.calculate);
    }

    private List<Button> initButtonList() {
        Button[] buttons = new Button[]{clear, mPlus, mMinus, mRecall, backSpace,
                one, two, three, four, five, six, seven, eight,
                nine, zero, point, percent, divide, multiply, minus, plus, plusMinus, x2, sqrt, ravno};
        return Arrays.asList(buttons);
    }

    public void showToast(String text) {
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            showToast(getString(R.string.exitApp));
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    protected void onStop() {
        if (toast != null) toast.cancel();
        super.onStop();
    }

    public TextView getMainWindow() {
        return mainWindow;
    }

    public TextView getMemoryView() {
        return memoryView;
    }


    public class ModelView {
        private final CalcHelper calcHelper = new CalcHelper();
        private String currentOperation = null;
        private String lastOperation = null;
        private String currentNumber1 = null;
        private String currentNumber2 = null;
        protected boolean isReadyToNum2 = false;
        protected boolean justResulted = false;

        public void setAlignmentEnd() {
            mainWindow.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            mainWindow.setPadding(5, 5, 50, 5);
        }

        public void clearView() {
            mainWindow.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            mainWindow.setText("");
            mainWindow.setHint("");
            isReadyToNum2 = false;
            currentNumber1 = null;
            currentNumber2 = null;
            lastOperation = null;
            currentOperation = null;
            justResulted = false;
        }

        public void stepBack() {
            if (isOperationActive()) return;
            if (justResulted) return;
            try {
                String text = mainWindow.getText().toString();
                if (!text.endsWith("\n")) {
                    text = text.substring(0, text.length() - 1);
                }
                mainWindow.setText(text);
            } catch (Exception ignored) {
            }
            if (mainWindow.getText().toString().isEmpty()) {
                clearView();
            }
        }

        public String clearLastNum() {
            String text;
            String lastNum;
            text = mainWindow.getText().toString();
            lastNum = text.substring(text.lastIndexOf("\n") + 1);
            try {
                text = text.substring(0, text.lastIndexOf("\n"));
            } catch (Exception e) {
                text = "";
            }
            mainWindow.setText(text);
            justResulted = true;
            return lastNum;

        }

        public void reNewView(String s) {
            if (s == null || s.isEmpty()) return;
            String text = mainWindow.getText().toString();
            if (text.substring(text.lastIndexOf("\n") + 1).contains(".") && s.equals(".")) return;
            if (justResulted && !isReadyToNum2) {
                text += "\n";
                justResulted = false;
            }
            if (isReadyToNum2) {
                text += "\n";
                isReadyToNum2 = false;
            }
            text += s;
            mainWindow.setText(text);
        }

        public boolean isOperationActive() {
            boolean b = false;
            try {
                b = mainWindow.getText() != null && (mainWindow.getText().toString().endsWith("/") ||
                        mainWindow.getText().toString().endsWith("x") ||
                        mainWindow.getText().toString().endsWith("+") ||
                        mainWindow.getText().toString().endsWith("-"));
            } catch (Exception e) {
            }
            return b;
        }

        public void initOperation(String s) {
            String text = "";
            try {
                text = mainWindow.getText().toString();
            } catch (Exception ignored) {
            }

            if (text.isEmpty()) return;
            if (text.endsWith("\n")) return;

            if (isOperationActive()) {
                currentOperation = s;
                isReadyToNum2 = false;

                if (!text.endsWith("\n")) {
                    text = text.substring(0, text.length() - 1);
                }
                mainWindow.setText(text);

                reNewView(s);
                isReadyToNum2 = true;
                return;
            }
            if (currentOperation != null) {
                produceResult();
                currentOperation = s;
                reNewView(s);
                isReadyToNum2 = true;
                return;
            }
            if (!isReadyToNum2) {         // main part of initializing operation
                currentOperation = s;
                currentNumber1 = mainWindow.getText().toString();
                currentNumber1 = currentNumber1.substring(currentNumber1.lastIndexOf("\n") + 1); //getting last string of view
                currentNumber1 = formatter.normalizeNumber(currentNumber1);  // here we get the number from text view in normal mode
                if (justResulted) {
                    reNewView(s);
                } else {
                    reNewView("\n" + s);
                }
                isReadyToNum2 = true;
            }
        }

        public void produceResult() {
            if (isOperationActive()) return;
            if (mainWindow.getText().toString().endsWith("\n")) return;
            if (justResulted) {
                justResulted = false;
                currentOperation = lastOperation;
                String result = "";
                String formattedResult = "";
                try {
                    currentNumber1 = formatter.normalizeNumber(currentNumber1);  // here we get the number from text view in normal mode before calculating ????????????
                    result = calcHelper.calculate(currentNumber1, currentNumber2, currentOperation);
                    formattedResult = formatter.formatNumber(result);  // testing............................................................
                } catch (Exception ignored) {
                }
                currentOperation = null;
                currentNumber1 = result;
                reNewView("\n" + "=" + "\n" + formattedResult);
                isReadyToNum2 = false;
                justResulted = true;
                return;
            }
            if (mainWindow.getText() == null || currentNumber1 == null || currentOperation == null)
                return;

            currentNumber2 = mainWindow.getText().toString();
            currentNumber2 = currentNumber2.substring(currentNumber2.lastIndexOf("\n") + 1); //getting last string of view
            currentNumber2 = formatter.normalizeNumber(currentNumber2);       // testing.......................................................................
            String result = "";
            String formattedResult = "";
            try {
                currentNumber1 = formatter.normalizeNumber(currentNumber1);  // here we get the number from text view in normal mode before calculating  ?????????????????
                result = calcHelper.calculate(currentNumber1, currentNumber2, currentOperation);
                formattedResult = formatter.formatNumber(result);  // testing............................................................
            } catch (Exception ignored) {
            }
            lastOperation = currentOperation;
            currentOperation = null;
            currentNumber1 = result;
            reNewView("\n" + "=" + "\n" + formattedResult);
            isReadyToNum2 = false;
            justResulted = true;
        }

        public void simple(String operation) {
            String text = "";
            try {
                text = mainWindow.getText().toString();
            } catch (Exception ignored) {
            }

            if (text.isEmpty()) return;
            if (text.endsWith("\n")) return;
            if (isOperationActive()) return;

            text = clearLastNum();
            switch (operation) {
                case "x2":
                    try {
                        text = calcHelper.simpleOperation(text, "x2");
                    } catch (Exception ignored) {
                    }
                    break;
                case "sqrt":
                    try {
                        text = calcHelper.simpleOperation(text, "sqrt");
                    } catch (Exception ignored) {
                    }
                    break;
            }
            reNewView(text);
        }

        public void percent() {
            String text = "";
            String startText = "";
            try {
                text = mainWindow.getText().toString();
            } catch (Exception ignored) {
            }

            if (text.isEmpty()) return;
            if (text.endsWith("\n")) return;
            if (isOperationActive()) return;
            if (justResulted) return;

//working code for /100
            if (text.contains("\n")) {
                startText = text.substring(0, text.lastIndexOf("\n") + 1);
                text = text.substring(text.lastIndexOf("\n") + 1); //getting last string of view
            }
            if (currentNumber1 != null) {
                text = calcHelper.percOperation(text, currentNumber1);
            } else {
                text = calcHelper.percOperation(text);
            }
            text = startText + text;
            mainWindow.setText(text);
        }
// it does work (but need to be tested with Engineering numbers)

        public void plusMinus() {
            if (isOperationActive() || mainWindow.getText().toString().isEmpty()) return;
            try {
                String text = mainWindow.getText().toString();
                if (text.endsWith("\n")) return;
                if (!text.contains("\n")) {
                    text = text.startsWith("-") ? text.substring(1) : "-" + text;
                } else {
                    String currentNumber = text.substring(text.lastIndexOf("\n") + 1);
                    currentNumber = currentNumber.startsWith("-") ? currentNumber.substring(1) : "-" + currentNumber;
                    text = text.substring(0, text.lastIndexOf("\n") + 1);
                    text = text + currentNumber;
                }
                mainWindow.setText(text);

            } catch (Exception ignored) {
            }
        }
    }
}
