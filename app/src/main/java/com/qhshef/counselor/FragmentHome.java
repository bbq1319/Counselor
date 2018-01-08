package com.qhshef.counselor;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    public FragmentHome() {
        // Required empty public constructor
    }

    private CalendarView mCalendarView;

    private static String TAG = "counselor_FragmentHome";
    private static final String TAG_JSON="gguoops";
    private static final String TAG_DATE = "resvDate";

    String mJsonString;
    String date = null;
    String clickDate = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_home, container, false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_notifications_black_24dp);

        mCalendarView = v.findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int i1, int i2) {
                String m, d;

                if(i1+1<10){
                    m = String.format("%02d", i1+1);
                }
                else {
                    m = String.format("%02d", i1+1);
                }

                if(i2<10){
                    d = String.format("%02d", i2);
                }
                else{
                    d = String.format("%02d", i2);
                }

                date = y + "/" + m + "/" + d;
                clickDate = y + "" + m + "" + d;

                // 주말 예약 비활성화 설정
                String day = "";
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, y);
                cal.set(Calendar.MONTH, i1);
                cal.set(Calendar.DATE, i2);
                int dayNum = cal.get(Calendar.DAY_OF_WEEK);
                switch (dayNum){
                    case 1:
                        day = "일";
                        break;
                    case 2:
                        day = "월";
                        break;
                    case 3:
                        day = "화";
                        break;
                    case 4:
                        day = "수";
                        break;
                    case 5:
                        day = "목";
                        break;
                    case 6:
                        day = "금";
                        break;
                    case 7:
                        day = "토";
                        break;
                }

                // 지난 날 비활성화
                if(Calendar.getInstance().get(Calendar.YEAR) >= y &&
                        Calendar.getInstance().get(Calendar.MONTH) >= i1 &&
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > i2){
                    //Toast.makeText(getActivity(), "지난 날은 예약할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    // 알림 창
                    builder.setTitle("경고창");
                    builder.setMessage("지난 날은 예약할 수 없습니다.")
                            .setCancelable(false)
                            .setNegativeButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    // 예약 불가
                    FragmentBottomNone fragmentHomeNone = new FragmentBottomNone();
                    FragmentManager manager = getFragmentManager();
                    Bundle bundle = new Bundle();
                    fragmentHomeNone.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.frameBottom, fragmentHomeNone).commit();

                }

                // 주말 비활성화
                else if(day.equals("토") || day.equals("일")){
                    //Toast.makeText(getActivity(), "주말은 예약할 수 없습니다.", Toast.LENGTH_SHORT).show();
                    // 알림 창
                    builder.setTitle("경고창");
                    builder.setMessage("주말은 예약할 수 없습니다.")
                            .setCancelable(false)
                            .setNegativeButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    // 예약 불가
                    FragmentBottomNone fragmentHomeNone = new FragmentBottomNone();
                    FragmentManager manager = getFragmentManager();
                    Bundle bundle = new Bundle();
                    fragmentHomeNone.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.frameBottom, fragmentHomeNone).commit();

                }

                // 그 외 활성화
                else {

                    FragmentHomeBottom fragmentHomeBottom = new FragmentHomeBottom();
                    Bundle bundle = new Bundle();

                    BackgroundTask task = new BackgroundTask();
                    task.execute("http://gguoops.cafe24.com/List_coun.php");

                    fragmentHomeBottom.setArguments(bundle);

                }

                /*
                FragmentReserv fragmentReserv = new FragmentReserv();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_home, fragmentReserv).commit();

                Intent intent = new Intent(getActivity(), FragmentHomeBottom.class);
                intent.putExtra("date", date);
                //startActivity(intent);
                */

            }
        });

        return v;

    }

    private class BackgroundTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mJsonString = result;

            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
                String resvDate;
                HashMap<String, String> hashMap = new HashMap<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    resvDate = item.getString(TAG_DATE);
                    hashMap.put("resvDate"+i, resvDate);
                }

                FragmentHomeBottom fragmentHomeBottom = new FragmentHomeBottom();
                FragmentManager manager = getFragmentManager();
                Bundle bundle = new Bundle();

                for(int i = 0; i < jsonArray.length(); i++){
                    bundle.putString("resvDate"+i, hashMap.get("resvDate"+i));
                }

                bundle.putInt("resvLength", jsonArray.length());
                bundle.putString("date", date);
                bundle.putString("clickDate", clickDate);

                fragmentHomeBottom.setArguments(bundle);
                manager.beginTransaction().replace(R.id.frameBottom, fragmentHomeBottom).commit();

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

}
