package com.example.instagram.navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagram.MainData;
import com.example.instagram.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DetailViewFragment extends Fragment {

    public static ArrayList<MainData> arrayList;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    MainPictureAsyncTask task2;
    Bitmap bmImg1;
    String name;
    int mainDataLength=0;
    String[] arrayID;
    int[] arrayPostID;
    String[] arrayNickname;
    String[] arrayContent;
    int[] arrayImages;
    String[] arrayUploadDate;

    int i;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_detail, container, false);

            recyclerView = (RecyclerView) view.findViewById(R.id.detailviewfragment);
            linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            arrayList = new ArrayList<>();
            mAdapter = new RecyclerViewAdapter(arrayList);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            //번들 안의 텍스트 불러오기
            name = getArguments().getString("name");

            System.out.println(name + "^^^^");

            new JSONTask().execute("http://13.125.61.188:8080/android_main2");//AsyncTask 시작시킴

            String imgUrl2 = "http://13.125.61.188:8080/data/" + 1 + "/" + 1 + ".jpg";
            task2 = new MainPictureAsyncTask();
            task2.execute(imgUrl2);
            return view;

    }

    class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuffer buffer=null;
            try {

                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("name", name);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(urls[0]);
                    //연결을 함

                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송

                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌

                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    buffer = new StringBuffer();

                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                }
                catch (MalformedURLException e){
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return buffer.toString();  //서버로 부터 받은 값을 리턴해줌
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                Log.d("check","ddddddddddddddddddddddddddddddddddd "+result);

                JSONArray jarray = new JSONArray(result);
                mainDataLength = jarray.length();
                String id = null; //포스트를 올린 사람의 아이디
                String nickname = null; //포스트를 올린 사람의 닉네임
                int post_id; //
                int images;
                String uploadDate = null;
                String contents = null;

                arrayID = new String[jarray.length()];
                arrayPostID = new int[jarray.length()];
                arrayNickname = new String[jarray.length()];
                arrayContent = new String[jarray.length()];
                arrayImages = new int[jarray.length()];
                arrayUploadDate = new String[jarray.length()];

                for (i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    id = jObject.optString("id");
                    nickname = jObject.optString("nickname");
                    contents = jObject.optString("content");
                    post_id = jObject.optInt("post_id");
                    images = jObject.optInt("images");
                    uploadDate = jObject.optString("upload_date");

                    arrayID[i] = id;
                    arrayContent[i] = contents;
                    arrayNickname[i] = nickname;
                    arrayPostID[i] = post_id; // 어떠한 포스트 아이디
                    arrayImages[i] = images; // 포스트에 들어갈 이미지 개수 ex)3 => 1.jpg, 2.jpg, 3.jpg
                    arrayUploadDate[i] = uploadDate;
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class MainPictureAsyncTask extends AsyncTask<String, Integer,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            // TODO Auto-generated method stub
            try{
                URL myFileUrl = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection)myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();

                bmImg1 = BitmapFactory.decodeStream(is);

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg1;
        }

        protected void onPostExecute(Bitmap img){

            Log.d("image","이미지 : "+ arrayImages[1]);
            // post_image.setImageBitmap(bmImg1);
            Log.i(" sss","arrayNickname:   "+arrayNickname[0]+arrayNickname[1]+arrayNickname[2]);

            MainData mainData = new MainData(bmImg1, arrayNickname[1], arrayContent[1], arrayUploadDate[1]);
            Log.i(" sss",mainData.getPost_user_name().toString());
            arrayList.add(mainData);
            mAdapter.notifyDataSetChanged();
        }
    }


    public static class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

            private ArrayList<MainData> arrayList;

        public RecyclerViewAdapter(ArrayList<MainData> arrayList) {
                this.arrayList = arrayList;
            }

            @NonNull
            @Override
            public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
                RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);
                return viewHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
                holder.post_image.setImageBitmap(arrayList.get(position).getPost_image());
                holder.post_user_name.setText(arrayList.get(position).getPost_user_name());
                holder.post_text.setText(arrayList.get(position).getPost_text());
                holder.sysdata.setText(arrayList.get(position).getSysdata());
        }

        @Override
        public int getItemCount() {

            // return arrayList.size();
            return (null !=arrayList ? arrayList.size() : 0);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            protected ImageView post_image;
            protected TextView post_user_name;
            protected TextView post_text;
            protected TextView sysdata;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.post_image = (ImageView) itemView.findViewById(R.id.post_image);
                this.post_user_name = (TextView) itemView.findViewById(R.id.post_user_name);
                this.post_text = (TextView) itemView.findViewById(R.id.post_text);
                this.sysdata = (TextView) itemView.findViewById(R.id.sysdata);
            }
        }
    }
}
