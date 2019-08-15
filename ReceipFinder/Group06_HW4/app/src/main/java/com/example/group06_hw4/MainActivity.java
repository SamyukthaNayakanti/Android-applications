package com.example.group06_hw4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipePuppy.SearchRecipe,Recipes.onFinishRecipe,RecipeProgress.SetProgressBar {

    GetIngredients getIngredients = new GetIngredients("");
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecipePuppy recipePuppy = new RecipePuppy();
        Bundle bun = new Bundle();
        getIngredients.addIngredient("");
        bun.putSerializable("ingredientslist",getIngredients);
        recipePuppy.setArguments(bun);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerforfrag,recipePuppy,"tag_recipePuppy")
                .commit();


    }

    public void setProgressBar(ProgressBar progressBar)
    {
        this.progressBar = progressBar;
        this.progressBar.setProgress(0);
    }
    @Override
    public void SearchRecipe(GetIngredients ingredients) {
    //http://www.recipepuppy.com/api/?
    //i=<INGREDIENTS LIST, COMMA SEPARATED>&q=<DISH NAME>.
        String recipeurl="http://www.recipepuppy.com/api/";
        new GetRecipePuppyContent(MainActivity.this,ingredients).execute(recipeurl);

    }
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
    }

    @Override
    public void FinishRecipe() {
        getSupportFragmentManager().popBackStack();
    }
}
