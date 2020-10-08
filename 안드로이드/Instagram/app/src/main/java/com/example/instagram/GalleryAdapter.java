package com.example.instagram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.navigation.AddFragment;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private ArrayList<String> mDataset;
    private Activity activity;
    private Intent intent;
    private Context context;
    private int clickposition = -1;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView layout;
        public MyViewHolder(CardView l) {
            super(l);
            layout = l;
        }
    }

    public GalleryAdapter(Activity activity, ArrayList<String> myDataset , Context context) {
        this.activity = activity;
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public GalleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        CardView cardView = holder.layout.findViewById(R.id.mainCardview);
        ImageView cardviewImage = holder.layout.findViewById(R.id.cardviewImage);

        Glide.with(activity).load(mDataset.get(position)).override(300).into(cardviewImage);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView fragment_ImageView = (ImageView) activity.findViewById(R.id.fragment_ImageView);
                Glide.with(activity).load(mDataset.get(holder.getAdapterPosition())).override(800).into(fragment_ImageView);
                ((MainActivity)context).setimgurl(mDataset.get(holder.getAdapterPosition()));
                //intent = new Intent(activity, AddPhotoActivity.class);
                //intent.putExtra("imgId", mDataset.get(holder.getAdapterPosition()));
                //clickposition = holder.getAdapterPosition();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
