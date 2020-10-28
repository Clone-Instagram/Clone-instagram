package com.example.instagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewpagerAdapter extends PagerAdapter {

    private Context mContext;
    private Bitmap[] imageList;

    public ViewpagerAdapter(Context context, Bitmap[] imageList) {

        this.mContext = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.viewpager2, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.viewPager);

        imageView.setImageBitmap(imageList[position]);

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
