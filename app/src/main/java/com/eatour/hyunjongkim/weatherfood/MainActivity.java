package com.eatour.hyunjongkim.weatherfood;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.eatour.hyunjongkim.weatherfood.data.remote.RemoteService;
import com.eatour.hyunjongkim.weatherfood.data.remote.ServiceGeneratorImage;
import com.eatour.hyunjongkim.weatherfood.data.remote.ServiceGeneratorWeather;
import com.eatour.hyunjongkim.weatherfood.lib.MyLog;
import com.eatour.hyunjongkim.weatherfood.lib.MyToast;
import com.eatour.hyunjongkim.weatherfood.model.GeoModel;
import com.eatour.hyunjongkim.weatherfood.model.ImageModel;
import com.eatour.hyunjongkim.weatherfood.model.WeatherModel;
import com.eatour.hyunjongkim.weatherfood.model.item.ItemsImageItem;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public final String TAG = MainActivity.class.getSimpleName();
    private int CURRENT_WEATHER_CONDITION_ID = 0;
    String wciIconUrl;
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;
    String keyWordForImageSearch;
    int startIndexCnt = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSwipeView = findViewById(R.id.swipeView);
        mContext = getApplicationContext();

        getWeatherInfo(GeoModel.knownLatitude, GeoModel.knownLongitude, RemoteService.WEATHER_API_KEY, startIndexCnt);

        settingSwipeHolderView();
    } // onCreate End

    //WeatherAPIの呼出し後、ImageAPIの呼び出し
    private void getWeatherInfo(double lat, double lon, String appId, int _startIndexCnt) {
        RemoteService remoteService = ServiceGeneratorWeather.createService(RemoteService.class);
        Call<WeatherModel> call = remoteService.getWeatherInfo(lat, lon, appId);
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                WeatherModel weatherModel = response.body();
                CURRENT_WEATHER_CONDITION_ID = weatherModel.getWeatherWeatherItem().get(0).getId();
                keyWordForImageSearch = getKeyword(CURRENT_WEATHER_CONDITION_ID);

                wciIconUrl = getIconIdURL(CURRENT_WEATHER_CONDITION_ID);

                getImageInfoFromGoogle(Constants.GOOGLE_IMAGE_API_KEY,
                        Constants.GOOGLE_IMAGE_API_SEARCH_ENGINE_ID,
                        Constants.IMAGE_SEARCH_TYPE,
                        keyWordForImageSearch,
                        _startIndexCnt);

            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                MyToast.l(getApplicationContext(), "ネットワークの状態が悪いです。\n もう一度確認してください。");
                MyLog.d(TAG,"WEATHER API ERROR");
            }
        });

    }

    // ImageのAPI処理
    private void getImageInfoFromGoogle(String key, String cx, String searchType, String searchKeyWord, int start) {
        RemoteService remoteService = ServiceGeneratorImage.createService(RemoteService.class);
        Call<ImageModel> call = remoteService.getImageInfo(key, cx, searchType, searchKeyWord, start);

        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                ImageModel imageModel = response.body();

                for (ItemsImageItem itemsImageItem : imageModel.getItemsImageItem()) {
                    mSwipeView.addView(new SkyFoodCard(mContext, itemsImageItem, mSwipeView, wciIconUrl));
                }
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                MyToast.l(getApplicationContext(), "ネットワークの状態が悪いです。\n もう一度確認してください。");
                MyLog.d(TAG,"IMAGE API ERROR");
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

    // WEATHER CONDITION ID URLの生成
    private String getIconIdURL(int _weatherConditionId) {
        String iconIdURL = "http://openweathermap.org/img/w/";
        String WholeWeatherIconStr = Constants.WEATHER_ICON_ID_PRE_WORD + String.valueOf(_weatherConditionId);
        int iconIdStr = getResources().getIdentifier(WholeWeatherIconStr, "string", getPackageName());
        String proWeatherIconId = getResources().getString(iconIdStr);
        iconIdURL = iconIdURL + proWeatherIconId + ".png";

        return iconIdURL;
    }

    private void settingSwipeHolderView() {

        mSwipeView.getBuilder()
                .setDisplayViewCount(10)
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(-20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.sky_food_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.sky_food_swipe_out_msg_view));

        findViewById(R.id.rejectBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSwipeView.doSwipe(false);
            }
        });

        findViewById(R.id.acceptBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLog.d("Accept");
                mSwipeView.doSwipe(true);
            }
        });

        mSwipeView.addItemRemoveListener(new ItemRemovedListener() {
            @Override
            public void onItemRemoved(int count) {
                MyLog.d("onItemRemoved", ">>>" + count);

                // 画像が0になったらAPIを呼び出す
                if (count == 0) {
                    // スタートインデックスに10をたして呼ぶ
                    startIndexCnt = startIndexCnt + 10;
                    getWeatherInfo(GeoModel.knownLatitude, GeoModel.knownLongitude, RemoteService.WEATHER_API_KEY, startIndexCnt);
                }
            }
        });

    }
}
