package com.example.group06_hw03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String itunesURL = "https://itunes.apple.com/search";
    public static final String TRACK_INFO = "trackinfo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int min = 10;
        final int max = 30;
        final int step = 1;
        final ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekbar);
        final ListView resultsView = (ListView)findViewById(R.id.resultsview);
        seekBar.setMax((max-min)/step);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = min + (progress*step);
                ((TextView)findViewById(R.id.limitvalue)).setText(String.valueOf(value));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Switch sortswitch = (Switch)findViewById(R.id.sortswitch);
                final Boolean isDate = sortswitch.isChecked();
                String artistName = ((EditText)findViewById(R.id.searchtext)).getText().toString();
                if(artistName.trim().isEmpty())
                {
                    ((EditText) findViewById(R.id.searchtext)).setError("Enter valid Value");
                }
                artistName = artistName.replace(" ","+");
                int value = Integer.parseInt(((TextView)findViewById(R.id.limitvalue)).getText().toString());
                new GetiTunesConent(MainActivity.this,resultsView,isDate,progressBar).execute(itunesURL,artistName,String.valueOf(value));

            }
        });

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText)findViewById(R.id.searchtext)).setText("");
                ((SeekBar) findViewById(R.id.seekbar)).setProgress(0);
                ArrayList<MusicRecord> records = new ArrayList<MusicRecord>();
                MusicAdapter madapter = new MusicAdapter(MainActivity.this,R.layout.record_layout,records);
                resultsView.setAdapter(madapter);
                ((Switch)findViewById(R.id.sortswitch)).setChecked(true);
            }
        });
    }
}
