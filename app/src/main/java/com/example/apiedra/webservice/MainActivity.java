package com.example.apiedra.webservice;

import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.apiedra.webservice.database.DatabaseManager;
import com.example.apiedra.webservice.modelo.Clima;
import com.example.apiedra.webservice.service.ServiceController;
import com.example.apiedra.webservice.utils.GeneralFunctions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener, LocationListener, OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    TextView lugar, fecha, hora, icono, temperatura, humedad, descripcion;
    Typeface weatherFont;
    GeneralFunctions generalFunctions;
    protected Date date;
    protected Double latitude, longitude;
    String appId = "a7cd43dfd2919186e801aaa8517f3e79";
    protected boolean busyService;
    ServiceController serviceController;
    private GoogleMap mMap;
    private ProgressBar waitDataProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarComponentes();

        weatherFont = Typeface.createFromAsset(this.getAssets(), "fonts/weather.ttf");
        SimpleDateFormat day = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = day.format(d);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd/MMM/yyyy");
        String formattedDate = date.format(c.getTime());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String formattedHour = simpleDateFormat.format(c.getTime());


        serviceController = new ServiceController();
        latitude = 9.9333;
        longitude = -84.0833;
        callWebService(latitude, longitude);
        busyService = false;
        fecha.setText(formattedDate + ", " + dayOfTheWeek);
        hora.setText(formattedHour);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        DatabaseManager.init(this);


    }

    public void iniciarComponentes() {
        lugar = (TextView) findViewById(R.id.lugar);
        fecha = (TextView) findViewById(R.id.fecha);
        hora = (TextView) findViewById(R.id.hora);
        icono = (TextView) findViewById(R.id.icono);
        temperatura = (TextView) findViewById(R.id.temperatura);
        humedad = (TextView) findViewById(R.id.humedad);
        descripcion = (TextView) findViewById(R.id.descripcion);

    }

    public String GetIcon(String weatherMain) {
        String icon;
        switch (weatherMain) {
            case "Rain": {
                icon = getString(R.string.weather_rainy);
            }
            break;

            case "Fog": {
                icon = getString(R.string.weather_foggy);
            }
            break;

            case "Cloud": {
                icon = getString(R.string.weather_cloudy);
            }
            break;

            case "Snow": {
                icon = getString(R.string.weather_snowy);
            }
            break;

            case "Drizzle": {
                icon = getString(R.string.weather_drizzle);
            }
            break;

            case "Thunder": {
                icon = getString(R.string.weather_thunder);
            }
            break;
            case "Clear": {
                icon = getString(R.string.weather_clear_night);
            }
            break;

            default: {
                icon = getString(R.string.weather_sunny);
            }
            break;
        }

        return icon;
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray jsonArray;
        JSONObject jsonObject;
        generalFunctions = new GeneralFunctions();

        try {
            Clima weather = new Clima();

            jsonArray = response.getJSONArray("weather");
            jsonObject = jsonArray.getJSONObject(0);
            weather.WeatherDescription = jsonObject.getString("description");
            weather.WeatherMain = jsonObject.getString("main");

            jsonObject = response.getJSONObject("main");
            weather.Temperature = generalFunctions.ConvertToCelsiusDegrees(jsonObject.getDouble("temp"));
            weather.MaxTemperature = generalFunctions.ConvertToCelsiusDegrees(jsonObject.getDouble("temp_max"));
            weather.MinTemperature = generalFunctions.ConvertToCelsiusDegrees(jsonObject.getDouble("temp_min"));
            weather.Humidity = generalFunctions.ConvertToPercentage(jsonObject.getDouble("humidity"));
            //weather.SeaLevel = jsonObject.getDouble("sea_level");

            DateFormat dateFormat = new SimpleDateFormat(getText(R.string.fulltime).toString());
            date = new Date();
            weather.Date = dateFormat.format(date);

            jsonObject = response.getJSONObject("wind");
            weather.WindSpeed = generalFunctions.ConvertToSpeed(jsonObject.getDouble("speed"));

            jsonObject = response.getJSONObject("sys");
            weather.Country = jsonObject.getString("country");
            weather.Sunrise = jsonObject.getLong("sunrise");
            weather.Sunset = jsonObject.getLong("sunset");
            weather.City = response.getString("name");


            lugar.setText(weather.City);
            temperatura.setText(String.valueOf(weather.Temperature));
            humedad.setText(weather.Humidity);
            descripcion.setText(weather.WeatherDescription);
            icono.setText(GetIcon(weather.WeatherMain));
            icono.setTypeface(weatherFont);
            icono.setText(GetIcon(weather.WeatherMain));




            /*
            tvWeatherMain.setText(weather.WeatherMain);
            tvMinMax.setText(String.valueOf(weather.MinTemperature) + " / " + String.valueOf(weather.MaxTemperature));
            tvSeaLevel.setText(String.valueOf(weather.SeaLevel) );
            tvWindSpeed.setText(String.valueOf(weather.WindSpeed) );
            tvCityCountry.setText(weather.City + ", " + weather.Country);

            tvWeatherIcon.setTypeface(weatherFont);*/

          /*  weatherObjectList = DatabaseManager.getInstance().getAllWeatherLists();

            if(weatherObjectList.size() == 0 || weatherObjectList == null)
            {
                DatabaseManager.getInstance().addWeather(weather);
            }
            else
            {
                for (WeatherObject wo : weatherObjectList)
                {
                    if (wo.City.equals(weather.City) && wo.Date.equals(weather.Date))
                    {
                        rowExist = true;
                        break;
                    }
                    else
                    {
                        rowExist = false;
                    }
                }

                if (rowExist)
                {
                    DatabaseManager.getInstance().updateWeather(weather);
                }
                else
                {
                    DatabaseManager.getInstance().addWeather(weather);
                }
            }*/
        } catch (Exception ex) {
            Toast.makeText(getBaseContext(), "Response exception " + ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        } finally {
            //  busyService = false;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
       /* latitude = location.getLatitude();
        longitude = location.getLongitude();

        latitude = 9.9333;
        longitude = -84.0833;
        callWebService(latitude, longitude);*/
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    public void callWebService(Double latitude, Double longitude) {


        String uri = "http://api.openweathermap.org/data/2.5/weather?lat=" + latitude.toString() + "&lon=" + longitude.toString() + "&appid=" + appId; // "http://api.openweathermap.org/data/2.5/weather?lat="+ latitude +"&lon=" + longitude;


        serviceController.jsonObjectRequest(uri, Request.Method.GET, null, this, this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
        LatLng cali = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions()
                .position(cali)
                .title("Cali la Sucursal del cielo"));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cali)
                .zoom(10)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        googleMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        mMap.clear();
        LatLng cali = new LatLng(latLng.latitude, latLng.longitude);
        mMap.addMarker(new MarkerOptions()
                .position(cali)
                .title("Cali la Sucursal del cielo"));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cali)
                .zoom(10)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap.setOnMapLongClickListener(this);
        callWebService(latLng.latitude, latLng.longitude);
    }
}
