package com.sen.cooey.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipeDirectionalView;
import com.mindorks.placeholderview.Utils;
import com.sen.cooey.R;
import com.sen.cooey.adapter.SwipeCardItemView;
import com.sen.cooey.storage.SessionManager;
import com.sen.cooey.storage.db.table.CardDataEntity;
import com.sen.cooey.ui.MainActivity;
import com.sen.cooey.ui.fragment.interfaces.SwipeInterface;
import com.sen.cooey.utils.AppConstants;
import com.sen.cooey.utils.CommonUtilities;
import com.sen.cooey.viewmodel.MyViewModel;

import java.util.Map;


public class MainFragment extends Fragment implements SwipeInterface {

    private final String TAG = MainFragment.this.getClass().getSimpleName();

    View viewRoot;

    private MyViewModel myViewModel;

    private boolean likedPhoto;

    private SwipeDirectionalView cardsContainer;
    private ImageView imageViewUndo;


    public MainFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        MainFragment instance;
        instance = new MainFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (viewRoot == null) {
            viewRoot = inflater.inflate(R.layout.fragment_main, null);
            cardsContainer = viewRoot.findViewById(R.id.cardsContainer);
            imageViewUndo = viewRoot.findViewById(R.id.imageViewUndo);
            populateCardView();
        } else {
            return viewRoot;
        }


        imageViewUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (likedPhoto) {
                    CommonUtilities.loadSnack(getContext(), imageViewUndo, R.string.instruction3);
                    return;
                }

                SessionManager smsp = SessionManager.getInstance(getContext());
                int currentValue = smsp.getNUM_OF_UNDO();

                if (currentValue == 3) {
                    //Call Payment Gateway
                    CommonUtilities.loadSnack(getContext(), imageViewUndo, R.string.instruction2);

                } else {
                    cardsContainer.undoLastSwipe();
                    smsp.putNUM_OF_UNDO(currentValue + 1);
                }


            }
        });


        return viewRoot;
    }

    private void populateCardView() {
        cardsContainer.getBuilder()
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f))
                .setSwipeVerticalThreshold(Utils.dpToPx(0))
                .setSwipeHorizontalThreshold(Utils.dpToPx(70))
                .setIsUndoEnabled(true);

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        if (MainActivity.hmCard != null) {
            for (Map.Entry<String, CardDataEntity> entry : MainActivity.hmCard.entrySet()) {
                if (entry.getValue().getVisited() != AppConstants.VISITED) {
                    cardsContainer.addView(new SwipeCardItemView(getActivity(), entry.getValue(), this));
                }
            }//eof for loop
        }//eof if


    }

    @Override
    public void swipeTop(String id) {
        doUnlike(id);
    }


    @Override
    public void swipeBottom(String id) {
        doUnlike(id);
    }

    @Override
    public void swipeLeft(String id) {
        //LIKE save in DB
        CommonUtilities.loadSnack(getActivity(), cardsContainer, " LIKED ! ");
        CardDataEntity cardPojo = MainActivity.hmCard.get(id);
        myViewModel.insert(cardPojo);
        likedPhoto = true;

        cardPojo.setVisited(AppConstants.VISITED);

        MainActivity.hmCard.put(id, cardPojo);

    }

    @Override
    public void swipeRight(String id) {
        doUnlike(id);
    }

    private void doUnlike(String id) {
        //UNLIKE
        likedPhoto = false;
        CardDataEntity cardPojo = MainActivity.hmCard.get(id);
        cardPojo.setVisited(AppConstants.VISITED);
        MainActivity.hmCard.put(id, cardPojo);
    }


    @Override
    public void onDestroyView() {
        if (viewRoot.getParent() != null) {
            ((ViewGroup) viewRoot.getParent()).removeView(viewRoot);
        }
        super.onDestroyView();

    }


}