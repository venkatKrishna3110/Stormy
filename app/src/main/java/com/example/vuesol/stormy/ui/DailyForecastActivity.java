package com.example.vuesol.stormy.ui;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vuesol.stormy.R;

public class DailyForecastActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
    }
}
