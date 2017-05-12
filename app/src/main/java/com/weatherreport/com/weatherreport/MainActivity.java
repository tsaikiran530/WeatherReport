package com.weatherreport.com.weatherreport;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.weatherreport.com.weatherreport.adapters.ForeCastListAdapter;
import com.weatherreport.com.weatherreport.model.Weather;
import com.weatherreport.com.weatherreport.model.WeatherForeCasting;
import com.weatherreport.com.weatherreport.network.WeatherHttpClient;

import org.json.JSONException;

import java.text.DecimalFormat;

public class MainActivity extends Activity {

    private TextView currentLoc;
    private TextView currentTemp;
    private ImageView imgView;
    private ListView listView;
    private static String noOfDaysForecast = "5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        String city = "London, UK";
        String lang = "en";

        //To get the current day weather.
        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute(new String[]{city,lang});

        //To get the forecast day weather.
        WeatherForeCastAsyncTask task1 = new WeatherForeCastAsyncTask();
        task1.execute(new String[]{city,lang, noOfDaysForecast});
    }

    private void initializeViews(){
        currentLoc = (TextView) findViewById(R.id.current_location);
        currentTemp = (TextView) findViewById(R.id.current_temperature);
        listView = (ListView) findViewById(R.id.listview);
        imgView = (ImageView) findViewById(R.id.weather_image);
    }





    private class WeatherAsyncTask extends AsyncTask<String, Void, Weather> {


        @Override
        protected Weather doInBackground(String... params) {
            WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
            Weather weather = new Weather();
            String data = weatherHttpClient.getWeatherData(params[0], params[1]);

            try {
                weather = WeatherParser.parseWeather(data);
                weather.statusIcon = weatherHttpClient.getImage(weather.currCondition.getIcon() + ".png");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if (weather.statusIcon != null && weather.statusIcon.length > 0) {
                Bitmap img = BitmapFactory.decodeByteArray(weather.statusIcon, 0, weather.statusIcon.length);
                imgView.setImageBitmap(img);
            }

            DecimalFormat df = new DecimalFormat("#.#");
            String formatted = df.format(weather.temperature.getTemp() - 273.15);

            currentLoc.setText(weather.location.getCity() + "," + weather.location.getCountry());
            currentTemp.setText((formatted) + " \u2103");
        }


    }
        private class WeatherForeCastAsyncTask extends AsyncTask<String, Void, WeatherForeCasting> {


            @Override
            protected WeatherForeCasting doInBackground(String... params) {
                WeatherHttpClient weatherHttpClient = new WeatherHttpClient();
                WeatherForeCasting weatherForeCasting  = new WeatherForeCasting();
                String data = weatherHttpClient.getForecastWeatherData(params[0], params[1],params[2]);


                try {
                    weatherForeCasting  = WeatherParser.getForecastWeather(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return weatherForeCasting;
            }


            @Override
            protected void onPostExecute(WeatherForeCasting weatherForeCasting) {
                ForeCastListAdapter adapter = new ForeCastListAdapter(MainActivity.this, weatherForeCasting.getDaysForecast());
                listView.setAdapter(adapter);
            }
        }

}
