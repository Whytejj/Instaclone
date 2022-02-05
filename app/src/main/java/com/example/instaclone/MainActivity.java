package com.example.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, View.OnKeyListener{
    String TAG = "tester";
    TextView underText;
    boolean underText_login = true;
    EditText passwordEditText;

    public void go_UserListActivity(){
        Intent intent = new Intent(getApplicationContext(),UserListActivity.class);
        startActivity(intent);
    }

    public void buttonPressed(View view){
        EditText usernameEditText = findViewById(R.id.usernameEditText);

        String usernameText = usernameEditText.getText().toString();
        String passwordText = passwordEditText.getText().toString();

        if (usernameText.equals("") || passwordText.equals("")) {
            Toast.makeText(this, "please enter username and password", Toast.LENGTH_LONG).show();
        }

        if (underText_login) {
            ParseUser user = new ParseUser();
            user.setUsername(usernameText);
            user.setPassword(passwordText);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        go_UserListActivity();
                    } else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            ParseUser.logInInBackground(usernameText, passwordText, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null && e == null){
                        go_UserListActivity();
                        Toast.makeText(MainActivity.this, "Login as "+user.getUsername(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        underText = findViewById(R.id.underText);
        passwordEditText = findViewById(R.id.passwordEditText);
        underText.setOnClickListener(this);
        underText.setOnLongClickListener(this);
        passwordEditText.setOnKeyListener(this);
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        constraintLayout.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null){
            go_UserListActivity();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.underText){
            Button button = findViewById(R.id.Button);

            if(underText_login){
                button.setText("Login");
                underText.setText("or Signup");
                underText_login = false;
            }else {
                button.setText("signup");
                underText.setText("or Login");
                underText_login = true;
            }
        } else if(v.getId() == R.id.constraintLayout){
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.underText){
            Toast.makeText(this, "Long click", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            buttonPressed(v);
        }

        return false;
    }
}
