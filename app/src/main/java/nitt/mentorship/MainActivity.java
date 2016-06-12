package nitt.mentorship;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    Spinner profile;
    Button submit;
    Switch mySwitch;
    EditText editTextNumber;
    EditText editTextName;
    public static String NAME ="Name";
    public static int REQUEST_CODE = 1;
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        editName();
        editNumber();
        addItemsOnSpinner();
        operateSwitch();
        submit = (Button) findViewById(R.id.button);
        submit.setOnClickListener(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //Switch
    public void operateSwitch() {
        mySwitch = (Switch) findViewById(R.id.switch1);
        mySwitch.setChecked(true);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Toast.makeText(getApplicationContext(), "Mentorship needed", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Mentorship is not needed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Edit name
    public void editName() {
        editTextName = (EditText) findViewById(R.id.name);
        editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    String inputText = v.getText().toString();
                    Toast.makeText(getApplicationContext(), "Your name is: " + inputText, Toast.LENGTH_SHORT).show();
                }
                return handled;
            }
        });
    }

    //Edit Roll number
    public void editNumber() {
        editTextNumber = (EditText) findViewById(R.id.rollNo);
        editTextNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Integer num = Integer.parseInt(v.getText().toString());
                    Toast.makeText(getApplicationContext(), "Your RollNumber is: " + num, Toast.LENGTH_SHORT).show();
                }
                return handled;
            }
        });
    }

    //Spinner
    //add items in spinner dynamically
    public void addItemsOnSpinner() {
        profile = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("CSE");
        list.add("ECE");
        list.add("EEE");
        list.add("ICE");
        list.add("MECH");
        list.add("PROD");
        list.add("MME");
        list.add("ARCHI");
        list.add("CIVIL");
        list.add("CHEM");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile.setAdapter(dataAdapter);
    }

    //Intent
    public void onClick(View v){
        editTextName = (EditText) findViewById(R.id.name);
        editTextNumber = (EditText) findViewById(R.id.rollNo);
        CheckBox checkBoxes[] = new CheckBox[4];
        checkBoxes[0] = (CheckBox) findViewById(R.id.checkBox);
        checkBoxes[1] = (CheckBox) findViewById(R.id.checkBox2);
        checkBoxes[2] = (CheckBox) findViewById(R.id.checkBox3);
        checkBoxes[3] = (CheckBox) findViewById(R.id.checkBox4);

        boolean flag = false;
        for(CheckBox checkBox: checkBoxes){
            if(checkBox.isChecked()){
                flag = true;
                break;
            }
        }

        if(editTextName.getText().toString().length() == 0){
            editTextName.setError("This field is required");
            Toast.makeText(getApplicationContext(),"Please enter name", Toast.LENGTH_SHORT).show();
            editTextName.requestFocus();
        }
        else if(editTextNumber.getText().toString().length() == 0){
            editTextNumber.setError("This field is required");
            Toast.makeText(getApplicationContext(),"Please enter roll no",Toast.LENGTH_SHORT).show();
            editTextNumber.requestFocus();
        }
        else if(flag == false){
            Toast.makeText(getApplicationContext(),"Please choose a profile",Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent(this,ActivityC.class);
            i.putExtra(NAME, editTextName.getText().toString());
            startActivityForResult(i, REQUEST_CODE);
        }
    }

    //checkBox
    public void onCheckBoxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.checkBox:
                if(checked)
                    Toast.makeText(getApplicationContext(),"WebDev is selected",Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"WebDev is removed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkBox2:
                if(checked)
                    Toast.makeText(getApplicationContext(),"AppDev is selected",Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"AppDev is removed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkBox3:
                if(checked)
                    Toast.makeText(getApplicationContext(),"Tronix is selected",Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"Tronix is removed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkBox4:
                if(checked)
                    Toast.makeText(getApplicationContext(),"Algorithm is selected",Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"Algorithm is removed",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
