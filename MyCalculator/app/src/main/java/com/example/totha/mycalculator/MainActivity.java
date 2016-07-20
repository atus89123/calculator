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

import java.util.ArrayList;
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

        }



        //resultTextView.setText(prevResult + buttonText);

    }



    public ArrayList<String> polishForm(String s){
        int i = 0;
        ArrayList<String> polishF = new ArrayList<String>();
        Stack<Character> operator = new Stack<Character>();
        String number = "";
        while(i < s.length()){
            Character character = s.charAt(i);
            if(character == '+' || character == '-'){
                polishF.add(number);
                number = "";
                if(operator.isEmpty()) operator.push(character);
                else{
                    while(!operator.isEmpty()){
                        Character stackCharacter = operator.pop();
                        polishF.add(stackCharacter.toString());
                    }
                    operator.push(character);
                }
            } else if(character == '*' || character == '/'){
                polishF.add(number);
                number = "";
                if(operator.isEmpty()) operator.push(character);
                else{
                    while(!operator.isEmpty() && (operator.peek() != '+' && operator.peek() != '-')){
                        Character stackCharacter = operator.pop();
                        polishF.add(stackCharacter.toString());
                    }
                    operator.push(character);
                }
            } else{
                number += character;
            }
            ++i;
        }
        if(!number.equals("")) polishF.add(number);
        while(!operator.isEmpty()){
            Character stackCharacter = operator.pop();
            polishF.add(stackCharacter.toString());
        }
        System.out.print(polishF.toString());
        return polishF;
    }
}