package com.example.apiedra.webservice.modelo;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by apiedra on 20/11/17.
 */

public class Clima implements Serializable
{
    public Clima()
    {

    }

    @DatabaseField
    public long Sunrise;

    @DatabaseField
    public long Sunset;

    @DatabaseField
    public String Icon;

    @DatabaseField
    public String Temperature;

    @DatabaseField
    public String MinTemperature;

    @DatabaseField
    public String MaxTemperature;

    @DatabaseField
    public String Humidity;

    @DatabaseField
    public String WindSpeed;

    @DatabaseField
    public double SeaLevel;

    @DatabaseField
    public String City;

    @DatabaseField
    public String Country;

    @DatabaseField
    public String WeatherDescription;

    @DatabaseField
    public String WeatherMain;

    @DatabaseField
    public String Date;

    /*public double kelvinDegree = 273.15;
    private NumberFormat formatter = new DecimalFormat("#0");*/

    // Temperature - 273.15

    /*public void SetSunrise(Long value) {   sunrise = value; }
    public Long GetSunrise(){return sunrise;}
    public void SetSunset(Long value) {   sunset = value; }
    public Long GetSunset(){return sunset;}
    public void SetHumidity(double value){   humidity = value; }
    public double GetHumidity(){return humidity;}
    public void SetTemperature(double value) {   temperature = Double.parseDouble(formatter.format( value - kelvinDegree )) ; }
    public double GetTemperature(){return temperature;}
    public void SetMinTemperature(double value) {    min_temperature = Double.parseDouble(formatter.format( value - kelvinDegree )) ; }
    public double GetMinTemperature(){return min_temperature;}
    public void SetMaxTemperature(double value) {   max_temperature = Double.parseDouble(formatter.format( value - kelvinDegree )) ;    }
    public double GetMaxTemperature(){return max_temperature;}
    public void SetCity(String value) {   city = value; }
    public String GetCity(){return city;}
    public void SetWeatherDescription(String value) {   weather_description = value; }
    public String GetWeatherDescription(){return weather_description;}
    public void SetCountry(String value) {   country = value;    }
    public String GetCountry(){return country;}
    public void SetWindSpeed(double value) {   windspeed = value;    }
    public double GetWindSpeed(){return windspeed;}
    public void SetSeaLevel(double value) {   sealevel = value;    }
    public double GetSeaLevel(){return sealevel;}
    public void SetWeatherMain(String value) {   weatherMain = value; }
    public String GetWeatherMain(){return weatherMain;}
    public void SetDate(Date value) {   date = value; }
    public Date GetDate(){return date;}*/

}