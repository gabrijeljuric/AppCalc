package com.gjuric.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private String display = "";
    private String doOperation = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //intialize widgets
        tvDisplay = (TextView) findViewById(R.id.tvDisplay);
        tvDisplay.setText(display);
    }

    //update screen
    private void updateScreen(){
        tvDisplay.setText(display);
    }

    //set number listener
    public void onClickNumber(View view){
        if(result != ""){
            clear();
            updateScreen();
        }
        Button button = (Button) view;
        display += button.getText();
        updateScreen();
    }

    //operation choices
    private boolean isOperator(char operator){
        switch (operator){
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
            default:
                return false;
        }
    }

    //set operation listener
    public void onClickOperator(View view){
        if(display == "")
            return;

        Button button = (Button) view;

        if(result != ""){
            String display2 = result;
            clear();
            display = display2;
        }

        if(doOperation != "") {
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), button.getText().charAt(0));
                updateScreen();
                return;
            }
            else {
                getResult();
                display = result;
                result = "";
            }
            doOperation = button.getText().toString();
        }
        display += button.getText();
        doOperation = button.getText().toString();
        updateScreen();
    }

    private void clear() {
        display = "";
        doOperation = "";
        result = "";
    }

    public void onClickClear(View view){
        clear();
        updateScreen();
    }

    private double operate(String a, String b, String operator){
        switch (operator){
            case "+":
                return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "*":
                return Double.valueOf(a) * Double.valueOf(b);
            case "/":
                try {
                return Double.valueOf(a) / Double.valueOf(b);
            }
                catch (Exception e) {
            }
            default:
                return -1;
        }
    }

    private boolean getResult(){
        if(doOperation == "")
            return false;
        String[] operation = display.split(Pattern.quote(doOperation));
        if(operation.length < 2)
            return false;
        result = String.valueOf(operate(operation[0], operation[1], doOperation));
        return true;
    }

    public void onClickEqual(View view){
        if(display == "")
            return;
        if(!getResult())
            return;

        tvDisplay.setText(display + "\n" + String.valueOf(result));
    }

}
