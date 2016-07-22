package titans.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.calculator.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    PolishFormAlgorithm polishForm;
    TextView resultTextView;
    StringBuilder rawStringBuilder;
    Map<String, Double> resultsMap;

    public final static String EXTRA_RESULT = "titans.calculator.MESSAGE";

    Button saveResult;
    boolean resultIsCalculated;

    String lastInput;
    Set<String> operatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        polishForm = new PolishFormAlgorithm();
        resultsMap = new HashMap<String, Double>();

        resultTextView.setMovementMethod(new ScrollingMovementMethod());

        rawStringBuilder = new StringBuilder();
        resultIsCalculated = false;

        saveResult = (Button)findViewById(R.id.saveResult);
        if (saveResult != null) {
            saveResult.setEnabled(false);
        }

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
            clearResultTextView();
        }
        else if(operatorSet.contains(buttonText) && !lastInput.equals(""))
        {
            if(operatorSet.contains(lastInput) || lastInput.equals(".")) deleteLastCharacter();
            showAndSaveNewInput(buttonText);
        }
        else if(buttonText.equals(".") && android.text.TextUtils.isDigitsOnly(lastInput) && checkForOneDotPerExpr()
                && !lastInput.equals(""))
        {
            showAndSaveNewInput(buttonText);
        }
        else if(operatorSet.contains(lastInput) && buttonText.equals(".")){
            deleteLastCharacter();
            if(checkForOneDotPerExpr()) {
                showAndSaveNewInput(buttonText);
            }
        }
        else if(buttonText.equals("=") && !operatorSet.contains(lastInput))
        {
            String finalString = rawStringBuilder.toString();
            Double result = polishForm.reversePolishForm(polishForm.toPolishForm(finalString));

            if(result % 1 == 0)
            {
                finalString += " = " + result.intValue();
                resultTextView.setText(finalString);
            }
            else
            {
                finalString += " = " + result;
                resultTextView.setText(finalString);

            }
            rawStringBuilder.setLength(0);
            lastInput = "";
            saveResult.setEnabled(true);

        }
        else if (Character.isDigit(buttonText.charAt(0)))
        {
            saveResult.setEnabled(false);
            showAndSaveNewInput(buttonText);
        }
    }

    /**
     * Clears the rawStringBuilder, resets
     * the lastInput and sets the resultTextView's text to 0.
     */
    private void clearResultTextView(){
        rawStringBuilder.setLength(0);
        lastInput = "";
        resultTextView.setText("0");
    }

    /**
     * Appends the new input to the end of the rawStringbuilder,
     * updates the resultTextView and saves the new input as last input.
     * @param newInput is the new input parameter.
     */
    private void showAndSaveNewInput(String newInput){
        rawStringBuilder.append(newInput);
        resultTextView.setText(rawStringBuilder.toString());
        lastInput = newInput;

    }

    /**
     * Checks if the last part of the expression contains a dot.
     * @return true, if the expression isn't empty and doesn't contain a dot.
     */
     private boolean checkForOneDotPerExpr()
    {
        String[] tmp = rawStringBuilder.toString().split("[^\\d\\.\\d]");
        return (tmp.length != 0 && !tmp[tmp.length-1].contains("."));
    }

    /**
     * Deletes the last character of the rawStringBuilder, if it's length
     * is not 0 and shifts the lastInput back accordingly to the deletion.
     */
    private void deleteLastCharacter()
    {
        int lengthOfRawString = rawStringBuilder.length();
        if(lengthOfRawString > 0) {
            rawStringBuilder.setLength(lengthOfRawString - 1);
            lastInput = lengthOfRawString - 1 == 0 ? "" : Character.valueOf(rawStringBuilder.charAt(lengthOfRawString - 2)).toString();
        }
    }

    /**
     * Called when the user clicks the "show results" button
     * create new intent to ShowResultsActivity (it will show a list of the saved results)
     * put a map into extras which contains the saved results by the user
     * then start activity with this intent
     * @param view not used
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd. H:mm:ss", Locale.ENGLISH);
        String result = resultTextView.getText().toString();
        String[] splitResult = result.split("=");
        Double doubleResult = Double.parseDouble(splitResult[1]);
        resultsMap.put(dateFormat.format(date), doubleResult);
        System.out.println(resultsMap.size());
        saveResult.setEnabled(false);
    }
}