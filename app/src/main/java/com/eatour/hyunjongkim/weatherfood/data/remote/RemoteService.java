
package com.eatour.hyunjongkim.weatherfood.data.remote;


import com.eatour.hyunjongkim.weatherfood.model.ImageModel;
import com.eatour.hyunjongkim.weatherfood.model.WeatherModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// サーバーに呼び出すメソッドのInterface
public interface RemoteService {

    String WEATHER_API_KEY = "b163330afcea03fe1c511ff3e76b5562";
    String WEATHER_API_BASE_URL = "https://api.openweathermap.org/data/2.5/";
    String GOOGLE_IMAGE_API_BASE_URL = "https://www.googleapis.com/customsearch/v1/";

    //Weather API
    @GET("weather")
    Call<WeatherModel> getWeatherInfo(@Query("lat") double latitude,
                                      @Query("lon") double longitude,
                                      @Query("APPID") String appId);

    //Image API
    // searchType image固定
    // startは結果の取得インデックス
    @GET("/customsearch/v1")
    Call<ImageModel> getImageInfo(@Query("key") String key,
                                      @Query("cx") String cx,
                                      @Query("searchType") String searchType,
                                      @Query("q") String searchKeyWord,
                                      @Query("start") int start );

}
