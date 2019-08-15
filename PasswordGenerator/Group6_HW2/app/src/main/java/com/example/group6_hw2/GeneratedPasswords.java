package com.example.group6_hw2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class GeneratedPasswords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_passwords);
        int count = 0,count_Async=0;
        Bundle bundle = getIntent().getExtras();
        count= bundle.getInt(MainActivity.COUNT_KEY);
        count_Async= bundle.getInt(MainActivity.COUNT_KEY_ASYNC);




       LinearLayout gallery =findViewById(R.id.gallery);
       LayoutInflater inflater = LayoutInflater.from(this);
        ArrayList<String> myList = getIntent().getExtras().getStringArrayList(MainActivity.THREAD_KEY);

        for (String s : myList) {
            View view = inflater.inflate(R.layout.linear,gallery,false);
           TextView textView = view.findViewById(R.id.text);
            textView.setText(s + "");
            gallery.addView(view);

        }



        LinearLayout gallery1 =findViewById(R.id.gallery1);

        ArrayList<String> myListAsync = getIntent().getExtras().getStringArrayList(MainActivity.ASYNC_KEY);
        for(String s: myListAsync)
        {
            View view = inflater.inflate(R.layout.linear,gallery1,false);
            TextView textView = view.findViewById(R.id.text);
            textView.setText(s+"");
            gallery1.addView(view);
        }

   }


}
