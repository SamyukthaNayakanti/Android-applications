package com.example.group06_hw4;

import java.io.Serializable;
import java.util.ArrayList;

public class GetIngredients implements Serializable {
    String dishname;
    ArrayList<String> ingredients;

    public GetIngredients(String dishname)
    {
        this.dishname = dishname;
        this.ingredients = new ArrayList<String>();
    }
    public GetIngredients(String dishname, ArrayList<String> ingredients) {
        this.dishname = dishname;
        this.ingredients = ingredients;
    }

    public void addIngredient(String in)
    {
        ingredients.add(in);
    }

    public void removeIngredient(int index)
    {
        ingredients.remove(index);
    }

    public String getDishname() {
        return dishname;
    }

    public void setDishname(String dishname) {
        this.dishname = dishname;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
