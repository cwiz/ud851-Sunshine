/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        mWeatherData = findViewById(R.id.tv_weather_data);

        loadWeatherData();
    }

    public class LoadWeatherDataTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String location = strings[0];
            URL url = NetworkUtils.buildUrl(location);
            String weatherResults = null;
            try {
                weatherResults = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return weatherResults;
        }

        @Override
        protected void onPostExecute(String s) {
            mWeatherData.setText(s);
        }
    }

    private void loadWeatherData(){
        String query = SunshinePreferences.getPreferredWeatherLocation(this);
        new LoadWeatherDataTask().execute(query);
    }
}