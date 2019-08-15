package com.example.group06_hw03;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MusicAdapter extends ArrayAdapter<MusicRecord> {


    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        MusicRecord record = getItem(position);
        ViewHolder viewHolder;;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.record_layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.trackName = (TextView)convertView.findViewById(R.id.trackName);
            viewHolder.artistName = (TextView)convertView.findViewById(R.id.artistName);
            viewHolder.priceValue = (TextView)convertView.findViewById(R.id.pricevalue);
            viewHolder.dateValue = (TextView)convertView.findViewById(R.id.datevalue);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.artistName.setText(record.getArtist());
        viewHolder.trackName.setText(record.getTrack());
        DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy");
        String strdate = dateFormat.format(record.getDate());
        viewHolder.dateValue.setText(strdate);
        viewHolder.priceValue.setText(Double.toString(record.getTrackprice()));
        return convertView;
    }

    public MusicAdapter(Context context, int resource, List<MusicRecord> objects) {
        super(context, resource, objects);
    }

    private static class ViewHolder{
        TextView trackName;
        TextView artistName;
        TextView priceValue;
        TextView dateValue;
    }
}
