package nitt.loginregister;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class UserAreaActivity extends AppCompatActivity {
    ImageView imageView;
    public final static int QRcodeWidth = 500 ;
    Bitmap bitmap;
    static String EditTextValue;
    ArrayList<String> trainname;
    ArrayList<String> coach;
    ArrayAdapter<String> adapter;
    static String encodedImage;
    String username, trainName, Coach;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        trainname=new ArrayList<String>();
        coach = new ArrayList<String>();
        trainname.add(0,"ROCKFORT EXPRESS");
        trainname.add(1,"MANGLORE EXPRESS");
        trainname.add(2,"THIRUKKURAL EXPRESS");
        coach.add(0,"SLEEPER");
        coach.add(1,"AC");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        adapter = new ArrayAdapter<String>(this,R.layout.spinner,trainname);
        final Spinner spinner=(Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,R.layout.spinner,coach);
        final Spinner spinner2=(Spinner) findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trainName = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                trainName = spinner.getItemAtPosition(0).toString();
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Coach = spinner2.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Coach = spinner2.getItemAtPosition(0).toString();
            }
        });


        imageView = (ImageView) findViewById(R.id.imageView);
        TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etAge = (EditText) findViewById(R.id.etAge);

        // Display user details
        String message = name + " Welcome";
        tvWelcomeMsg.setText(message);
        etUsername.setText(username);
        etAge.setText(age + "");
        EditTextValue = etUsername.getText().toString();
    }
    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public class encode extends AsyncTask<Bitmap , Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            Bitmap bmp = null;
            try {
                bmp = TextToImageEncode(trainName+":"+Coach);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            Log.d("length",""+encodedImage.length());
            return encodedImage;
            //    return null;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            if(result!=null){
                Log.d("image",result);
                byte[] decodeimg=Base64.decode(result,Base64.DEFAULT);
                Bitmap decodebyte = BitmapFactory.decodeByteArray(decodeimg,0,decodeimg.length);
                imageView.setImageBitmap(decodebyte);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        Button b=(Button)menu.findItem(R.id.generate).getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.generate:
           //     EditTextValue = etUsername.getText().toString();
                Log.d("generate",EditTextValue);

                    //bitmap = TextToImageEncode(trainName+Coach);
                    //String encimg = getStringImage(bitmap);
                    encode e=new encode();
                    e.execute();
                    //Log.d("image",encimg);
                    //imageView.setImageBitmap(bitmap);


                break;
            case R.id.save:
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("response",response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            Log.d("blah",jsonResponse.toString());
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
                                builder.setMessage("Success").create().show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserAreaActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ImageRequest imageRequest = new ImageRequest(username, encodedImage, trainName, Coach,responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserAreaActivity.this);
                queue.add(imageRequest);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
