package co.danielgarcia.calculadora;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    EditText editText;

    ArrayList<Integer> numberButtons = new ArrayList<Integer>();
    ArrayList<Integer> operatorButtons  = new ArrayList<Integer>();
    Button buttonDecimal;
    Button buttonEquals;
    Button buttonClear;

    Float tempOperand;
    String operator;
    int lastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        editText.setKeyListener(null); //non editable

        numberButtons.add(R.id.button1);
        numberButtons.add(R.id.button2);
        numberButtons.add(R.id.button3);
        numberButtons.add(R.id.button4);
        numberButtons.add(R.id.button5);
        numberButtons.add(R.id.button6);
        numberButtons.add(R.id.button7);
        numberButtons.add(R.id.button8);
        numberButtons.add(R.id.button9);
        numberButtons.add(R.id.button0);

        operatorButtons.add(R.id.buttonAdd);
        operatorButtons.add(R.id.buttonSubtract);
        operatorButtons.add(R.id.buttonMultiply);
        operatorButtons.add(R.id.buttonDivide);

        buttonDecimal = (Button) findViewById(R.id.buttonDecimal);
        buttonEquals = (Button) findViewById(R.id.buttonEquals);
        buttonClear = (Button) findViewById(R.id.buttonClear);

        for (int i = 0; i < numberButtons.size(); i++) {
            findViewById(numberButtons.get(i)).setOnClickListener(this);
        }

        for (int i = 0; i < operatorButtons.size(); i++) {
            findViewById(operatorButtons.get(i)).setOnClickListener(this);
        }

        buttonDecimal.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        int buttonId = button.getId();
        String buttonText = button.getText().toString();
        String currentText = editText.getText().toString();


        if (numberButtons.contains(buttonId)){
            if (currentText.equals("0") || operatorButtons.contains(lastButton)){
                editText.setText(buttonText);
            }
            else {
                editText.append(buttonText);
            }
        }
        else if (operatorButtons.contains(buttonId)){
            if (operator != null && tempOperand != null && numberButtons.contains(lastButton)) {
                calculate();
            }
            tempOperand = Float.parseFloat(currentText);
            operator = buttonText;

        }
        else if (button == buttonDecimal) {
            if (operatorButtons.contains(lastButton)) {
                editText.setText("0".concat(getString(R.string.decimal_button)));
            }
            else if (!currentText.contains(getString(R.string.decimal_button))){
                editText.append(buttonText);
            }
        }
        else if (button == buttonEquals) {
            if (operator != null && tempOperand != null && numberButtons.contains(lastButton)) {
                calculate();
            }
        }
        else if (button == buttonClear) {
            tempOperand = null;
            operator = null;
            editText.setText("0");
        }

        lastButton = buttonId;
    }

    public void calculate() {
        Float operand = Float.parseFloat(editText.getText().toString());
        if (operator.equals(getString(R.string.add_button))) {
            tempOperand = tempOperand + operand;
        }
        else if (operator.equals(getString(R.string.subtract_button))) {
            tempOperand = tempOperand - operand;
        }
        else if (operator.equals(getString(R.string.multiply_button))) {
            tempOperand = tempOperand * operand;
        }
        else if (operator.equals(getString(R.string.divide_button))) {
            tempOperand = tempOperand / operand;
        }
        editText.setText(tempOperand.toString());
    }
}
