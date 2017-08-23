/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Boolean signupModeActive = true;
    TextView changesignupmodeTextView ;
    EditText usernameEditText ;
    EditText passwordEditText ;

    public void redirectIfLoggedIn(){
        Intent intent = new Intent(getApplicationContext() , UserListActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        changesignupmodeTextView = (TextView) findViewById(R.id.changesignupmodeTextView);
        changesignupmodeTextView.setOnClickListener(this);

        redirectIfLoggedIn();

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    public void signUp(View view){

        if(usernameEditText.getText().toString().matches(" ") && passwordEditText.getText().toString().matches(" ")){
            Toast.makeText(this, "username and password are required", Toast.LENGTH_SHORT).show();
        }else{
            if(usernameEditText.getText().length() != 0 && passwordEditText.getText().length() != 0) {
                if(signupModeActive){
                    ParseUser user = new ParseUser();
                    user.setUsername(usernameEditText.getText().toString());
                    user.setPassword(passwordEditText.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                                Toast.makeText(MainActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                                redirectIfLoggedIn();
                            }

                    });
                }else{
                    ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {

                                Toast.makeText(MainActivity.this, "log in successsful", Toast.LENGTH_SHORT).show();
                                redirectIfLoggedIn();
                        }
                    });
                }
            }else{
                Toast.makeText(this, "username and password are required", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.changesignupmodeTextView) {
      Log.i("info" , "button clicked");
            if (signupModeActive) {
                Button signupButton = (Button) findViewById(R.id.signupButton);
                signupModeActive = false;
                signupButton.setText("LOG IN");
                changesignupmodeTextView.setText("or, SIGNUP");


            } else {
                Button signupButton = (Button) findViewById(R.id.signupButton);
                signupModeActive = true;
                signupButton.setText("SIGNUP");
                changesignupmodeTextView.setText("or, LOGIN");

            }
        }
    }


}
