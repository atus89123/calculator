package com.example.totha.mycalculator;

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

        /*for(int i = 0; i < 8; ++i){
            Button calcButton = new Button(this);
            calcButton.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            calcButton.setText( i + "");
            buttonGridLayout.addView(calcButton);
            switch(i){

            }
        }*/
    }


    @Override
    public void onClick(View v) {
        String prevResult = (String) resultTextView.getText().toString();

        Button button =(Button)v;
        String buttonText = button.getText().toString();

        switch(buttonText){
            case "=":  /*reversePolishForm(prevResult);*/ break;
            case "+":
                if()
                prevNum = Integer.parseInt(prevResult);
                operator = '+';
            case "-":
            case "*":
            case "/":
            default: resultTextView.setText(prevResult + buttonText); break;
        }



        //resultTextView.setText(prevResult + buttonText);

    }

    public void reversePolishForm(String result) {

        while (result.hasMoreElements()) {
            str.nextElement();
            stack.push((Double) str.nextElement());

            for (int i = 0; i < x.length(); i++) {

                switch (x.charAt(i)) {
                    case '+':
                        number1 = stack.pop();
                        number2 = stack.pop();
                        System.out.println(number1 + number2);
                        break;
                    case '-':
                        number1 = stack.pop();
                        number2 = stack.pop();
                        System.out.println(number1 - number2);
                        break;
                    case '/':
                        number1 = stack.pop();
                        number2 = stack.pop();
                        System.out.println(number1 / number2);
                        break;
                    case '*':
                        number1 = stack.pop();
                        number2 = stack.pop();
                        System.out.println(number1 * number2);
                        break;
                }

            }
            System.out.println(stack.pop());
        }
    }
}

