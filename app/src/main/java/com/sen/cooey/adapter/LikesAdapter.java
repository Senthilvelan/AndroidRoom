package com.sen.cooey.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sen.cooey.R;
import com.sen.cooey.storage.db.table.CardDataEntity;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.MyViewHolder> {

    private Context mContext;
    private List<CardDataEntity> alCardData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewEmail;
        public TextView textViewColor;
        public ImageView imageViewProfile;
        public LinearLayout inflaterLikeMain;

        public MyViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewName);
            textViewEmail = (TextView) view.findViewById(R.id.textViewEmail);
            textViewColor = (TextView) view.findViewById(R.id.textViewColor);
            imageViewProfile = view.findViewById(R.id.imageViewProfile);
            inflaterLikeMain = view.findViewById(R.id.inflaterLikeMain);
        }
    }


    public LikesAdapter(Context mContext, List<CardDataEntity> alCardData) {
        this.mContext = mContext;
        this.alCardData = alCardData;
    }


    @Override
    public LikesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inflater_likes, parent, false);
        return new LikesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LikesAdapter.MyViewHolder holder, int position) {
        final CardDataEntity alCardDataObj = alCardData.get(position);

        holder.textViewName.setText("" + alCardDataObj.getName() + ", " + alCardDataObj.getAge() + ",  " + alCardDataObj.getGender());
        holder.textViewEmail.setText("" + alCardDataObj.getEmail());
        holder.textViewColor.setText("Favorite color : " + alCardDataObj.getFavoriteColor());

        String imageURL = alCardDataObj.getPicture();

        //Image downloading took more time, so we increased  the timedout in glide
        if (imageURL != null) {

            RequestOptions requestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_like)
                    .placeholder(R.drawable.ic_like);

            Glide.with(mContext)
                    .load(imageURL)
//                .thumbnail(0.25f)
                    .transition(withCrossFade(1000))
                    .apply(requestOptions)
                    .into(holder.imageViewProfile);
        }

    }

    @Override
    public int getItemCount() {
        return alCardData.size();
    }

}
