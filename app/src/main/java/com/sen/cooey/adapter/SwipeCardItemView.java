package com.sen.cooey.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mindorks.placeholderview.SwipeDirection;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeInDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutDirectional;
import com.mindorks.placeholderview.annotations.swipe.SwipeView;
import com.mindorks.placeholderview.annotations.swipe.SwipingDirection;
import com.sen.cooey.R;
import com.sen.cooey.storage.db.table.CardDataEntity;
import com.sen.cooey.ui.fragment.MainFragment;

@Layout(R.layout.inflater_main)
public class SwipeCardItemView {
    private static final String TAG = "SwipeCardItemView";
    private static final int IMAGE_TIMEED_OUT = 200000;

    @View(R.id.imageViewProfile)
    ImageView imageViewProfile;

    @View(R.id.textViewName)
    TextView textViewName;

    @View(R.id.textViewEmail)
    TextView textViewEmail;

    @View(R.id.textViewColor)
    TextView textViewColor;

    @SwipeView
    android.view.View mSwipeView;

    private Context mContext;
    private CardDataEntity cardPojo;
    MainFragment mainFragment;

    public SwipeCardItemView(Context context, CardDataEntity cardPojo, MainFragment mainFragment) {
        this.mContext = context;
        this.cardPojo = cardPojo;
        this.mainFragment = mainFragment;
    }

    @Resolve
    public void onResolved() {

        if (cardPojo == null) {
            return;
        }
        String imageURL = cardPojo.getPicture().trim();

        if (imageURL != null) {
            //Image downloading took more time, so we increased  the timedout in glide

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_like)
                    .timeout(IMAGE_TIMEED_OUT)
                    .placeholder(R.drawable.ic_like);

            Glide.with(mContext)
                    .load(imageURL)
//                .transition(withCrossFade(750))
                    .apply(requestOptions)
                    .into(imageViewProfile);
        }


        textViewName.setText(cardPojo.getName() + ", " + cardPojo.getAge() + ",  " + cardPojo.getGender());
        textViewEmail.setText("" + cardPojo.getEmail());
        textViewColor.setText("Favorite color : " + cardPojo.getFavoriteColor());

        mSwipeView.setAlpha(1);
    }


    @Click(R.id.imageViewProfile)
    public void onClick() {
        Log.d(TAG, "profileImageView click");
    }

    @SwipeOutDirectional
    public void onSwipeOutDirectional(SwipeDirection direction) {
        Log.d(TAG, "SwipeOutDirectional " + direction.name());


        if (direction.getDirection() == SwipeDirection.TOP.getDirection()
                || direction.getDirection() == SwipeDirection.LEFT_TOP.getDirection()) {
            //LEFT
            mainFragment.swipeLeft(cardPojo.get_id());
        } else if (direction.getDirection() == SwipeDirection.BOTTOM.getDirection()
                || direction.getDirection() == SwipeDirection.LEFT_BOTTOM.getDirection()
                || direction.getDirection() == SwipeDirection.LEFT_TOP.getDirection()) {
            //LEFT
            mainFragment.swipeLeft(cardPojo.get_id());
        } else if (direction.getDirection() == SwipeDirection.LEFT.getDirection()) {
            //LEFT
            mainFragment.swipeLeft(cardPojo.get_id());
        } else if (direction.getDirection() == SwipeDirection.RIGHT.getDirection() || direction.getDirection() == SwipeDirection.RIGHT_TOP.getDirection()) {
            mainFragment.swipeRight(cardPojo.get_id());
        }

    }

    @SwipeCancelState
    public void onSwipeCancelState() {
        Log.d(TAG, "onSwipeCancelState");
        mSwipeView.setAlpha(1);
    }

    @SwipeInDirectional
    public void onSwipeInDirectional(SwipeDirection direction) {
        Log.d(TAG, "SwipeInDirectional " + direction.name());
    }

    @SwipingDirection
    public void onSwipingDirection(SwipeDirection direction) {
        Log.d(TAG, "SwipingDirection " + direction.name());
    }


}
