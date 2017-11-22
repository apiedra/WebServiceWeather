package com.example.apiedra.webservice.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.apiedra.webservice.modelo.Clima;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apiedra on 22/11/17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "Test.weatherFinal";

    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<Clima, Integer> wishListDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Clima.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        List<String> allSql = new ArrayList<String>();
        switch (oldVersion) {
            case 1:
                //allSql.add("alter table AdData add column `new_col` VARCHAR");
                //allSql.add("alter table AdData add column `new_col2` VARCHAR");
        }
        for (String sql : allSql) {
            db.execSQL(sql);
        }

    }

    public Dao<Clima, Integer> getUserListDao() {
        if (null == wishListDao) {
            try {
                wishListDao = getDao(Clima.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return wishListDao;
    }
}
