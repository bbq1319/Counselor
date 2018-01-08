package com.qhshef.counselor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qhshe on 2017-08-22.
 */

public class AddRequest extends StringRequest {

    final static private String URL = "http://gguoops.cafe24.com/ResvAdd.php";
    private Map<String, String> parameters;

    public AddRequest(String resvUserName, String userPhone, String counName, String resvDate, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("resvUserName", resvUserName);
        parameters.put("userPhone", userPhone);
        parameters.put("counName", counName);
        parameters.put("resvDate", resvDate);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
