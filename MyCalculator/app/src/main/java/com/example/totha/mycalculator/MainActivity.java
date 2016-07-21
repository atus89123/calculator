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


/*          TO_DO           */
/*  - AUTOMATIC SRCOLL DOWN
    - GRIDLAYOUT SIZE ELEMNTS
     */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    PolishFormAlgorithm polishForm;
    TextView resultTextView;
    StringBuilder rawStringBuilder;
    Map<String, Double> resultsMap;

    public final static String EXTRA_RESULT = "com.mycompany.calculator.MESSAGE";


    Button saveResult;
    boolean resultIsCalculated;
    /*String lastInput;
    Set<String> operatorSet;
    Stack<String> previousInputs;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        GridLayout buttonGridLayout = (GridLayout) findViewById(R.id.buttonGridLayout);

        polishForm = new PolishFormAlgorithm();

        resultsMap = new HashMap<String, Double>();


        resultTextView = (TextView) findViewById(R.id.resultTextView);
        resultTextView.setMovementMethod(new ScrollingMovementMethod());

        rawStringBuilder = new StringBuilder();
        resultIsCalculated = false;

        saveResult = (Button)findViewById(R.id.saveResult);
        saveResult.setEnabled(false);

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
        String finalString = rawStringBuilder.toString();

        switch(buttonText) {
            case "=":
                if(Character.isDigit(finalString.charAt(finalString.length() - 1))){
                    double res = polishForm.reversePolishForm(polishForm.toPolishForm(finalString));
                    resultTextView.setText(finalString + " = " + res);
                    rawStringBuilder.setLength(0);
                    rawStringBuilder.append(res);
                    resultIsCalculated = true;
                    saveResult.setEnabled(true);
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                saveResult.setEnabled(false);
                resultIsCalculated = false;
                validateOperator(rawStringBuilder, buttonText);
                break;
            case ".":
                String[] tmp = finalString.split("[^\\d\\.\\d]");
                if (tmp.length != 0 && !tmp[tmp.length - 1].contains("."))
                    validateOperator(rawStringBuilder, buttonText);
                break;
            case "Clear":
                saveResult.setEnabled(false);
                resultIsCalculated = false;
                rawStringBuilder.setLength(0);
                resultTextView.setText("0");
                break;
            case "Delete":
                if(finalString.length() != 0){
                    rawStringBuilder.setLength(finalString.length()-1);
                    resultTextView.setText(rawStringBuilder.toString());
                }
                break;
            default:
                saveResult.setEnabled(false);
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
    }
    */




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
    }
}