package titans.calculator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        setTitle(R.string.showResults_appBar);

        LinearLayout myLinearLayout = (LinearLayout) findViewById(R.id.resultsLayout);

        Intent intent = getIntent();
        ArrayList<ResultData> mResultsArrayList = new ArrayList<>( intent.getIntegerArrayListExtra(MainActivity.EXTRA_RESULT) );

        for( ResultData e : mResultsArrayList ) {
            String date = e.getSavedResultDate();
            double value = e.getResultValue();

            TextView textView = new TextView(this);
            textView.setTextSize(25);
            textView.setBackgroundResource(R.color.blue);
            textView.setTextColor(Color.BLACK);

            String display = "Date:    " + date + '\n' + "Result:    " + value + '\n';
            textView.setText(display);

            if (myLinearLayout != null) {
                myLinearLayout.addView(textView);
            }
        }
    }
}
