package com.eatour.hyunjongkim.weatherfood;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eatour.hyunjongkim.weatherfood.data.remote.RemoteService;
import com.eatour.hyunjongkim.weatherfood.data.remote.ServiceGeneratorImage;
import com.eatour.hyunjongkim.weatherfood.data.remote.ServiceGeneratorWeather;
import com.eatour.hyunjongkim.weatherfood.lib.Utils;
import com.eatour.hyunjongkim.weatherfood.model.ImageInfoModel;
import com.eatour.hyunjongkim.weatherfood.model.ImageModel;
import com.eatour.hyunjongkim.weatherfood.model.Profile;
import com.eatour.hyunjongkim.weatherfood.model.WeatherModel;
import com.eatour.hyunjongkim.weatherfood.model.item.ItemsImageItem;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String CURRENT_KEY_WORD = "";
    private int CURRENT_WEATHRER_CONDITION_ID = 0;
    private int CURRENT_GOOGLE_IMAGE_API_INDEX = 1;
    String wciIconUrl;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    ImageInfoModel imageInfoModel;
    String keyWordForImageSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeView = findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        getWeatherInfo(37.376385, 126.635564, RemoteService.WEATHER_API_KEY);

        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.sky_food_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.sky_food_swipe_out_msg_view));


        /*for(Profile profile : Utils.loadProfiles(this.getApplicationContext())){
            mSwipeView.addView(new SkyFoodCard(mContext, profile, mSwipeView));
        }*/

       /*for (ImageInfoModel imageInfoModel :) {
            mSwipeView.addView(new SkyFoodCard(mContext, imageInfoModel, mSwipeView));
        }*/

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(true);
            }
        });

    } // onCreate End

    private void getWeatherInfo(double lat, double lon, String appId) {
        RemoteService remoteService = ServiceGeneratorWeather.createService(RemoteService.class);
        Call<WeatherModel> call = remoteService.getWeatherInfo(lat, lon, appId);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                WeatherModel weatherModel = response.body();
                CURRENT_WEATHRER_CONDITION_ID = weatherModel.getWeatherWeatherItem().get(0).getId();
                keyWordForImageSearch = getKeyword(CURRENT_WEATHRER_CONDITION_ID);

                wciIconUrl = getIconIdURL(CURRENT_WEATHRER_CONDITION_ID);

                getImageInfoFromGoogle(Constants.GOOGLE_IMAGE_API_KEY,
                        Constants.GOOGLE_IMAGE_API_SEARCH_ENGINE_ID,
                        Constants.IMAGE_SEARCH_TYPE,
                        keyWordForImageSearch,
                        1);

            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
            }
        });

    }

    private void getImageInfoFromGoogle(String key, String cx, String searchType, String searchKeyWord, int start) {
        RemoteService remoteService = ServiceGeneratorImage.createService(RemoteService.class);
        Call<ImageModel> call = remoteService.getImageInfo(key, cx, searchType, searchKeyWord, start);

        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {

                ImageModel imageModel = response.body();
                imageInfoModel = new ImageInfoModel();

                for (ItemsImageItem itemsImageItem :imageModel.getItemsImageItem()) {
                    mSwipeView.addView(new SkyFoodCard(mContext, itemsImageItem, mSwipeView, wciIconUrl));
                }

            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                t.getStackTrace();
            }
        });

    }


    // values/strings.xmlから画像検索キーワードを抽出
    private String getKeyword(int _weatherConditionId) {
        String concatWeatherConditionID = Constants.WEATHER_CONDITION_ID_PRE_WORD + String.valueOf(_weatherConditionId);
        int preSearchKeyword = getResources().getIdentifier(concatWeatherConditionID, "string", getPackageName());
        String proSearchKeyWord = getResources().getString(preSearchKeyword);

        return proSearchKeyWord;
    }

    private String getIconIdURL(int _weatherConditionId) {

        String iconIdURL = "http://openweathermap.org/img/w/";
        String preIconStr = "wci_icon_";

        String WholeWeatherIconStr = preIconStr + String.valueOf(_weatherConditionId);
        int iconIdStr = getResources().getIdentifier(WholeWeatherIconStr, "string", getPackageName());
        String proWeatherIconId = getResources().getString(iconIdStr);
        iconIdURL = iconIdURL + proWeatherIconId + ".png";

        return iconIdURL;
    }
}
