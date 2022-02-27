package com.beecoder.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beecoder.attendanceapp.bean.FacultyBean;

public class MainActivity extends AppCompatActivity {
    Button login;
    EditText email,password;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , SignupActivity.class));
            }
        });

        login =(Button)findViewById(R.id.login_btn);
        email=(EditText)findViewById(R.id.email_log);
        password=(EditText)findViewById(R.id.pass_log);


        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                    String user_email = email.getText().toString();
                    String pass_word = password.getText().toString();

                    if (TextUtils.isEmpty(user_email))
                    {
                        email.setError("Invalid Email");
                    }
                    else if(TextUtils.isEmpty(pass_word))
                    {
                        password.setError("Enter Password");
                    }
                    DbHelper dbAdapter = new DbHelper(MainActivity.this);
                    FacultyBean facultyBean = dbAdapter.validateFaculty(user_email, pass_word);

                    if(facultyBean!=null)
                    {
                        startActivity(new Intent(MainActivity.this , HomeActivity.class));
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }



        });


    }
}