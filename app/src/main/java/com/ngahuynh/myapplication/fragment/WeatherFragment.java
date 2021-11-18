package com.ngahuynh.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ngahuynh.myapplication.R;
import com.ngahuynh.myapplication.helper.Weather;
import com.ngahuynh.myapplication.viewmodel.DetailViewModel;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class WeatherFragment extends Fragment implements View.OnClickListener {
    String CITY;
    String API = "2df5a6349a3ac9a86c4f6d6c6b4920bd";
    ImageView search;
    EditText etCity;
    URL url;
    String response;

    TextView city,country,time,temp,forecast,humidity,min_temp,max_temp,sunrises,sunsets;

    DetailViewModel weatherVM;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        weatherVM = new ViewModelProvider(requireActivity()).get(DetailViewModel.class);
        weatherVM.getWeatherData().observe(getViewLifecycleOwner(), weatherObserver );

        TextView title = view.findViewById(R.id.tv_bar_title);
        if (title != null) {
            title.setText("Weather");
        }

        {
            etCity = (EditText) view.findViewById(R.id.edt_city);
            search = (ImageView) view.findViewById(R.id.img_search);
            search.setOnClickListener(this);

            // CALL ALL ANSWERS :

            city = (TextView) view.findViewById(R.id.tv_city);
            country = (TextView) view.findViewById(R.id.tv_country);
            time = (TextView) view.findViewById(R.id.tv_time);
            temp = (TextView) view.findViewById(R.id.tv_temp);
            forecast = (TextView) view.findViewById(R.id.tv_forecast);
            humidity = (TextView) view.findViewById(R.id.tv_humidity);
            min_temp = (TextView) view.findViewById(R.id.tv_min_temp);
            max_temp = (TextView) view.findViewById(R.id.tv_max_temp);
            sunrises = (TextView) view.findViewById(R.id.tv_sunrise);
            sunsets = (TextView) view.findViewById(R.id.tv_sunset);

        }

        CITY = weatherVM.getWeatherData().getValue().city;

    }

    final Observer<Weather> weatherObserver = new Observer<Weather>() {
        @Override
        public void onChanged(@Nullable final Weather appWeather ) {
            try {
                url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        response = getDataFromURL(url);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObj = new JSONObject(response);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);
                JSONObject sys = jsonObj.getJSONObject("sys");

                // CALL VALUE IN API :
                String city_name = jsonObj.getString("name");
                String countryname = sys.getString("country");
                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Last Updated at: " + new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temperature = main.getString("temp");
                String cast = weather.getString("description");
                String humi_dity = main.getString("humidity");
                String temp_min = main.getString("temp_min");
                String temp_max = main.getString("temp_max");
                Long rise = sys.getLong("sunrise");
                String sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(rise * 1000));
                Long set = sys.getLong("sunset");
                String sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(set * 1000));


                setWeatherCity(city_name);
                setWeatherCountry(countryname);
                setWeatherLastUpdated(updatedAtText);
                setWeatherTemp(tempConvert(temperature) + "°F");
                setWeatherCast(cast);
                setWeatherHumidity(humi_dity);
                setWeatherMinTemp(tempConvert(temp_min));
                setWeatherMaxTemp(tempConvert(temp_max));
                setWeatherSunrise(sunrise);
                setWeatherSunset(sunset);

            } catch (Exception e) {

                Log.i("TAG", "ERROR");
            }

            try{
                city.setText(appWeather.getCity());
                country.setText(appWeather.getCountry());
                time.setText(appWeather.getLastUpdated());
                temp.setText(appWeather.getTemp());
                forecast.setText(appWeather.getCast());
                humidity.setText(appWeather.getHumidity());
                min_temp.setText(appWeather.getMinTemp());
                max_temp.setText(appWeather.getMaxTemp());
                sunrises.setText(appWeather.getSunrise());
                sunsets.setText(appWeather.getSunset());



            } catch (Exception e) {

                Log.i("TAG", "SETTING ERROR");
            }
        }
    };

    public static String tempConvert(String temp) {
        float C = Float.parseFloat(temp);
        /* Convert Celsius to Fahrenheit */
        int F = (int) (C * (9f / 5) + 32);
        /* Return result */
        return String.valueOf(F);
    }

    public static String getDataFromURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
            else{
                return null;
            }

        }finally {
            urlConnection.disconnect();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_search:{
                CITY = etCity.getText().toString();

                Weather newData = weatherVM.getWeatherData().getValue();
                newData.setCity(CITY);
                weatherVM.getWeatherData().setValue(newData);

            }
        }
    }

    void setWeatherCity(String city) {weatherVM.setWeatherCity(city);}
    void setWeatherCountry(String country) {weatherVM.setWeatherCountry(country);}
    void setWeatherLastUpdated(String lastUpdated) {weatherVM.setWeatherLastUpdated(lastUpdated);}
    void setWeatherTemp(String temperature) {weatherVM.setWeatherTemp(temperature);}
    void setWeatherCast(String cast) {weatherVM.setWeatherCast(cast);}
    void setWeatherHumidity(String humidity) {weatherVM.setWeatherHumidity(humidity);}
    void setWeatherMinTemp(String minTemp) {weatherVM.setWeatherMinTemp(minTemp);}
    void setWeatherMaxTemp(String maxTemp) {weatherVM.setWeatherMaxTemp(maxTemp);}
    void setWeatherSunrise(String sunrise) {weatherVM.setWeatherSunrise(sunrise);}
    void setWeatherSunset(String sunset) {weatherVM.setWeatherSunset(sunset);}
}