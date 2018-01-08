package com.qhshef.counselor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class UserAdminListAdapter extends BaseAdapter{

    private Context context;
    private List<UserAdmin> userAdminList;
    private Activity parentActivity;

    public UserAdminListAdapter(Context context, List<UserAdmin> userAdminList, Activity parentActivity) {
        this.context = context;
        this.userAdminList = userAdminList;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return userAdminList.size();
    }

    @Override
    public Object getItem(int i) {
        return userAdminList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, final View view, final ViewGroup viewGroup) {
        final View v = View.inflate(context, R.layout.listview_item, null);
        final TextView textView1 = v.findViewById(R.id.textView1);
        final TextView textView2 = v.findViewById(R.id.textView2);

        textView1.setText(userAdminList.get(i).getCounName());
        textView2.setText(userAdminList.get(i).getResvDate());

        v.setTag(userAdminList.get(i).getResvDate());

        // 삭제버튼
        final Button delButton = v.findViewById(R.id.delButton);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 경고창
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parentActivity, R.style.MyAlertDialogStyle);
                alertDialogBuilder.setTitle("상담취소 안내창");

                alertDialogBuilder.setMessage("정말 취소하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int id) {

                                        // 현재 시간 구하기
                                        long now = System.currentTimeMillis();
                                        Date nowTime = new Date(now);

                                        // 시간 포맷정하기
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/dd HH:00");
                                        String formatDate = simpleDateFormat.format(nowTime);

                                        // 날짜 비교
                                        // 예약날짜가 지나면 취소 불가능
                                        // getCounName 변수는 getResvDate 변수랑 값이 바뀜
                                        // 고로 getCounName은 예약날짜를 의미하고 getResvDate는 상담이름을 의미함
                                        if(formatDate.compareTo(userAdminList.get(i).getCounName()) < 0){

                                            // 삭제 원문 코드
                                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try{
                                                        JSONObject jsonResponse = new JSONObject(response);
                                                        boolean success = jsonResponse.getBoolean("success");
                                                        if(success) {
                                                            userAdminList.remove(i);
                                                            notifyDataSetChanged();
                                                        }
                                                    }
                                                    catch (Exception e){
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            DeleteRequest deleteRequest = new DeleteRequest(textView1.getText().toString(), responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(parentActivity);
                                            queue.add(deleteRequest);

                                            AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                            builder.setMessage("취소가 완료되었습니다.")
                                                    .setPositiveButton("확인", null)
                                                    .create()
                                                    .show();

                                        } else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
                                            builder.setMessage("이미 지난 시간의 상담은 취소할 수 없습니다.")
                                                    .setNegativeButton("확인", null)
                                                    .create()
                                                    .show();
                                        }

                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                // 다이얼로그 생성
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        return v;

    }

}
