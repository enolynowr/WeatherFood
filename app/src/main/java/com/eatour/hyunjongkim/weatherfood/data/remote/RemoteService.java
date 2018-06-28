
package com.eatour.hyunjongkim.weatherfood.data.remote;


import com.eatour.hyunjongkim.weatherfood.model.ImageModel;
import com.eatour.hyunjongkim.weatherfood.model.WeatherModel;
import com.eatour.hyunjongkim.weatherfood.model.item.ItemsImageItem;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * 서버에 호출할 메소드를 선언하는 인터페이스
 */

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
