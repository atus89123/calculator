package com.example.totha.mycalculator;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultTextView;
    StringBuilder rawStringBuilder;
    Map<String, Double> resultsMap;

    public final static String EXTRA_RESULT = "com.mycompany.calculator.MESSAGE";


    Button saveResult;
    boolean resultIsCalculated;
    String lastInput;
    Set<String> operatorSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        GridLayout buttonGridLayout = (GridLayout) findViewById(R.id.buttonGridLayout);
        resultsMap = new HashMap<String, Double>();


        resultTextView = (TextView) findViewById(R.id.resultTextView);
        resultTextView.setMovementMethod(new ScrollingMovementMethod());

        rawStringBuilder = new StringBuilder();
        resultIsCalculated = false;

        saveResult = (Button)findViewById(R.id.saveResult);
        saveResult.setEnabled(false);

        lastInput = "";

        resultTextView.setText("0");

        operatorSet = new TreeSet<>();
        operatorSet.add("+");
        operatorSet.add("-");
        operatorSet.add("*");
        operatorSet.add("/");

    }


    @Override
    public void onClick(View v) {

        Button button =(Button)v;
        String buttonText = button.getText().toString();

        if(buttonText.equals("Delete"))
        {
            deleteLastCharacter();
            resultTextView.setText(rawStringBuilder.toString());
        }
        else if(buttonText.equals("Clear"))
        {
            rawStringBuilder.setLength(0);
            lastInput = "";
            resultTextView.setText("0");
        }
        else if(operatorSet.contains(buttonText) && !lastInput.equals(""))
        {
            if(operatorSet.contains(lastInput) || lastInput.equals(".")) deleteLastCharacter();
            showAndSaveNewInput(buttonText);
            lastInput = buttonText;
        }
        else if(buttonText.equals(".") && android.text.TextUtils.isDigitsOnly(lastInput) && checkForOneDotPerExpr()
                && !lastInput.equals(""))
        {
            showAndSaveNewInput(buttonText);
            lastInput = buttonText;
        }
        else if(operatorSet.contains(lastInput) && buttonText.equals(".")){
            deleteLastCharacter();
            if(checkForOneDotPerExpr()) {
                showAndSaveNewInput(buttonText);
                lastInput = buttonText;
            }
        }
        else if(buttonText.equals("=") && !operatorSet.contains(lastInput))
        {
            String finalString = rawStringBuilder.toString();
            Double result = new Double(reversePolishForm(polishForm(finalString)));

            if(result % 1 == 0)
            {
                resultTextView.setText(finalString + " = " + result.intValue());
            }
            else
            {
                resultTextView.setText(finalString + " = " + result);

            }
            rawStringBuilder.setLength(0);
            lastInput = "";
            saveResult.setEnabled(true);

        }
        else if (Character.isDigit(buttonText.charAt(0)))
        {
            saveResult.setEnabled(false);
            showAndSaveNewInput(buttonText);
            lastInput = buttonText;
        }
    }


    /**
     * Called by onClick method if user press "=" button
     * Returns a double that calculated from an equation
     * it will be wrote to the reult textview
     * @param components contains the operators and numbers in a row
     * @return calculated result
     */
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



    private void showAndSaveNewInput(String newInput){
        rawStringBuilder.append(newInput);
        resultTextView.setText(rawStringBuilder.toString());
    }

     private boolean checkForOneDotPerExpr()
    {
        String[] tmp = rawStringBuilder.toString().split("[^\\d\\.\\d]");
        return (tmp.length != 0 && !tmp[tmp.length-1].contains("."));
    }

    private void deleteLastCharacter()
    {
        int lengthOfRawString = rawStringBuilder.length();
        if(lengthOfRawString > 0) {
            rawStringBuilder.setLength(lengthOfRawString - 1);
            lastInput = lengthOfRawString - 1 == 0 ? "" : Character.valueOf(rawStringBuilder.charAt(lengthOfRawString - 2)).toString();
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
     * Called when the user clicks the "show results" button
     * create new intent to ShowResultsActivity (it will show a list of the saved results)
     * put a map into extras which contains the saved results by the user
     * then start activity with this intent
     * @param view
     */
    public void showResults(View view) {
        Intent intent = new Intent(this, ShowResultsActivity.class);
        intent.putExtra(EXTRA_RESULT, (Serializable) resultsMap);
        startActivity(intent);
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
        saveResult.setEnabled(false);
    }
}