package com.example.inclass03;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyProfileActivity extends AppCompatActivity {

    public static final int REQ_AVTAR = 100;
    public static final String STUDENT_INFO = "StudentInfo";
    public static final String EDITED_STUDENT_INFO = "EditedStudentInfo";
    public static final String SELECTED_AVTAR = "SelectedAvtar";
    public static final int DISPLAY_AVATAR = 110;
    int imageid = -1;
    Student student;
    EditText fname,lname,sid;
    String firstname,lastname,studentid;
    RadioGroup rg;
    public int validateInput()
    {
        if(imageid == -1)
        {
            Toast.makeText(MyProfileActivity.this,"Please select an avtar",Toast.LENGTH_LONG).show();
            return -1;
        }
        if(firstname.isEmpty()) {
            fname.setError("Should not be empty");
            return -1;
        }
        if(firstname.matches(".*\\d+.*"))
        {
            fname.setError("Should not contain numbers");
            return -1;
        }

        if(lastname.isEmpty()) {
            lname.setError("Should not be empty");
            return -1;
        }
        if(lastname.matches(".*\\d+.*"))
        {
            lname.setError("Should not contain numbers");
            return -1;
        }

        if(studentid.isEmpty()) {
            sid.setError("Should not be empty");
            return -1;
        }
        if(studentid.length() !=9)
        {
            sid.setError("ID is not valid");
            return -1;
        }
        if(rg.getCheckedRadioButtonId() == -1)
        {
            Toast.makeText(MyProfileActivity.this,"Please select a Department",Toast.LENGTH_LONG).show();
            return -1;
        }
        return 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ((Button)findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = (EditText) findViewById(R.id.firstname);
                lname = ((EditText) findViewById(R.id.lastname));
                sid = ((EditText) findViewById(R.id.studentid));

                rg = (RadioGroup) findViewById(R.id.deptradiogrp);
                firstname = fname.getText().toString();
                lastname = lname.getText().toString();
                studentid = sid.getText().toString();

                if(validateInput() == -1)
                    return;

                String DeptValue = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
                student = new Student(firstname,lastname,DeptValue,studentid,imageid);
                Intent displayprofile = new Intent(MyProfileActivity.this,DisplayMyProfileActivity.class);
                displayprofile.putExtra(STUDENT_INFO,student);
                startActivityForResult(displayprofile,DISPLAY_AVATAR);
            }
        });

        ((ImageView)findViewById(R.id.noavatar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent selectavtavactivity = new Intent(MyProfileActivity.this,SelectAvtarActivity.class);
                startActivityForResult(selectavtavactivity,REQ_AVTAR);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQ_AVTAR)
        {
            if(resultCode == RESULT_OK) {
                String value = data.getExtras().getString(SELECTED_AVTAR);
                if (value.equalsIgnoreCase("0")) {
                    ((ImageView) findViewById(R.id.noavatar)).setImageResource(R.drawable.avatar_f_1);
                    this.imageid = 0;
                } else if (value.equalsIgnoreCase("1")) {
                    ((ImageView) findViewById(R.id.noavatar)).setImageResource(R.drawable.avatar_f_2);
                    this.imageid = 1;
                } else if (value.equalsIgnoreCase("2")) {
                    ((ImageView) findViewById(R.id.noavatar)).setImageResource(R.drawable.avatar_f_3);
                    this.imageid = 2;
                } else if (value.equalsIgnoreCase("3")) {

                    ((ImageView) findViewById(R.id.noavatar)).setImageResource(R.drawable.avatar_m_1);
                    this.imageid = 3;
                } else if (value.equalsIgnoreCase("4")) {
                    ((ImageView) findViewById(R.id.noavatar)).setImageResource(R.drawable.avatar_m_2);
                    this.imageid = 4;
                } else if (value.equalsIgnoreCase("5")) {
                    ((ImageView) findViewById(R.id.noavatar)).setImageResource(R.drawable.avatar_m_3);
                    this.imageid = 5;
                }
            }
        }
        else if(requestCode == DISPLAY_AVATAR){
            if(resultCode == RESULT_OK) {
                if(getIntent() != null && getIntent().getExtras() != null)
                {
                    Student student = (Student)getIntent().getExtras().getSerializable(MyProfileActivity.STUDENT_INFO);
                    ((EditText) findViewById(R.id.firstname)).setText(student.getFirstName());
                    ((EditText) findViewById(R.id.lastname)).setText(student.getLastName());
                    ((EditText) findViewById(R.id.studentid)).setText(student.getStudentid());
                    String deptVal = student.getDept();
                    if(deptVal.equals("CS")) {
                        RadioButton b = (RadioButton) findViewById(R.id.cs);
                        b.setChecked(true);
                    }
                    else if (deptVal.equals("SIS"))
                    {
                        RadioButton b = (RadioButton) findViewById(R.id.sis);
                        b.setChecked(true);
                    }
                    else if (deptVal.equals("BIO"))
                    {
                        RadioButton b = (RadioButton) findViewById(R.id.bio);
                        b.setChecked(true);
                    }
                    else if (deptVal.equals("Other"))
                    {
                        RadioButton b = (RadioButton) findViewById(R.id.other);
                        b.setChecked(true);
                    }

                }

             }


        }
    }
}
