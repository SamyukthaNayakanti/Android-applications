package com.example.group06_hw06;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.lang.reflect.Array;
import java.util.List;

public class ChatAdapter extends ArrayAdapter<Message> {

    public ChatAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Message record = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chatroom_listview_layout,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView)convertView.findViewById(R.id.textView_messagesender);
            viewHolder.tv_date = (TextView)convertView.findViewById(R.id.textView_time);
            viewHolder.message = (TextView)convertView.findViewById(R.id.textView_message);
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.imageView_messageimage);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name.setText(record.getName());
        PrettyTime p = new PrettyTime();
        viewHolder.tv_date.setText(p.format(record.getDate()));
        viewHolder.message.setText(record.getMessage());
        if(record.getImageURL() != null)
        Picasso.get().load(record.getImageURL()).into(viewHolder.imageView);
        else
            viewHolder.imageView.setVisibility(View.INVISIBLE);


        ((ImageButton)convertView.findViewById(R.id.imageButton_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                DatabaseReference expensesRef = mDatabase.child("expenses");
                DatabaseReference mypushref = expensesRef.child(record.getKey());
                mypushref.removeValue();
            }
        });
        return convertView;
    }

    private static class ViewHolder{
        TextView name;
        TextView tv_date;
        TextView message;
        ImageView imageView;
    }
}
