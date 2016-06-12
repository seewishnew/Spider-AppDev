package nitt.mentorship;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ActivityC extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_c);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String name = getIntent().getStringExtra(MainActivity.NAME);

        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText("Thank You " + name + " for your response");

        TextView timeStamp = (TextView) findViewById(R.id.textView3);
        Long myTime = System.currentTimeMillis()/1000;
        String time = myTime.toString();
        timeStamp.setText(time);
    }

}
