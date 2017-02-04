package nitt.qrcodescanner;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String qrString,train,coach;
    private Button buttonScan;
    private TextView textViewName, textViewAddress;
    IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrScan = new IntentIntegrator(this);

        buttonScan = (Button) findViewById(R.id.buttonScan);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrScan.initiateScan();
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    qrString = result.getContents();
                    Toast.makeText(this, qrString, Toast.LENGTH_LONG).show();
                    train = qrString.split(":")[0];
                    coach = qrString.split(":")[1];

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.d("response",response);
                                JSONObject jsonResponse = new JSONObject(response);
                                Log.d("json",jsonResponse.toString());
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    Toast.makeText(MainActivity.this, "Have a safe and happy journey!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                                    intent.setPackage("com.android.chrome");
                                    intent.setData(Uri.parse("http://192.168.43.62/H"));
                                    startActivity(intent);

                                } else {
                                    /*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Register Failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();*/
                                    Toast.makeText(MainActivity.this,"Not verified",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };


                    Request request = new Request(train, coach, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(request);
                    //converting the data to json
                    //JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    //textViewName.setText(obj.getString("name"));
                    //textViewAddress.setText(obj.getString("address"));
                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public class check extends AsyncTask<Void,Void,Void> {


        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
}
