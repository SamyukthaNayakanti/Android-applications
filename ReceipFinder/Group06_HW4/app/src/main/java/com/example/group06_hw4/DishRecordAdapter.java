package com.example.group06_hw4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DishRecordAdapter extends RecyclerView.Adapter<DishRecordAdapter.MyViewHolder> {
    private ArrayList<DishRecord> mDataset;
    Context context;
    public DishRecordAdapter(ArrayList<DishRecord> mDataset, Context context)
    {
        this.mDataset = mDataset;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_view, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        DishRecord d = mDataset.get(i);
        myViewHolder.title.setText(d.getTitle());
        myViewHolder.url.setText(d.getUrl());
        myViewHolder.url.setPaintFlags(myViewHolder.url.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        myViewHolder.ingredients.setText(d.getIngredients());
        if(!d.getImageurl().isEmpty())
            Picasso.get().load(d.getImageurl()).into(myViewHolder.imageview);
        myViewHolder.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = myViewHolder.url.getText().toString();
                Intent i =new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,ingredients,url;
        ImageView imageview;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.titlevalue);
            url = (TextView)itemView.findViewById(R.id.urlvalue);
            ingredients = (TextView)itemView.findViewById(R.id.ingredientsvalue);
            imageview = (ImageView)itemView.findViewById(R.id.imageView);


        }
        // each data item is just a string in this case

    }
}
