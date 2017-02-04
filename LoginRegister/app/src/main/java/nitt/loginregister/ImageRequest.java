package nitt.loginregister;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by APRATIM on 30-01-2017.
 */

public class ImageRequest extends StringRequest {
    private static final String IMAGE_REQUEST_URL = "http://silentpadoda.16mb.com/image.php";
    private Map<String, String> params;

    public ImageRequest(String username, String result, String trainName, String coach, Response.Listener<String> listener) {
        super(Method.POST, IMAGE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        Log.d("paaaaaaaaaaaaad",username);
        params.put("username", username);
        params.put("result", result);
        params.put("trainname", trainName);
        params.put("coach", coach);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
