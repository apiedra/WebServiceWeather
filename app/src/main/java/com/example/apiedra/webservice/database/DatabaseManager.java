package com.example.apiedra.webservice.database;

import android.content.Context;

import com.example.apiedra.webservice.modelo.Clima;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by apiedra on 22/11/17.
 */

public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseHelper helper;

    public static void init(Context context){
        if (instance == null) {
            instance = new DatabaseManager(context);
        }
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    private DatabaseManager(Context context) {
        helper = new DatabaseHelper(context);
    }

    private DatabaseHelper getHelper() {
        return helper;
    }

    public List<Clima> getAllWeatherLists() {
        List<Clima> WeatherLists = null;
        try {
            WeatherLists = getHelper().getUserListDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return WeatherLists;
    }

    public void addWeather(Clima weatherObject) {
        try {
            getHelper().getUserListDao().create(weatherObject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateWeather(Clima weatherObject) {
        try {
            getHelper().getUserListDao().update(weatherObject);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
