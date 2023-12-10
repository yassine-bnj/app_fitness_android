package com.example.my_fit_app;

import android.widget.EditText;

public class Category {
    private String categName;
    private String imageURL;
    private String key;
    public Category() {
    }

    public Category(String categName, String imageURL) {
        this.categName = categName;
        this.imageURL = imageURL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategName() {
        return categName;
    }

    public void setCategName(String categName) {
        this.categName = categName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
