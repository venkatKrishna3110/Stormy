package com.example.vuesol.stormy;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private CurrentWeather mCurrentWeather;

    @BindView(R.id.labelTime) TextView mTimeLabel;
    @BindView(R.id.labelTemperature) TextView mTemperatureLabel;
    @BindView(R.id.labelHumidity) TextView mHumidityValue;
    @BindView(R.id.labelPrecip) TextView mPrecipValue;
    @BindView(R.id.labelSummary) TextView mSummaryLabel;
    @BindView(R.id.imageViewIcon) ImageView mIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        String apiKey = "ba5c4d7ecde37c60e2d856e324a517a8";
        double latitude = 37.8267;
        double longitude = -122.4233;
        String forescastUrl = "https://api.darksky.net/forecast/"+apiKey+
                "/"+latitude+","+longitude;

        if(isNetworkAvailable()) {

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forescastUrl)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            mCurrentWeather = getCurrentDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutTheError();
                        }
                    }
                    catch (IOException e) {
                        Log.e(TAG, "Exception Caught : ", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception Caught : ", e);
                    }
                    catch (NullPointerException e) {
                        Log.e(TAG, "Exception Caught : ", e);
                    }
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, R.string.network_unavailable_message,
                    Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, "Main UI Code is Running!");


    }

    private void updateDisplay() {

        //mTemperatureLabel.setText(mCurrentWeather.getTemperature()+"");
      //  mTimeLabel.setText("At "+mCurrentWeather.getFormattedTime()+" it will be ");
      //  mHumidityValue.setText((int) mCurrentWeather.getHumidity()+"");
      //  mPrecipValue.setText(mCurrentWeather.getPrecipChance()+"%");
        mSummaryLabel.setText(mCurrentWeather.getSummary());

        Drawable drawable = getResources().getDrawable(mCurrentWeather.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        Log.i(TAG,"From JSON : "+ timezone);

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setHumidity(currently.getDouble("humidity"));
        currentWeather.setTime(currently.getLong("time"));
        currentWeather.setIcon(currently.getString("icon"));
        currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
        currentWeather.setSummary(currently.getString("summary"));
        currentWeather.setTemperature(currently.getInt("temperature"));
        currentWeather.setTimeZone(timezone);

        Log.d(TAG,currentWeather.getFormattedTime());

        return currentWeather;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;


        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutTheError() {

        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");
    }
}
