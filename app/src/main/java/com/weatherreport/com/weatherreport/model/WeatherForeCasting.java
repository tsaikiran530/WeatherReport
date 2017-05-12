package com.weatherreport.com.weatherreport.model;

import java.util.ArrayList;
import java.util.List;

/**
 * WeatherForeCasting.
 */
public class WeatherForeCasting {


    private List<DayForecast> daysForecast = new ArrayList<DayForecast>();

    public void addForecast(DayForecast forecast) {
        daysForecast.add(forecast);
    }

    public List<DayForecast> getDaysForecast() {
        return daysForecast;
    }
}
