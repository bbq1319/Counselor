package com.qhshef.counselor;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMypage extends Fragment {

    // 예약 내역 리스트뷰
    private ListView listView;
    private UserAdminListAdapter adapter;
    private List<UserAdmin> userAdminList;


    // 예약 내역 준비
    private static String TAG = "counselor_FragmentHome";

    private static final String TAG_JSON="gguoops";
    private static final String TAG_USERNAME = "resvUserName";
    private static final String TAG_COUNNAME = "counName";
    private static final String TAG_RESVDATE = "resvDate";

    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;


    public FragmentMypage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mypage, container, false);

        // TextView 설정
        TextView myName = v.findViewById(R.id.myName);
        TextView mySno = v.findViewById(R.id.mySno);
        TextView myPhone = v.findViewById(R.id.myPhone);
        TextView myCoun = v.findViewById(R.id.myCoun);
        TextView myLogout = v.findViewById(R.id.myLogout);
        TextView managerment = v.findViewById(R.id.managerment);
        TextView myTel = v.findViewById(R.id.myTel);

        Intent intent = getActivity().getIntent();

        String userName = intent.getStringExtra("userName");
        String userSno = intent.getStringExtra("userSno");
        String userPhone = intent.getStringExtra("userPhone");

        myName.setText(userName);
        mySno.setText(userSno);
        myPhone.setText(userPhone);

        // 로그아웃 버튼
        myLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Text_logout();
            }
        });

        // 회원관리 버튼
        if(!userSno.equals("admin")){
            managerment.setVisibility(View.GONE);
        }

        managerment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                new BackgroundManagerment().execute();
            }
        });

        // 예약내역 버튼
        myCoun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Background().execute();
            }
        });

        // 전화걸기 버튼
        myTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent telIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:041-731-3121"));
                startActivity(telIntent);
            }
        });

        return v;
    }


    // 로그아웃
    public void Text_logout(){
        new AlertDialog.Builder(getActivity())
                .setTitle("로그아웃 안내 창")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }



    // 회원관리
    class BackgroundManagerment extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://gguoops.cafe24.com/List_manager_coun.php";
        }

        @Override
        protected String doInBackground(Void... voids){
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            Intent intent = new Intent(getActivity(), ManagermentActivity.class);
            intent.putExtra("userList", result);
            getActivity().startActivity(intent);
        }
    }

    // 예약 내역
    class Background extends AsyncTask<Void, Void, String>{
        String tar;

        @Override
        protected void onPreExecute() {
            tar = "http://gguoops.cafe24.com/List_coun_myP.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(tar);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString();
            } catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result){
            Intent intent = getActivity().getIntent();
            String userName = intent.getStringExtra("userName");

            intent = new Intent(getActivity(), ResvPage.class);
            intent.putExtra("userList", result);
            intent.putExtra("name", userName);
            getActivity().startActivity(intent);
        }
    }


    /*
    // 예약내역
    private class BackgroundTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mJsonString = result;

            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for(int i=0;i<jsonArray.length();i++){

                    JSONObject item = jsonArray.getJSONObject(i);

                    String resvUserName = item.getString(TAG_USERNAME);
                    String counName = item.getString(TAG_COUNNAME);
                    String resvDate = item.getString(TAG_RESVDATE);

                    HashMap<String,String> hashMap = new HashMap<>();

                    hashMap.put(TAG_USERNAME, resvUserName);
                    hashMap.put(TAG_COUNNAME, counName);
                    hashMap.put(TAG_RESVDATE, resvDate);

                    Intent intent = getActivity().getIntent();
                    String userName = intent.getStringExtra("userName");

                    if(resvUserName.equals(userName)){
                        mArrayList.add(hashMap);
                    }

                    // 버튼 처리
                    LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.listview_item, null);
                    Button delButton = view.findViewById(R.id.delButton);

                    delButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println("123");
                            System.out.println("123");
                            System.out.println("123");
                        }
                    });

                }

                ListAdapter adapter = new SimpleAdapter(
                        getActivity(), mArrayList, R.layout.listview_item,
                        new String[]{TAG_COUNNAME,TAG_RESVDATE},
                        new int[]{R.id.textView1, textView2}
                );

                mlistView.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();

            } catch (Exception e) {

                e.printStackTrace();
                return null;
            }

        }

    }
    */



}
