package com.qhshef.counselor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagermentActivity extends AppCompatActivity {

    private ListView listView;
    private UserListAdapter adapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managerment);
        final Intent intent = getIntent();

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        listView = (ListView) findViewById(R.id.listView2);
        userList = new ArrayList<User>();
        adapter = new UserListAdapter(getApplicationContext(), userList, this);
        listView.setAdapter(adapter);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String resvDate, userName, userPhone;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                resvDate = object.getString("resvDate");
                userName = object.getString("resvUserName");
                userPhone = object.getString("userPhone");
                User user = new User(resvDate, userName, userPhone);
                userList.add(user);
                count++;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
