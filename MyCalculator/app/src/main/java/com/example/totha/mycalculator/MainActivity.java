package com.example.totha.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;


//TO_DO
/*

- AUTOMATIC SRCOLL DOWN

- GRIDLAYOUT SIZE ELEMNTS

 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTextView;
    Button oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton, sevenButton, eightButton, nineButton, zeroButton, addButton, subButton, mulButton, divButton, clearButton, equalButton;

    double prevNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        GridLayout buttonGridLayout = (GridLayout) findViewById(R.id.buttonGridLayout);


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

        resultTextView.setMovementMethod(new ScrollingMovementMethod());

    }


    @Override
    public void onClick(View v) {
        String prevResult = (String) resultTextView.getText().toString();

        Button button =(Button)v;
        String buttonText = button.getText().toString();

        switch(buttonText){
            case "=":  /*reversePolishForm(prevResult);*/ break;
            default: resultTextView.setText(prevResult + buttonText); break;
        }



        //resultTextView.setText(prevResult + buttonText);

    }

    public double reversePolishForm(ArrayList<String> components) {

        Stack<Double> stack = new Stack<Double>();;
        double num1;
        double num2;

        for(String comp : components){
            if(comp.equals("+")) {
                num2 = stack.pop();
                num1 = stack.pop();
                stack.push(num1 + num2);
            }
            else if(comp.equals("-")) {
                num2 = stack.pop();
                num1 = stack.pop();
                stack.push(num1 - num2);
            }
            else if(comp.equals("*")) {
                num2 = stack.pop();
                num1 = stack.pop();
                stack.push(num1 * num2);
            }
            else if(comp.equals("/")){
                num2 = stack.pop();
                num1 = stack.pop();
                stack.push(num1 / num2);
            }
            else {
                stack.push(Double.parseDouble(comp));
            }
        }
        return stack.pop();
    }


    public ArrayList<String> polishForm(String equation){
        int i = 0;
        ArrayList<String> polishF = new ArrayList<String>();
        Stack<Character> operator = new Stack<Character>();
        String number = "";
        Character c = ' ';
        while(i < equation.length()){
            char character = equation.charAt(i);
            switch(character){
                case '+':
                    polishF.add(number);
                    number = "";
                    if(!operator.isEmpty()){
                        c = operator.pop();
                        if(c == '+' || c == '-'){
                            polishF.add(c.toString());
                            operator.push(character);
                        } else{
                            polishF.add(c.toString());
                            if(operator.isEmpty()){
                                operator.push(character);
                            }else{
                                c = operator.pop();
                                polishF.add(c.toString());
                                operator.push(character);
                            }
                        }
                    }else{
                        operator.push(character);
                    }

                    break;
                case '-':
                    polishF.add(number);
                    number = "";
                    if(!operator.isEmpty()){
                        c = operator.pop();
                        if(c == '+' || c == '-'){
                            polishF.add(c.toString());
                            operator.push(character);
                        } else{
                            polishF.add(c.toString());
                            if(operator.isEmpty()){
                                operator.push(character);
                            } else{
                                c = operator.pop();
                                polishF.add(c.toString());
                                operator.push(character);
                            }
                        }
                    } else{
                        operator.push(character);
                    }
                    break;
                case '*':
                    polishF.add(number);
                    number = "";
                    if(!operator.isEmpty()){
                        c = operator.pop();
                        if(c == '+' || c == '-'){
                            operator.push(c);
                            operator.push(character);
                        } else{
                            polishF.add(c.toString());
                            operator.push(character);
                        }
                    } else{
                        operator.push(character);
                    }
                    break;
                case '/':
                    polishF.add(number);
                    number = "";
                    if(!operator.isEmpty()){
                        c = operator.pop();
                        if(c == '+' || c == '-'){
                            operator.push(c);
                            operator.push(character);
                        } else{
                            polishF.add(c.toString());
                            operator.push(character);
                        }
                    } else{
                        operator.push(character);
                    }
                    break;
                default:
                    number += character;
                    break;
            }
            ++i;
        }
        if(!number.equals("")){
            polishF.add(number);
        }
        while(!operator.isEmpty()){
            c = operator.pop();
           // System.out.println(c);
            polishF.add(c.toString());
        }
        return polishF;
    }
}