package titans.calculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // STATIC MEMBERS

    public final static String EXTRA_RESULT = "titans.calculator.MESSAGE";

    private PolishFormAlgorithm mPolishForm;
    private TextView mResultTextView;
    private StringBuilder rawStringBuilder;
    private List<ResultData> mResultDataList;

    private Button mSaveResultButton;
    boolean mIsResultCalculated;

    String lastInput;
    Set<String> operatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPolishForm = new PolishFormAlgorithm();
        mResultDataList = new ArrayList<>();

        rawStringBuilder = new StringBuilder();
        mIsResultCalculated = false;

        lastInput = "";

        operatorSet = new TreeSet<>();
        operatorSet.add("+");
        operatorSet.add("-");
        operatorSet.add("*");
        operatorSet.add("/");

        mResultTextView = (TextView) findViewById(R.id.resultTextView);
        mResultTextView.setMovementMethod(new ScrollingMovementMethod());
        mResultTextView.setText("0");

        mSaveResultButton = (Button) findViewById(R.id.saveResult);
        if (mSaveResultButton != null) {
            mSaveResultButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.main_sevenButton) {

        }

        Button button = (Button) v;
        String buttonText = button.getText().toString();

        if(buttonText.equals("Delete"))
        {
            deleteLastCharacter();
            mResultTextView.setText(rawStringBuilder.toString());
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
            Double result = mPolishForm.reversePolishForm(mPolishForm.toPolishForm(finalString));

            if(result % 1 == 0)
            {
                finalString += " = " + result.intValue();
                mResultTextView.setText(finalString);
            }
            else
            {
                finalString += " = " + result;
                mResultTextView.setText(finalString);

            }
            rawStringBuilder.setLength(0);
            lastInput = "";
            mSaveResultButton.setEnabled(true);

        }
        else if (Character.isDigit(buttonText.charAt(0)))
        {
            mSaveResultButton.setEnabled(false);
            showAndSaveNewInput(buttonText);
        }
    }

    /**
     * Clears the rawStringBuilder, resets
     * the lastInput and sets the mResultTextView's text to 0.
     */
    private void clearResultTextView(){
        rawStringBuilder.setLength(0);
        lastInput = "";
        mResultTextView.setText("0");
    }

    /**
     * Appends the new input to the end of the rawStringbuilder,
     * updates the mResultTextView and saves the new input as last input.
     * @param newInput is the new input parameter.
     */
    private void showAndSaveNewInput(String newInput){
        rawStringBuilder.append(newInput);
        mResultTextView.setText(rawStringBuilder.toString());
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
    private void deleteLastCharacter() {
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
        intent.putExtra(EXTRA_RESULT, (Serializable) mResultDataList);
        startActivity(intent);
    }

    /**
     * This method runs, when click the Save Result button. This save the TextView text parameters
     * in Map. The map's key is a Date and the value is a Double. The method first split the String and get the actual date
     * then put the date and the result in a Map.
     *
     * @param v not used
    */
    public void saveResult(View v) {
        String result = mResultTextView.getText().toString();
        String[] splitResult = result.split("=");
        Double doubleResult = Double.parseDouble(splitResult[1]);

        mResultDataList.add(new ResultData(doubleResult));
        mSaveResultButton.setEnabled(false);
    }
}