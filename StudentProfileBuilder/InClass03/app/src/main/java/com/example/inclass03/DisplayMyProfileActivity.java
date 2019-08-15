package com.example.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayMyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_my_profile);
        final Student student = (Student)getIntent().getExtras().getSerializable(MyProfileActivity.STUDENT_INFO);
        ((TextView)findViewById(R.id.editname)).setText(student.getFullName());
        ((TextView)findViewById(R.id.editstudentID)).setText(student.getStudentid());
        ((TextView)findViewById(R.id.editdepartment)).setText(student.getDept());
        ((ImageView)findViewById(R.id.selectedavatar)).setImageResource(Student.avatars[student.imageid]);

        ((Button)findViewById(R.id.button_edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(MyProfileActivity.EDITED_STUDENT_INFO,student);
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }
}
