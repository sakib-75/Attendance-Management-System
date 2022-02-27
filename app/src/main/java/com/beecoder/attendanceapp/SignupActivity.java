package com.beecoder.attendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beecoder.attendanceapp.bean.FacultyBean;

public class SignupActivity extends AppCompatActivity {
    private TextView mTextView;

    Button registerButton;
    EditText textName;
    EditText textEmail;
    EditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mTextView = findViewById(R.id.textViewsignup);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this , MainActivity.class));
            }
        });

        textName=(EditText)findViewById(R.id.editTextName);
        textEmail=(EditText)findViewById(R.id.email_reg);
        textPassword=(EditText)findViewById(R.id.passreg);
        registerButton=(Button)findViewById(R.id.registration_btn);

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name = textName.getText().toString();
                String email = textEmail.getText().toString();
                String passWord = textPassword.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    textName.setError("Please Faculty Name");
                }
                else if (TextUtils.isEmpty(email)) {
                    textEmail.setError("Enter Email");
                }
                else {

                    FacultyBean facultyBean = new FacultyBean();
                    facultyBean.setFaculty_name(name);
                    facultyBean.setFaculty_email(email);
                    facultyBean.setFaculty_password(passWord);

                    DbHelper dbAdapter = new DbHelper(SignupActivity.this);
                    dbAdapter.addFaculty(facultyBean);

                    Intent intent =new Intent(SignupActivity.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Faculty added successfully", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}