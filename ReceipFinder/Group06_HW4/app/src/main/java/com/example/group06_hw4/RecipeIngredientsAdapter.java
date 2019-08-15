package com.example.group06_hw4;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.MyViewHolder> {
    private ArrayList<String> mDataset;
    private ArrayList<String> results = new ArrayList<String>();
    Context context;

    public ArrayList<String> getResults()
    {
        return mDataset;
    }
    public RecipeIngredientsAdapter(ArrayList<String> mDataset, Context context) {
        this.mDataset = mDataset;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ingredient_view, viewGroup, false);
        RecipeIngredientsAdapter.MyViewHolder viewHolder = new RecipeIngredientsAdapter.MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            String editText_content = mDataset.get(i);
            final int index = i;

            myViewHolder.editText.setText(editText_content);

            if(i ==4)
            {
                myViewHolder.add.hide();
                myViewHolder.add.setEnabled(false);
                myViewHolder.remove.show();
                myViewHolder.remove.setEnabled(true);
            }
            else
            {
                int count = getItemCount();
                if(index==count-1 )
                {
                    myViewHolder.remove.hide();
                    myViewHolder.remove.setEnabled(false);
                    myViewHolder.add.show();
                    myViewHolder.add.setEnabled(true);
                }
            }
            if(getItemCount() == 1 && i==0)
            {
                myViewHolder.remove.hide();
                myViewHolder.remove.setEnabled(false);
                myViewHolder.add.show();
                myViewHolder.add.setEnabled(true);
            }
            myViewHolder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if(getItemCount() ==1) {
//                        if (mDataset.get(0) == "")
//                            mDataset.remove(index);
//                    }
//                    else
//                    {
//                        mDataset.remove("");
//                    }
//                    mDataset.add(myViewHolder.editText.getText().toString());

                    mDataset.remove("");
                    mDataset.add(myViewHolder.editText.getText().toString());
                    mDataset.add("");
                    myViewHolder.add.setEnabled(false);
                    myViewHolder.add.hide();
                    myViewHolder.remove.setEnabled(true);
                    myViewHolder.remove.show();
                    notifyDataSetChanged();
                }
            });
            myViewHolder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDataset.remove(myViewHolder.editText.getText().toString());
                    //results.remove();
                    notifyDataSetChanged();
                }
            });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.editText = (EditText)itemView.findViewById(R.id.edittext_ingredient);
            this.add = (FloatingActionButton)itemView.findViewById(R.id.addbtn);
            this.remove = (FloatingActionButton)itemView.findViewById(R.id.remove);
        }

        public EditText editText;
        public FloatingActionButton add,remove;

    }
}
