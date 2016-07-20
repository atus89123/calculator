package com.example.totha.mycalculator;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTextView;
    Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton, addButton, subButton, mulButton, divButton, clearButton, equalButton;
    //int op1;
    //int op2;
    //String operator;

    Stack<Double> stack;
    double number1;
    double number2;

    double prevNum;
    Character operator;

    StringBuilder rawStringBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        GridLayout buttonGridLayout = (GridLayout) findViewById(R.id.buttonGridLayout);
        stack = new Stack<Double>();

        oneButton = (Button) findViewById(R.id.one);
        twoButton = (Button) findViewById(R.id.two);
        threeButton = (Button) findViewById(R.id.three);
        fourButton = (Button) findViewById(R.id.four);
        fiveButton = (Button) findViewById(R.id.five);
        sixButton = (Button) findViewById(R.id.six);
        sevenButton = (Button) findViewById(R.id.seven);
        eightButton = (Button) findViewById(R.id.eight);
        nineButton = (Button) findViewById(R.id.nine);
        zeroButton = (Button) findViewById(R.id.zero);

        rawStringBuilder = new StringBuilder();
        resultTextView.setText("0");

    }


    @Override
    public void onClick(View v) {

        Button button =(Button)v;
        String buttonText = button.getText().toString();

        switch(buttonText){
            case "=":
                //reversePolishForm(null);
                break;
            case "+": case "-": case "*": case "/":
                validateOperator(rawStringBuilder, buttonText);
                break;
            case ".":
                String[] tmp = rawStringBuilder.toString().split("[^\\d\\.\\d]");
                if(tmp.length != 0 && !tmp[tmp.length-1].contains(".")){
                    validateOperator(rawStringBuilder, buttonText);
                }
                break;
            default:
                if(buttonText.equals("Clear")){
                    rawStringBuilder.delete(0, rawStringBuilder.length());
                    resultTextView.setText("0");}
                else {
                    rawStringBuilder.append(buttonText);
                    resultTextView.setText(rawStringBuilder.toString());
                }
                break;
        }
    }

    private void validateOperator(StringBuilder rawStringBuilder, String buttonText) {
        if (rawStringBuilder.length() != 0) {
            char lastChar = rawStringBuilder.charAt(rawStringBuilder.length() - 1);
            if ((lastChar == '+') || (lastChar == '-') || (lastChar == '*') ||
                    (lastChar == '/') || lastChar == '.') {
                rawStringBuilder.deleteCharAt(rawStringBuilder.length() - 1);
            }
            rawStringBuilder.append(buttonText);
            resultTextView.setText(rawStringBuilder.toString());
        }
    }
}

