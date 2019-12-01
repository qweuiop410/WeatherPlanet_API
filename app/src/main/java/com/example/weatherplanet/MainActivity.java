package com.example.weatherplanet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String requestUrl;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread() {
            @Override
            public void run() {
                try {
                    // 위, 경도는 서울로 지정
                    URL url = new URL("https://apis.openapi.sk.com/weather/current/minutely?lat=37.556722&lon=126.960717");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET"); //전송방식
                    connection.setDoInput(true);        //데이터를 읽어올지 설정
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setRequestProperty("appKey", "앱키");

                    InputStream is = connection.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                    while ((result = br.readLine()) != null) {
                        sb.append(result + "\n");
                    }

                    result = sb.toString();
                    try {
                        JSONObject jsonObj = new JSONObject(result);
                        JSONObject getjson = jsonObj.getJSONObject("weather").getJSONArray("minutely").getJSONObject(0);

                        String city = getjson.getJSONObject("station").getString("name"); //관측소 위치
                        String sky = getjson.getJSONObject("sky").getString("name"); // 하늘 상태
                        String rain = getjson.getJSONObject("rain").getString("sinceOntime"); // 강수 확률
                        String temp = getjson.getJSONObject("temperature").getString("tc"); // 현재온도
                        String tempMin = getjson.getJSONObject("temperature").getString("tmin"); // 최저 온도
                        String tempMax = getjson.getJSONObject("temperature").getString("tmax");  //최고 온도
                        String humid = getjson.getString("humidity"); // 습도

                        Log.d("     도시     : ", city + " // " + sky + " // " + rain + " // " + temp + " // " + tempMin + " // " + tempMax + " // " + humid);
                        //하늘상태 코드명 (https://openapi.sk.com/resource/apidoc/indexView)
                        // SKY_A01:맑음,
                        // SKY_A02:구름조금,
                        // SKY_A03:구름많음,
                        // SKY_A04:구름많고 비,
                        // SKY_A05:구름많고 눈,
                        // SKY_A06:구름많고 비 또는 눈,
                        // SKY_A07:흐림,
                        // SKY_A08:흐리고 비,
                        // SKY_A09:흐리고 눈,
                        // SKY_A10:흐리고 비 또는 눈,
                        // SKY_A11:흐리고 낙뢰
                        // SKY_A12:뇌우/비,
                        // SKY_A13:뇌우/눈,
                        // SKY_A14:뇌우/비 또는 눈

                    } catch (JSONException e) {
                        Log.d("     JSON ERROR ", e.getMessage());
                    }

                } catch (MalformedURLException e) {
                    Log.d("   MalformedURL   ", e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d("     IOException   :", e.getMessage());
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
