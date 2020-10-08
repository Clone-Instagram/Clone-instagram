package com.example.instagram.navigation;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.AddPhotoActivity;
import com.example.instagram.GalleryAdapter;
import com.example.instagram.MainActivity;
import com.example.instagram.R;

import java.util.ArrayList;

public class AddFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter1;
    private Button next3;
    private ImageView fragment_ImageView;
    private String imgurl;
    ImageButton ex;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add, container, false);

/*        ex = (ImageButton) view.findViewById(R.id.ex);

        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DetailViewFragment.class);
                startActivity(intent);
            }
        });*/

        final int clo = 4;

        next3 = (Button) view.findViewById(R.id.next3);
        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPhotoActivity.class);
                Log.i("확인", ""+((MainActivity)getActivity()).getimgurl());
                intent.putExtra("imgId", ((MainActivity)getActivity()).getimgurl());
                startActivity(intent);
            }
        });

        fragment_ImageView = (ImageView) view.findViewById(R.id.fragment_ImageView);
        fragment_ImageView.setImageResource(R.drawable.camera);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_Rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), clo));

        mAdapter1 = new GalleryAdapter(getActivity(), getImagesPath(getActivity()),getContext());
        recyclerView.setAdapter(mAdapter1);
        return view;
    }

    @NonNull
    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor;
        int column_data, column_name;
        String pathImg = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {
                MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        cursor = activity.getContentResolver().query(uri, projection, null, null ,null);
        column_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        while(cursor.moveToNext()) {
            pathImg = cursor.getString(column_data);
            list.add(pathImg);
        }
        return list;
    }
}