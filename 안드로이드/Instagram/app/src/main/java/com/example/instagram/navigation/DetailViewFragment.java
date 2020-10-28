package com.example.instagram.navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.instagram.MainData;
import com.example.instagram.R;
//import com.example.instagram.ViewpagerAdapter;

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
    Bitmap[] bmImg1 = new Bitmap[2];
    String name;
    int mainDataLength=0;
    String[] arrayID;
    int[] arrayPostID;
    String[] arrayNickname;
    String[] arrayContent;
    int[] arrayImages;
    String[] arrayUploadDate;
    Bitmap[] arrImagers2;
//    ViewpagerAdapter adapter;

    int i;

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

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

           /* ViewPager viewPager = (ViewPager) getView().findViewById(R.id.post_image);
            adapter = new ViewpagerAdapter(getContext(),bmImg1);
            viewPager.setAdapter((PagerAdapter) adapter);*/

        //번들 안의 텍스트 불러오기
        name = getArguments().getString("name");

        System.out.println(name + "^^^^");
        String[] imgUrl2 = new String[2];
        new JSONTask().execute("http://13.125.61.188:8080/android_main");//AsyncTask 시작시킴
        for(int i=1; i<=2; i++){
            imgUrl2[i-1] = "http://13.125.61.188:8080/data/" + i + "/" + 1 + ".jpg";
            task2 = new MainPictureAsyncTask();

        }
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
                    Log.d("check","log "+arrayID[i]+arrayContent[i]+arrayNickname[i]+arrayPostID[i]+arrayImages[i]+arrayUploadDate[i]);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class MainPictureAsyncTask extends AsyncTask<String[], Integer, Bitmap[]> {

        @Override
        protected Bitmap[] doInBackground(String[]... urls) {
            // TODO Auto-generated method stub

            try{
                URL myFileUrl[] = new URL[2];
                HttpURLConnection[] conn = new HttpURLConnection[2];
                InputStream[] is = new InputStream[2];
                for(int i=0; i<2; i++){
                    myFileUrl[i] = new URL(urls[0][i]);
                    conn[i] = (HttpURLConnection)myFileUrl[i].openConnection();
                    conn[i].setDoInput(true);
                    conn[i].connect();
                    is[i] = conn[i].getInputStream();
                    bmImg1[i] = BitmapFactory.decodeStream(is[i]);
                }

                //bmImg1 = new Bitmap[]{ BitmapFactory.decodeStream(is)};

            }catch(IOException e){
                e.printStackTrace();
            }
            return bmImg1;
        }

        protected void onPostExecute(Bitmap[] img){

//            Log.d("image","이미지 : "+ arrayImages[0]);
            // post_image.setImageBitmap(bmImg1);
            Log.i(" sss","arrayNickname:   "+arrayNickname[0]);
            for(int i=0; i<2; i++)
            {
                MainData mainData = new MainData(bmImg1, arrayNickname, arrayContent, arrayUploadDate);
                Log.i(" sss", String.valueOf(bmImg1));
                arrayList.add(mainData);
                mAdapter.notifyDataSetChanged();
            }

//            adapter.notifyDataSetChanged();
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
            Log.i("vhwltus", ""+position);
            holder.post_image.setImageBitmap(arrayList.get(0).getPost_image()[position]);
            holder.post_user_name.setText(arrayList.get(0).getPost_user_name()[position]);
            holder.post_text.setText(arrayList.get(0).getPost_text()[position]);
            holder.sysdata.setText(arrayList.get(0).getSysdata()[position]);
        }

        @Override
        public int getItemCount() {
            // return arrayList.size();
            if(arrayList.size() != 0)
                return (null !=arrayList.get(0).getPost_user_name() ? arrayList.get(0).getPost_user_name().length : 0);
            else return 0;
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