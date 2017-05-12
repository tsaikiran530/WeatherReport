package com.weatherreport.com.weatherreport.model;

/**
 * DayForecast.
 */
public class DayForecast {
    public Weather weather = new Weather();
    public ForecastTemp forecastTemp = new ForecastTemp();

    public class ForecastTemp {
        public float day;
        public float min;
        public float max;
        public float night;
        public float eve;
        public float morning;
    }

}
