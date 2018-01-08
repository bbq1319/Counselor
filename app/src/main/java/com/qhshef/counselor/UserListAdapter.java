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

/**
 * Created by qhshe on 2017-09-02.
 */

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;
    private Activity parentActivity;

    public UserListAdapter(Context context, List<User> userList, Activity parentActivity) {
        this.context=context;
        this.userList=userList;
        this.parentActivity=parentActivity;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.user, null);
        final TextView resvDate = v.findViewById(R.id.resvDate);
        TextView userName = v.findViewById(R.id.userName);
        TextView userPhone = v.findViewById(R.id.userPhone);

        resvDate.setText(userList.get(i).getResvDate());
        userName.setText(userList.get(i).getUserName());
        userPhone.setText(userList.get(i).getUserPhone());

        v.setTag(userList.get(i).getResvDate());

        Button deleteButton = v.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
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
                                        if(formatDate.compareTo(userList.get(i).getResvDate()) < 0){

                                            // 삭제 원문 코드
                                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try{
                                                        JSONObject jsonResponse = new JSONObject(response);
                                                        boolean success = jsonResponse.getBoolean("success");
                                                        if(success) {
                                                            userList.remove(i);
                                                            notifyDataSetChanged();
                                                        }
                                                    }
                                                    catch (Exception e){
                                                        e.printStackTrace();
                                                    }
                                                }
                                            };
                                            DeleteRequest deleteRequest = new DeleteRequest(resvDate.getText().toString(), responseListener);
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
