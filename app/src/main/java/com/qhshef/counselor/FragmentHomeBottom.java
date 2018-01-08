package com.qhshef.counselor;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class FragmentHomeBottom extends Fragment implements View.OnClickListener {


    private int cnt;

    public FragmentHomeBottom() {
        // Required empty public constructor
    }

    private String coun1 = "심리검사";
    private String coun2 = "개인상담";
    private Button[] arr = new Button[8];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_home_bottom, container, false);

        Button button9 = v.findViewById(R.id.button9);
        Button button10 = v.findViewById(R.id.button10);
        Button button11 = v.findViewById(R.id.button11);
        Button button13 = v.findViewById(R.id.button13);
        Button button14 = v.findViewById(R.id.button14);
        Button button15 = v.findViewById(R.id.button15);
        Button button16 = v.findViewById(R.id.button16);
        Button button17 = v.findViewById(R.id.button17);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button13.setOnClickListener(this);
        button14.setOnClickListener(this);
        button15.setOnClickListener(this);
        button16.setOnClickListener(this);
        button17.setOnClickListener(this);

        // 시간 가져오기
        String date = getArguments().getString("date");
        TextView dateTextView = v.findViewById(R.id.dateTextView);
        dateTextView.setText(date);

        // 이미 예약되있는 상담 확인
        String resvDate;
        int resvLength = getArguments().getInt("resvLength");

        arr[0] = button9;
        arr[1] = button10;
        arr[2] = button11;
        arr[3] = button13;
        arr[4] = button14;
        arr[5] = button15;
        arr[6] = button16;
        arr[7] = button17;

        for(int i=0;i<arr.length;i++){
            String clickedDate = date + " " + arr[i].getTag() + ":00";

            for(int j=0;j<resvLength;j++){
                resvDate = getArguments().getString("resvDate"+j);
                if(clickedDate.equals(resvDate)){
                    arr[i].setEnabled(false);
                }
            }
        }

        return v;
        //return inflater.inflate(R.layout.fragment_home_bottom, container, false);
    }

    public void onClick(final View v){

        // 시간 변수 설정
        final String date = getArguments().getString("date");
        final String clickDate = getArguments().getString("clickDate");
        final String clickedDate = date + " " + v.getTag() + ":00";

        // 현재 시간 구하기
        long now = System.currentTimeMillis();
        Date nowTime = new Date(now);

        // 시간 포맷정하기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH");
        String formatDate = simpleDateFormat.format(nowTime);

        // 시간 비교하기
        int compare = formatDate.compareTo(clickDate+v.getTag());

        // 지난 시간 일경우
        if(compare >= 0){
            Toast.makeText(getActivity(), "지난 시간은 예약할 수 없습니다.", Toast.LENGTH_SHORT).show();
        }

        // 예약하기
        else if(compare < 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.create();
            builder.setTitle("종류 선택");
            builder.setIcon(R.drawable.ic_notifications_black_24dp);

            final String[] items = {coun1, coun2};
            final boolean[] checkedItems = {false, false};

            builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {

                    checkedItems[which] = isChecked;
                    cnt = 0;
                    for(int i=0; i<checkedItems.length; i++){
                        if(checkedItems[i]){
                            cnt++;
                        }
                    }

                    if(cnt > 1){
                        Toast.makeText(getActivity(), "1개만 선택가능합니다.", Toast.LENGTH_SHORT).show();
                        checkedItems[which]=false;
                        ((AlertDialog) dialogInterface).getListView().setItemChecked(which, false);
                    }

                }
            });

            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialog, int which) {

                    if(cnt == 0){
                        Toast.makeText(getActivity(), "예약에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }

                    else if (which == DialogInterface.BUTTON_POSITIVE){
                        String str = "";
                        int selecCount = 0;
                        for(int i=0; i<items.length; i++){
                            if(checkedItems[i]){
                                selecCount++;
                                if(selecCount<=1){
                                    str += items[i];
                                }
                            }
                        }
                        str = str.substring(0, str.length());

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setTitle("예약신청 확인");
                        alertDialogBuilder.setIcon(R.drawable.ic_notifications_black_24dp);
                        final String finalStr = str;
                        alertDialogBuilder.setMessage(str + "(으)로 예약하시겠습니까?")
                                .setCancelable(false)
                                .setPositiveButton("확인",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialogInterface, int id) {

                                                final Intent intent = getActivity().getIntent();
                                                String userName = intent.getStringExtra("userName");
                                                String userPhone = intent.getStringExtra("userPhone");

                                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try
                                                        {
                                                            JSONObject jsonResponse = new JSONObject(response);
                                                            boolean success = jsonResponse.getBoolean("success");
                                                            if(success){
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                builder.setMessage("예약이 완료되었습니다..")
                                                                        .setPositiveButton("확인", null)
                                                                        .create()
                                                                        .show();

                                                                v.setEnabled(false);
                                                            }
                                                            else
                                                            {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                builder.setMessage("예약에 실패하였습니다.")
                                                                        .setNegativeButton("다시 시도", null)
                                                                        .create()
                                                                        .show();
                                                            }
                                                        }
                                                        catch(JSONException e)
                                                        {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                };
                                                AddRequest addRequest = new AddRequest(userName, userPhone, finalStr, clickedDate, responseListener);
                                                RequestQueue queue = Volley.newRequestQueue(getActivity());
                                                queue.add(addRequest);

                                            /*
                                            String str = "";
                                            int selecCount = 0;
                                            for(int i=0; i<items.length; i++){
                                                if(checkedItems[i]){
                                                    selecCount++;
                                                    if(selecCount<=1){
                                                        str += items[i];
                                                    }
                                                }
                                            }
                                            str = str.substring(0, str.length());
                                            Toast.makeText(getActivity(), str + " 예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                            */

                                            }
                                        })
                                .setNegativeButton("취소",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int id) {
                                                dialogInterface.cancel();
                                            }
                                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();

                    }else if(which == DialogInterface.BUTTON_NEUTRAL){
                        dialog.dismiss();
                    }
                }
            };

            builder.setPositiveButton("선택", listener);
            builder.setNeutralButton("취소", listener);
            builder.setCancelable(true);
            builder.create().show();

        }

    }

}
