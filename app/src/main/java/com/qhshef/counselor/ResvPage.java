package com.qhshef.counselor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResvPage extends Activity {

    private ListView listView;
    private UserAdminListAdapter adapter;
    private List<UserAdmin> userAdminList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resv_page);
        Intent intent = getIntent();

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.counList);
        userAdminList = new ArrayList<UserAdmin>();
        adapter = new UserAdminListAdapter(getApplicationContext(), userAdminList, this);
        listView.setAdapter(adapter);

        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("userList"));
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String resvDate, counName, resvUserName;
            while(count < jsonArray.length()){
                JSONObject object = jsonArray.getJSONObject(count);
                resvDate = object.getString("resvDate");
                counName = object.getString("counName");
                resvUserName = object.getString("resvUserName");

                UserAdmin user = new UserAdmin(resvDate, counName);

                String userName = intent.getStringExtra("name");
                System.out.println(resvUserName);
                System.out.println(userName);

                if(resvUserName.equals(userName)){
                    userAdminList.add(user);
                }
                count++;
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
