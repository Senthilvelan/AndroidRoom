package com.sen.cooey.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sen.cooey.R;
import com.sen.cooey.network.IResult;
import com.sen.cooey.network.VolleyService;
import com.sen.cooey.storage.SessionManager;
import com.sen.cooey.storage.db.table.CardDataEntity;
import com.sen.cooey.ui.fragment.LikesFragment;
import com.sen.cooey.ui.fragment.MainFragment;
import com.sen.cooey.ui.fragment.ProfileFragment;
import com.sen.cooey.utils.AppConstants;
import com.sen.cooey.utils.CommonUtilities;
import com.sen.cooey.utils.NetworkUtils;
import com.sen.cooey.widget.NeumorphCardView;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();

    //UI
    private NeumorphCardView opt_home;
    private NeumorphCardView opt_like;
    private NeumorphCardView opt_profile;
    private ContentLoadingProgressBar progressBar;

    //Network
    private VolleyService mVolleyService;
    private IResult mResultCallback = null;
    private SessionManager sessionManager = null;


    public static HashMap<String, CardDataEntity> hmCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initilize();
        onClicks();
    }


    private void initilize() {
        opt_home = findViewById(R.id.opt_home);
        opt_like = findViewById(R.id.opt_like);
        opt_profile = findViewById(R.id.opt_profile);
        progressBar = findViewById(R.id.progressBar);
        sessionManager = SessionManager.getInstance(MainActivity.this);

        CommonUtilities.loadSnack(MainActivity.this, opt_home, R.string.instruction);

        if (NetworkUtils.isNetworkConnected(MainActivity.this)) {
            getVolley();
        } else {
            CommonUtilities.loadSnack(MainActivity.this, opt_home, R.string.instruction4);
            //load offline data from SP as of now
            processData();
        }

    }

    private void onClicks() {
        opt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMainFragment();
            }
        });


        opt_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikesFragment();
            }
        });


        opt_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProfileFragment();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void onMainFragment() {
        MainFragment mainFragment = (MainFragment) MainFragment.newInstance();
        FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainFragment, MainFragment.class.getName());
        transaction.addToBackStack(MainFragment.class.getName());
        transaction.commit();
    }


    public void onProfileFragment() {
        ProfileFragment mainFragment = (ProfileFragment) ProfileFragment.newInstance();
        FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, mainFragment, ProfileFragment.class.getName());
        transaction.addToBackStack(ProfileFragment.class.getName());
        transaction.commit();
    }

    public void onLikesFragment() {
        LikesFragment likesFragment = (LikesFragment) LikesFragment.newInstance();
        FragmentTransaction transaction = (FragmentTransaction) getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, likesFragment, LikesFragment.class.getName());
        transaction.addToBackStack(LikesFragment.class.getName());
        transaction.commit();
    }


    private void getVolley() {

        progressBar.setVisibility(View.VISIBLE);
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, MainActivity.this);
        Map<String, String> params = new HashMap<String, String>();
        mVolleyService.postReqStringRes(AppConstants.URL_CARDS_REQ, Request.Method.GET,
                AppConstants.URL_CARDS + "", params, true);

    }

    void initVolleyCallback() {
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                progressBar.setVisibility(View.GONE);

                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                sessionManager.putLAST_DATA(response);

                processData();


            }


            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.e(TAG, "Volley requester " + requestType);
                Log.e(TAG, "Volley error " + error);
                progressBar.setVisibility(View.GONE);
                CommonUtilities.loadSnack(MainActivity.this, opt_home, R.string.error);
            }
        };
    }//eof initVolleyCallback()

    private void processData() {
        String response = sessionManager.getLAST_DATA();

        if (response == null) {
            return;
        }

        if (response.length() <= 0) {
            return;
        }

        try {
            JSONArray jsonArray = new JSONArray(response);
            Gson gson = new Gson();
            Type listType = new TypeToken<List<CardDataEntity>>() {
            }.getType();
            ArrayList<CardDataEntity> alCards = gson.fromJson(jsonArray.toString(), listType);
            hmCard = filterByGender(alCards);
            onMainFragment();


        } catch (Exception e) {
            Log.e(TAG, "Exception :   " + e.getMessage());
        }

    }

    private HashMap<String, CardDataEntity> filterByGender(ArrayList<CardDataEntity> alCards) {

        if (alCards == null) {
            return null;
        }

        HashMap<String, CardDataEntity> hmCard = new HashMap<>();
        String search_text = "m";
        int mygender = sessionManager.getLOGGED_IN();
        if (mygender == 1) {
            search_text = "f";
        }
        for (int i = 0; i < alCards.size(); i++) {
            if (!alCards.get(i).getGender().startsWith(search_text)) {
                hmCard.put(alCards.get(i).get_id(), alCards.get(i));
            }
        }//eof for

        return hmCard;

    }

}
