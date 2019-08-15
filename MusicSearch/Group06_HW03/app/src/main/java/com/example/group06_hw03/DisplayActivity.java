package com.example.group06_hw03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        final MusicRecord record = (MusicRecord) getIntent().getExtras().getSerializable(MainActivity.TRACK_INFO);
        ((TextView)findViewById(R.id.trackcontent)).setText(record.getTrack());
        ((TextView)findViewById(R.id.genrecontent)).setText(record.getGenre());
        ((TextView)findViewById(R.id.artistcontent)).setText(record.getArtist());
        ((TextView)findViewById(R.id.albumcontent)).setText(record.getAlbum());
        ((TextView)findViewById(R.id.trackpricecontent)).setText(Double.toString(record.getTrackprice()));
        ((TextView)findViewById(R.id.albumpricecontent)).setText(Double.toString(record.getAlbumprice()));
        Picasso.get().load(record.getImageUrl()).into((ImageView)findViewById(R.id.artistimg));
        ((Button)findViewById(R.id.finish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
