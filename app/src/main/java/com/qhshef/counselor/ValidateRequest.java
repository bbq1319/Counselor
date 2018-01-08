package com.qhshef.counselor;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qhshe on 2017-09-19.
 */

public class ValidateRequest extends StringRequest {

    final static private String URL = "http://gguoops.cafe24.com/UserValidate.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userSno, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userSno", userSno);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
