package com.example.totha.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    /**
     * Returns an ArrayList object. The equationString argument
     * is the equation in the normal form. The polishForm method shape the
     * equation in polish form and save the arguments (in String form) in ArrayList
     * @param equationString is the equation in normal form
     * @return an ArrayList
     */
    public ArrayList<String> polishForm(String equationString){
        int i = 0;
        ArrayList<String> polishF = new ArrayList<String>();
        Stack<Character> operators = new Stack<Character>();
        String number = "";
        while(i < equationString.length()){
            Character character = equationString.charAt(i);
            if(character == '+' || character == '-'){
                polishF.add(number);
                number = "";
                if(operators.isEmpty()) operators.push(character);
                else{
                    while(!operators.isEmpty()){
                        Character stackCharacter = operators.pop();
                        polishF.add(stackCharacter.toString());
                    }
                    operators.push(character);
                }
            } else if(character == '*' || character == '/'){
                polishF.add(number);
                number = "";
                if(operators.isEmpty()) operators.push(character);
                else{
                    while(!operators.isEmpty() && (operators.peek() != '+' && operators.peek() != '-')){
                        Character stackCharacter = operators.pop();
                        polishF.add(stackCharacter.toString());
                    }
                    operators.push(character);
                }
            } else{
                number += character;
            }
            ++i;
        }
        if(!number.equals("")) polishF.add(number);
        while(!operators.isEmpty()){
            Character stackCharacter = operators.pop();
            polishF.add(stackCharacter.toString());
        }
        return polishF;
    }

    /**
     * This method run, when click the Save Result button. This save the TextView text parameters
     * in Map. The map's key is a Date and the value is a Double. The method first split the String and get the actual date
     * then put the date and the result in a Map.
     *
     * @param v
     */
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