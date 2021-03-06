package com.qhshef.counselor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qhshe on 2017-09-03.
 */

public class DeleteRequest extends StringRequest {

    final static private String URL = "http://gguoops.cafe24.com/Delete_coun.php";
    private Map<String, String> parameters;

    public DeleteRequest(String resvDate, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("resvDate", resvDate);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }

}
