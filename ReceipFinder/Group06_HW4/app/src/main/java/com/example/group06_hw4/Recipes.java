package com.example.group06_hw4;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Recipes extends Fragment {

    ArrayList<DishRecord> records;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    onFinishRecipe flistener;
    public Recipes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            flistener = (onFinishRecipe) context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle bun = getArguments();
        records =(ArrayList<DishRecord>)bun.getSerializable("recipeslist");
        recyclerView = (RecyclerView)getView().findViewById(R.id.list_recipes);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DishRecordAdapter(records,getActivity());
        recyclerView.setAdapter(mAdapter);

        Button finishbtn = (Button)getView().findViewById(R.id.finishbtn);
        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flistener.FinishRecipe();
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    public interface onFinishRecipe{
        public void FinishRecipe();
    }
}
