package com.example.totha.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


/*          TO_DO           */
/*  - AUTOMATIC SRCOLL DOWN
    - GRIDLAYOUT SIZE ELEMNTS   */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTextView;
    StringBuilder rawStringBuilder;
    Map<String, Double> resultsMap;
    Button saveResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        GridLayout buttonGridLayout = (GridLayout) findViewById(R.id.buttonGridLayout);
        resultsMap = new HashMap<String, Double>();
        resultTextView.setMovementMethod(new ScrollingMovementMethod());

        rawStringBuilder = new StringBuilder();
        resultTextView.setText("0");
        saveResult = (Button)findViewById(R.id.saveResult);
        saveResult.setEnabled(false);
    }


    @Override
    public void onClick(View v) {

        Button button =(Button)v;
        String buttonText = button.getText().toString();

        switch(buttonText){

            case "=": if(rawStringBuilder.toString().equals("")){ // operator után = javítani!!
                        break;
                    }
                    double res = reversePolishForm(polishForm(rawStringBuilder.toString()));
                    resultTextView.setText(rawStringBuilder.toString() + " = " + res);
                    saveResult.setEnabled(true);
                    rawStringBuilder.setLength(0);
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
                saveResult.setEnabled(false);
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

    public double reversePolishForm(ArrayList<String> components) {
        //if(components.isEmpty()) return 0;
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

    public void saveResult(View v){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd. H:mm:ss");
        String result = resultTextView.getText().toString();
        String[] splitResult = result.split("=");
        Double doubleResult = Double.parseDouble(splitResult[1]);
        resultsMap.put(dateFormat.format(date), doubleResult);
        System.out.println(resultsMap.size());
    }
}