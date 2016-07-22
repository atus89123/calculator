package titans.calculator;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.HashMap;

public class ShowResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        setTitle(R.string.showResults_appBar);

        LinearLayout myLinearLayout = (LinearLayout) findViewById(R.id.resultsLayout);
        Intent intent = getIntent();
        HashMap<String, Double> resultsMap = (HashMap) intent.getSerializableExtra(MainActivity.EXTRA_RESULT);

        for( String key : resultsMap.keySet() ) {
            TextView textView = new TextView(this);
            textView.setTextSize(25);
            textView.setBackgroundResource(R.color.blue);
            textView.setTextColor(Color.BLACK);
            String display = "Date:    " + key + '\n' + "Result:    " + resultsMap.get(key) + '\n';
            textView.setText(display);
            if (myLinearLayout != null) {
                myLinearLayout.addView(textView);
            }
        }
    }
}
