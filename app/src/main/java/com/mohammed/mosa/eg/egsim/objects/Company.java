package com.mohammed.mosa.eg.egsim.objects;

import android.view.View;

public class Company {

    private String title;
    private String description;
    private int image;
    private View.OnClickListener listener;

    public Company() {
    }

    public Company(String title, String description, int image, View.OnClickListener listener) {
        this.description = description;
        this.title = title;
        this.image = image;
        this.listener = listener;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
