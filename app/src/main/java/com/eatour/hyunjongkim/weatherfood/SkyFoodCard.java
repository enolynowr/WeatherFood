package com.eatour.hyunjongkim.weatherfood;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eatour.hyunjongkim.weatherfood.model.item.ItemsImageItem;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.sky_food_card_view)
public class SkyFoodCard {

    @View(R.id.foodImageView)
    private ImageView profileImageView;
    @View(R.id.wci_icon)
    private ImageView wciIconIv;
    @View(R.id.btn_detail)
    private TextView tv;
    private String wciIconUrl;
    private ItemsImageItem itemsImageItem;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public SkyFoodCard(Context context, ItemsImageItem _itemsImageItem, SwipePlaceHolderView swipeView, String _wciIconUrl) {
        mContext = context;
        itemsImageItem = _itemsImageItem;
        mSwipeView = swipeView;
        wciIconUrl = _wciIconUrl;
    }

    @Resolve
    private void onResolved() {
        Glide.with(mContext).load(itemsImageItem.getLink()).into(profileImageView);
        Glide.with(mContext).load(wciIconUrl).into(wciIconIv);
        tv.setText(itemsImageItem.getImageImageItems().getContextLink());
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("EVENT", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d("EVENT", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("EVENT", "onSwipeOutState");
    }
}
