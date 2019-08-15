package com.example.group06_hw03;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GetiTunesConent extends AsyncTask<String,Void, ArrayList<MusicRecord>> {

//    String url;
//    String artistName;
//    int limitvalue;

    ListView resultsview;
    Context context;
    Boolean isDate;
    ProgressBar progressBar;
    GetiTunesConent(Context context, ListView resultsview, Boolean isDate, ProgressBar progressBar)
    {
//        this.url = url;
//        this.artistName = artistName;
//        this.limitvalue = limitvalue;
        this.context = context;
        this.resultsview = resultsview;
        this.isDate = isDate;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected ArrayList<MusicRecord> doInBackground(String... params) {
        HttpURLConnection connection = null;
        String totalnewsurl = params[0]+"?term="+params[1]+"&limit="+params[2];
        ArrayList<MusicRecord> result= new ArrayList<MusicRecord>();
        try {
            URL url = new URL(totalnewsurl);
            Log.e("Meghana",totalnewsurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");

                JSONObject root = new JSONObject(json);
                JSONArray tracks = root.getJSONArray("results");
                for (int i=0;i<tracks.length();i++) {
                    JSONObject trackJson = tracks.getJSONObject(i);
                    MusicRecord musicRecord = new MusicRecord();
                    musicRecord.setAlbum(trackJson.getString("collectionName"));
                    musicRecord.setAlbumprice(trackJson.getDouble("collectionPrice"));
                    musicRecord.setArtist(trackJson.getString("artistName"));
                    String dateInString = trackJson.getString("releaseDate");
                    //2006-02-06T08:00:00Z
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
                    Date parsedDate = formatter.parse(dateInString);
                    musicRecord.setDate(parsedDate);
                    musicRecord.setGenre(trackJson.getString("primaryGenreName"));
                    musicRecord.setImageUrl(trackJson.getString("artworkUrl100"));
                    musicRecord.setTrack(trackJson.getString("trackName"));
                    musicRecord.setTrackprice(trackJson.getDouble("trackPrice"));
                    Log.d("Meghana","Record "+i+" "+musicRecord.toString());
                    result.add(musicRecord);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Close open connection
            if(connection !=null)
                connection.disconnect();
        }
        return result;
    }

    @Override
    protected void onPostExecute(final ArrayList<MusicRecord> records) {

        if(isDate) {
            Collections.sort(records, new Comparator<MusicRecord>() {
                @Override
                public int compare(MusicRecord o1, MusicRecord o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
        }

        else
        {
            Collections.sort(records, new Comparator<MusicRecord>() {
                @Override
                public int compare(MusicRecord o1, MusicRecord o2) {
                    if(o1.getTrackprice() < o2.getTrackprice())
                        return -1;
                    else if (o1.getTrackprice()>o2.getTrackprice())
                        return 1;
                    else
                        return 0;
                }
            });
        }
        if(records.size() > 0)
        {
            progressBar.setVisibility(View.INVISIBLE);
            MusicAdapter madapter = new MusicAdapter(context,R.layout.record_layout,records);
            resultsview.setAdapter(madapter);
            resultsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i =new Intent(context,DisplayActivity.class);
                    i.putExtra(MainActivity.TRACK_INFO,records.get(position));
                    context.startActivity(i);
                    Log.d("Meghana","Clicked"+records.get(position).getTrack());
                }
            });
        }
    }
}
