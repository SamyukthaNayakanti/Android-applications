package com.example.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectAvtarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avtar);
        ((ImageView)findViewById(R.id.avatar1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(MyProfileActivity.SELECTED_AVTAR,"0");
                setResult(RESULT_OK,i);
                finish();
            }
        });

        ((ImageView)findViewById(R.id.avatar2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(MyProfileActivity.SELECTED_AVTAR,"1");
                setResult(RESULT_OK,i);
                finish();
            }
        });

        ((ImageView)findViewById(R.id.avatar3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(MyProfileActivity.SELECTED_AVTAR,"2");
                setResult(RESULT_OK,i);
                finish();
            }
        });

        ((ImageView)findViewById(R.id.avatar4)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(MyProfileActivity.SELECTED_AVTAR,"3");
                setResult(RESULT_OK,i);
                finish();
            }
        });

        ((ImageView)findViewById(R.id.avatar5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(MyProfileActivity.SELECTED_AVTAR,"4");
                setResult(RESULT_OK,i);
                finish();
            }
        });

        ((ImageView)findViewById(R.id.avatar6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(MyProfileActivity.SELECTED_AVTAR,"5");
                setResult(RESULT_OK,i);
                finish();
            }
        });


    }
}
