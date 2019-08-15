package com.example.group06_hw4;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GetRecipePuppyContent extends AsyncTask<String,Integer, ArrayList<DishRecord>> {
    GetIngredients ingredients;
    Context context;
    ProgressBar progressBar;
    public GetRecipePuppyContent(Context context,GetIngredients ingredients) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar = ((MainActivity)context).progressBar;
        progressBar.setProgress(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        ((MainActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerforfrag,new RecipeProgress(),"tag_recipeProgress")
                .addToBackStack(null)
                .commit();
        super.onPreExecute();
    }

    @Override
    protected ArrayList<DishRecord> doInBackground(String... params) {
        HttpURLConnection connection = null;
        publishProgress(5);
        String comma_sep = android.text.TextUtils.join(",", ingredients.getIngredients());
        String totalnewsurl = params[0]+"?q="+ingredients.getDishname()+"&i="+comma_sep;
        ArrayList<DishRecord> result= new ArrayList<DishRecord>();
        try {
            URL url = new URL(totalnewsurl);
            Log.e("Meghana",totalnewsurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String json = IOUtils.toString(connection.getInputStream(), "UTF8");
                Log.e("Meghana",json);

                JSONObject root = new JSONObject(json);
                JSONArray tracks = root.getJSONArray("results");
                for (int i=0;i<tracks.length();i++) {
                    JSONObject recipeJson = tracks.getJSONObject(i);
                    DishRecord record = new DishRecord();
                    record.setTitle(recipeJson.getString("title"));
                    record.setIngredients(recipeJson.getString("ingredients"));
                    record.setImageurl(recipeJson.getString("thumbnail"));
                    record.setUrl(recipeJson.getString("href"));
                    Log.d("Meghana","Record "+i+" "+record.toString());
                    result.add(record);
                    publishProgress((100/tracks.length())*(i+1));
                }
                publishProgress(100);
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
    protected void onPostExecute(final ArrayList<DishRecord> records) {
        ((MainActivity)context).getSupportFragmentManager().popBackStack();
        if(records.size() ==0)
        {
            Toast.makeText(context,"No Recipes to show.",Toast.LENGTH_LONG).show();
            return;
        }
        Recipes recipes = new Recipes();
        Bundle bun = new Bundle();
        bun.putSerializable("recipeslist",records);
        recipes.setArguments(bun);
        ((MainActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerforfrag,recipes,"tag_recipes")
                .addToBackStack(null)
                .commit();
    }
}
