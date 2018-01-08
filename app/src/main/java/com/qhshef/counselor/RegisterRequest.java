package com.qhshef.counselor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qhshe on 2017-08-22.
 */

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://gguoops.cafe24.com/Register_count.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userSno, String userPassword, String userName, String userPhone, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userSno", userSno);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userPhone", userPhone);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
