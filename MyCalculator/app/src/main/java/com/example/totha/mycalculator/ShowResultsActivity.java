package com.example.totha.mycalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShowResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);
        setTitle("Saved Results");

        LinearLayout layout = (LinearLayout) findViewById(R.id.resultsLayout);
        Intent intent = getIntent();
        Map<String, Double> resultsMap = new HashMap<String, Double>((Map)intent.getSerializableExtra(MainActivity.EXTRA_RESULT));

        for( String key : resultsMap.keySet() ) {
            TextView textView = new TextView(this);
            textView.setTextSize(30);
            textView.setText("Date:    " + key + '\n' + "Result:    " + resultsMap.get(key) + '\n');
            layout.addView(textView);
        }
    }
}
