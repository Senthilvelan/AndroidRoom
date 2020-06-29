package com.sen.cooey.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mindorks.placeholderview.Utils;
import com.sen.cooey.R;
import com.sen.cooey.adapter.LikesAdapter;
import com.sen.cooey.viewmodel.MyViewModel;
import com.sen.cooey.widget.GridSpacingItemDecoration;


public class LikesFragment extends Fragment {


    private MyViewModel myViewModel;
    androidx.recyclerview.widget.RecyclerView recycler;


    public LikesFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        LikesFragment instance;
        instance = new LikesFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_likes, null);
        recycler = v.findViewById(R.id.recycler);


        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(mLayoutManager);
        recycler.addItemDecoration(new GridSpacingItemDecoration(1, Utils.dpToPx(10), true));
        recycler.setItemAnimator(new DefaultItemAnimator());

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        myViewModel.getCardData().observe(getActivity(), likesList -> {
            // update UI
            LikesAdapter likesAdapter = new LikesAdapter(getActivity(), likesList);
            // Assign adapter to Recycler
            recycler.setAdapter(likesAdapter);
        });

        return v;
    }


}