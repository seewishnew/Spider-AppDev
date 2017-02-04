package nitt.qrcodescanner;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by APRATIM on 31-01-2017.
 */

public class Request extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://silentpadoda.16mb.com/check.php";
    private Map<String, String> params;

    public Request(String trainname, String coach, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("trainname", trainname);
        Log.d("paaaaaaaaaaaaad",trainname);
        params.put("coach", coach);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
