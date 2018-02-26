package com.example.vuesol.stormy.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.vuesol.stormy.R;
import com.example.vuesol.stormy.adapters.DayAdapter;
import com.example.vuesol.stormy.weather.Day;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DailyForecastActivity extends ListActivity {

    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
/*
        String[] daysOfTheWeek = {"Sunday", "Monday", "Tuesday", "Wednesday"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, daysOfTheWeek);
        setListAdapter(adapter);
*/

       Intent intent = getIntent();
       // Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        //mDays = Arrays.copyOf(parcelables,parcelables.length,Day[].class);

      //  intent.getStringExtra(MainActivity.DAILY_FORECAST);
        // DayAdapter adapter = new DayAdapter(this, daysOfTheWeek);

    }

}
