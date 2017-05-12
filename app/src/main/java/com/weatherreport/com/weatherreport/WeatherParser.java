package com.weatherreport.com.weatherreport;


import com.weatherreport.com.weatherreport.model.DayForecast;
import com.weatherreport.com.weatherreport.model.Location;
import com.weatherreport.com.weatherreport.model.Weather;
import com.weatherreport.com.weatherreport.model.WeatherForeCasting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * WeatherParser.
 */
public class WeatherParser {

    public static Weather parseWeather(String data) throws JSONException {
        Weather weather = new Weather();
        JSONObject jObj = new JSONObject(data);

        Location loc = new Location();

        JSONObject coordObj = getObject("coord", jObj);
        loc.setLatitude(getFloat("lat", coordObj));
        loc.setLongitude(getFloat("lon", coordObj));

        JSONObject sysObj = getObject("sys", jObj);
        loc.setCountry(getString("country", sysObj));
        loc.setCity(getString("name", jObj));
        weather.location = loc;

        // weather info.
        JSONArray jArr = jObj.getJSONArray("weather");

        // There is only one index.
        JSONObject JSONWeather = jArr.getJSONObject(0);
        weather.currCondition.setWeatherId(getInt("id", JSONWeather));
        weather.currCondition.setDescr(getString("description", JSONWeather));
        weather.currCondition.setCondition(getString("main", JSONWeather));
        weather.currCondition.setIcon(getString("icon", JSONWeather));

        JSONObject mainObj = getObject("main", jObj);
        weather.currCondition.setHumidity(getInt("humidity", mainObj));
        weather.currCondition.setPressure(getInt("pressure", mainObj));
        weather.temperature.setMaxTemp(getFloat("temp_max", mainObj));
        weather.temperature.setMinTemp(getFloat("temp_min", mainObj));
        weather.temperature.setTemp(getFloat("temp", mainObj));


        return weather;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }


    public static WeatherForeCasting getForecastWeather(String data) throws JSONException {

        WeatherForeCasting forecast = new WeatherForeCasting();

        JSONObject jObj = new JSONObject(data);

        JSONArray jArr = jObj.getJSONArray("list");

        for (int i = 0; i < jArr.length(); i++) {
            JSONObject jDayForecast = jArr.getJSONObject(i);

            DayForecast df = new DayForecast();

            JSONObject jTempObj = jDayForecast.getJSONObject("temp");

            df.forecastTemp.day = (float) jTempObj.getDouble("day");
            df.forecastTemp.min = (float) jTempObj.getDouble("min");
            df.forecastTemp.max = (float) jTempObj.getDouble("max");
            df.forecastTemp.night = (float) jTempObj.getDouble("night");
            df.forecastTemp.eve = (float) jTempObj.getDouble("eve");
            df.forecastTemp.morning = (float) jTempObj.getDouble("morn");

            df.weather.currCondition.setPressure((float) jDayForecast.getDouble("pressure"));
            df.weather.currCondition.setHumidity((float) jDayForecast.getDouble("humidity"));

            JSONArray jWeatherArr = jDayForecast.getJSONArray("weather");
            JSONObject jWeatherObj = jWeatherArr.getJSONObject(0);
            df.weather.currCondition.setWeatherId(getInt("id", jWeatherObj));
            df.weather.currCondition.setDescr(getString("description", jWeatherObj));
            df.weather.currCondition.setCondition(getString("main", jWeatherObj));
            df.weather.currCondition.setIcon(getString("icon", jWeatherObj));

            forecast.addForecast(df);
        }


        return forecast;
    }


}
