package com.example.group06_hw4;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeProgress extends Fragment {

    SetProgressBar pb;
    public RecipeProgress() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_progress, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        ProgressBar pbview = (ProgressBar)getView().findViewById(R.id.progressBar);
        pb.setProgressBar(pbview);
        super.onActivityCreated(savedInstanceState);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            pb = (RecipeProgress.SetProgressBar)context;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public interface  SetProgressBar{
        void setProgressBar(ProgressBar progressBar);
    }


}
