package com.example.group06_hw4;

import java.io.Serializable;

public class DishRecord implements Serializable {
    String title,url,imageurl,ingredients;

    public DishRecord(){}
    public DishRecord(String title, String url, String imageurl, String ingredients) {
        this.title = title;
        this.url = url;
        this.imageurl = imageurl;
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "DishRecord{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", imageurl='" + imageurl + '\'' +
                ", ingredients='" + ingredients + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
