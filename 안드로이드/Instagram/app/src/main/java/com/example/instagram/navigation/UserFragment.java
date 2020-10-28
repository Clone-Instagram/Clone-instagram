package com.example.instagram.navigation;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagram.MainData;
import com.example.instagram.MainData2;
import com.example.instagram.R;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    public static ArrayList<MainData2> arrayList2;
//    private UserFragment.RecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    ImageView profileImage2;
    TextView profileNickname;
    String nick;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_profile, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.mypost);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList2 = new ArrayList<>();
    /*  mAdapter = new UserFragment.RecyclerViewAdapter(arrayList2);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();*/

        final int clo = 4;

        profileImage2 = (ImageView) view.findViewById(R.id.profile_Image2);
        profileImage2.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage2.setClipToOutline(true);

        profileNickname = (TextView) view.findViewById(R.id.profile_nickname);
        nick = getArguments().getString("nickname");
        System.out.println("닉네임2" + nick);
       profileNickname.setText(nick);

        return view;
    }

   /* public static class RecyclerViewAdapter extends RecyclerView.Adapter<UserFragment.RecyclerViewAdapter.ViewHolder> {
        private ArrayList<MainData> arrayList;

        public RecyclerViewAdapter(ArrayList<MainData> arrayList) {
            this.arrayList = arrayList;
        }

        @NonNull
        @Override
        public UserFragment.RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview2,parent,false);
            UserFragment.RecyclerViewAdapter.ViewHolder viewHolder = new UserFragment.RecyclerViewAdapter.ViewHolder(cardView);
            return viewHolder;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public CardView layout;
            public ImageView cardviewImage2;

            public ViewHolder(CardView l) {
                super(l);
                layout = l;
                this.cardviewImage2 = (ImageView) itemView.findViewById(R.id.cardviewImage2);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull UserFragment.RecyclerViewAdapter.ViewHolder holder, int position) {
            Log.i("vhwltus", ""+position);
            CardView cardView = holder.layout.findViewById(R.id.mainCardview2);
            ImageView cardviewImage2 = holder.layout.findViewById(R.id.cardviewImage2);
            holder.cardviewImage2.setImageBitmap(arrayList2.get(0).getMyPost()[position]);
        }

        @Override
        public int getItemCount() {
            // return arrayList.size();
            if(arrayList2.size() != 0)
                return (null !=arrayList2.get(0).getMyPost() ? arrayList2.get(0).getMyPost().length : 0);
            else return 0;
        }
    }*/
}