### README.md

+ SK Planet / Weather Planet의 날씨 API 활용입니다.
+ REST API / JSON
+ 참고
  + [Weather planet 날씨정보](http://www.weatherplanet.co.kr/api/)
  
+ 요청 (GET)
``` java
URL url = new URL("https://apis.openapi.sk.com/weather/current/minutely?lat=37.556722&lon=126.960717");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET"); //전송방식
                    connection.setDoInput(true); //데이터를 읽어올지 설정      
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setRequestProperty("appKey", "앱키");
```

+ 결과
``` json
{
  "weather": {
    "minutely": [
      {
        "station": {
          "longitude": "127.0657778000",
          "latitude": "37.1373888900",
          "name": "남촌",
          "id": "446",
          "type": "KMA"
        },
        "wind": {
          "wdir": "287.00",
          "wspd": "2.80"
        },
        "precipitation": {
          "sinceOntime": "0.00",
          "type": "0"
        },
        "sky": {
          "code": "SKY_A01",
          "name": "맑음"
        },
        "rain": {
          "sinceOntime": "0.00",
          "sinceMidnight": "0.00",
          "last10min": "0.00",
          "last15min": "0.00",
          "last30min": "0.00",
          "last1hour": "0.00",
          "last6hour": "0.00",
          "last12hour": "0.00",
          "last24hour": "0.00"
        },
        "temperature": {
          "tc": "22.10",
          "tmax": "24.00",
          "tmin": "14.00"
        },
        "humidity": "",
        "pressure": {
          "surface": "",
          "seaLevel": ""
        },
        "lightning": "0",
        "timeObservation": "2017-05-25 16:53:00"
      }
    ]
  },
  "common": {
    "alertYn": "Y",
    "stormYn": "N"
  },
  "result": {
    "code": 9200,
    "requestUrl": "/weather/current/minutely?version=2&lat=37.123&lon=127.123",
    "message": "성공"
  }
}
```

+ 결과 처리
``` java
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
```
