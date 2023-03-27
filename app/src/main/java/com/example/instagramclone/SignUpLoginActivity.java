package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUsername, edtPassword;
    private Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnSignUp.setOnClickListener(SignUpLoginActivity.this);
        btnLogIn.setOnClickListener(SignUpLoginActivity.this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        
        switch (id){
            case R.id.btnSignUp:

                ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUsername.getText().toString());
                appUser.setPassword(edtPassword.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            FancyToast.makeText(SignUpLoginActivity.this, appUser.get("username")
                                    + " signed up successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true)
                                    .show();

                            Intent intent = new Intent(SignUpLoginActivity.this, WelcomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            FancyToast.makeText(SignUpLoginActivity.this, e.getMessage(),FancyToast.LENGTH_LONG,
                                    FancyToast.ERROR,true).show();
                        }
                    }
                });
                break;

            case R.id.btnLogIn:

                ParseUser.logInInBackground(edtUsername.getText().toString(), edtPassword.getText().toString(),
                        new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                      if(user != null && e == null){
                          FancyToast.makeText(SignUpLoginActivity.this, user.get("username")
                                          + " logged in successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, true)
                                  .show();

                          Intent intent = new Intent(SignUpLoginActivity.this, WelcomeActivity.class);
                          startActivity(intent);
                      }
                      else {
                          FancyToast.makeText(SignUpLoginActivity.this, e.getMessage(),FancyToast.LENGTH_LONG,
                                  FancyToast.ERROR,true).show();
                      }
                    }
                });
                break;
        }
    }
}
