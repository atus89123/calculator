package com.example.totha.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTextView;
    StringBuilder rawStringBuilder;
    boolean resultIsCalculated;
    /*String lastInput;
    Set<String> operatorSet;
    Stack<String> previousInputs;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = (TextView) findViewById(R.id.resultTextView);
        resultTextView.setMovementMethod(new ScrollingMovementMethod());

        rawStringBuilder = new StringBuilder();
        resultIsCalculated = false;
        /*lastInput = "";

        previousInputs = new Stack<>();

        resultTextView.setText("0");

        operatorSet = new TreeSet<>();
        operatorSet.add("+");
        operatorSet.add("-");
        operatorSet.add("*");
        operatorSet.add("/");*/
    }


    @Override
    public void onClick(View v) {

        Button button =(Button)v;
        String buttonText = button.getText().toString();
        //lastInput = buttonText;

        switch(buttonText) {

            case "=":
                String finalString = rawStringBuilder.toString();
                double res = reversePolishForm(polishForm(finalString));
                resultTextView.setText(finalString + " = " + res);
                rawStringBuilder.setLength(0);
                rawStringBuilder.append(res);
                resultIsCalculated = true;
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                resultIsCalculated = false;
                validateOperator(rawStringBuilder, buttonText);
                break;
            case ".":
                String[] tmp = rawStringBuilder.toString().split("[^\\d\\.\\d]");
                if (tmp.length != 0 && !tmp[tmp.length - 1].contains("."))
                    validateOperator(rawStringBuilder, buttonText);
                break;
            case "Clear":
                resultIsCalculated = false;
                rawStringBuilder.setLength(0);
                resultTextView.setText("0");
                break;
            default:
                if (resultIsCalculated) {
                    resultIsCalculated = false;
                    rawStringBuilder.setLength(0);
                }
                rawStringBuilder.append(buttonText);
                resultTextView.setText(rawStringBuilder.toString());
                break;
        }

        /*if(buttonText.equals("Delete"))
        {
            deleteLastCharacter();
        }
        else if(buttonText.equals("Clear"))
        {
            rawStringBuilder.setLength(0);
            lastInput = "";
            previousInputs.clear();
            resultTextView.setText("0");
        }
        else if(operatorSet.contains(buttonText) && !previousInputs.isEmpty())
        {
            if(operatorSet.contains(lastInput) || lastInput.equals(".")) deleteLastCharacter();
            showAndSaveNewInput(buttonText);
        }
        else if(buttonText.equals(".") && checkForOneDotPerExpr())
        {
            showAndSaveNewInput(buttonText);
        }
        else if(buttonText.equals("="))
        {
            String finalString = rawStringBuilder.toString();
            double result = reversePolishForm(polishForm(finalString));
            resultTextView.setText(finalString + " = " + result);
            rawStringBuilder.setLength(0);
            lastInput = "";
            previousInputs.clear();

        }
        else
        {
            showAndSaveNewInput(buttonText);
        }*/
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

    public double reversePolishForm(ArrayList<String> components) {
        if(components.isEmpty()) return 0;
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

    /*
    private void showAndSaveNewInput(String newInput){
        rawStringBuilder.append(newInput);
        previousInputs.push(lastInput);
        lastInput = newInput;
        resultTextView.setText(rawStringBuilder.toString());
    }

    private boolean checkForOneDotPerExpr()
    {
        String[] tmp = rawStringBuilder.toString().split("[^\\d\\.\\d]");
        return (tmp.length != 0 && !tmp[tmp.length-1].contains(".") && !operatorSet.contains(lastInput));
    }

    private void deleteLastCharacter()
    {
        int lengthOfRawString = rawStringBuilder.length();
        if(lengthOfRawString > 0 && !previousInputs.isEmpty()) {
            rawStringBuilder.deleteCharAt(lengthOfRawString - 1);
            previousInputs.pop();
            lastInput = previousInputs.peek();
        }
    }*/

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
        return polishF;
    }
}