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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipePuppy extends Fragment {

    private RecipeIngredientsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    SearchRecipe searchRecipe;
    GetIngredients getIngredients;
    public RecipePuppy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_puppy, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        recyclerView = (RecyclerView)getView().findViewById(R.id.list_ingredients);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        Button searchbtn = (Button)getView().findViewById(R.id.searchbtn);
        final EditText editText_dishname = (EditText)getView().findViewById(R.id.edittext_dishname);
        Bundle bun = getArguments();
        getIngredients = (GetIngredients)bun.getSerializable("ingredientslist");
        mAdapter = new RecipeIngredientsAdapter(getIngredients.getIngredients(),getActivity());
        recyclerView.setAdapter(mAdapter);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dishname = editText_dishname.getText().toString();
                if(dishname.isEmpty()){
                    Toast.makeText(getActivity(),"Enter a proper dishname",Toast.LENGTH_LONG).show();
                    return;
                }
                getIngredients.setDishname(dishname);
                getIngredients.setIngredients(mAdapter.getResults());
                searchRecipe.SearchRecipe(getIngredients);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            searchRecipe = (SearchRecipe)context;
            }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public interface SearchRecipe{
        void SearchRecipe(GetIngredients ingredients);
    }

}
