package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private Button btnSave, btnGetAllData, btnNextActivity;
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private TextView txtGetData;
    private String allKickBoxers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        btnSave = findViewById(R.id.btnSave);
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnNextActivity = findViewById(R.id.btnNextActivity);

        btnSave.setOnClickListener(SignUp.this);
        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("qBkyNvFvqY", new GetCallback<ParseObject>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if(object != null && e == null){
                            txtGetData.setText("name: " + object.get("name") + "\n" + "punch speed: " + object.get("punch_speed")
                            + "\n" + "punch power: " + object.get("punch_power") + "\n" + "kick speed: " + object.get("kick_speed")
                            + "\n" + "kick power: " + object.get("kick_power"));
                        }
                    }
                });

            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

//                queryAll.whereGreaterThan("punch_power", 7000);
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null){
                            if(objects.size() > 0){
                                for(ParseObject kickBoxer: objects) {
                                  allKickBoxers = allKickBoxers + "name: " + kickBoxer.get("name") + "\n" + "punch speed: " + kickBoxer.get("punch_speed")
                                          + "\n" + "punch power: " + kickBoxer.get("punch_power") + "\n" + "kick speed: " + kickBoxer.get("kick_speed")
                                          + "\n" + "kick power: " + kickBoxer.get("kick_power") + "\n\n";
                                }
                                txtGetData.setText(allKickBoxers);

                                FancyToast.makeText(SignUp.this, "Success", FancyToast.LENGTH_LONG,
                                        FancyToast.SUCCESS, true).show();
                            }
                            else {
                                FancyToast.makeText(SignUp.this, e.getMessage(),FancyToast.LENGTH_LONG,
                                        FancyToast.ERROR,true).show();
                            }
                        }
                    }
                });
            }
        });

        btnNextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, SignUpLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        try {
            ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punch_speed", Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punch_power", Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("kick_speed", Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("kick_power", Integer.parseInt(edtKickPower.getText().toString()));

            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
//                  Toast.makeText(SignUp.this, kickBoxer.get("name") + " is saved to server", Toast.LENGTH_SHORT).show();
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " is saved to server",
                                FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                    } else {
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        catch (Exception e){
            FancyToast.makeText(SignUp.this, e.getMessage(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }
    }

}