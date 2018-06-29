
package com.eatour.hyunjongkim.weatherfood.data.remote;

import com.eatour.hyunjongkim.weatherfood.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 天気APIのRetrofit
public class ServiceGeneratorWeather {

    // Interface　メソッドを呼び出せるサービスを生成
    // serviceClass: 遠隔呼び出しメソッドを定義したInterface
    public static <S> S createService(Class<S> serviceClass) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofitImage = new Retrofit.Builder()
                .baseUrl(RemoteService.WEATHER_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        // Interface 具現体
        return retrofitImage.create(serviceClass);
    }
}
