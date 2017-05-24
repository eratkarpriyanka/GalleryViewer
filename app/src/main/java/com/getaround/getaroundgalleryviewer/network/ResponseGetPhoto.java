package com.getaround.getaroundgalleryviewer.network;

import com.getaround.getaroundgalleryviewer.models.Photo;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseGetPhoto {

    long current_page;
    long total_pages;
    long total_items;

    @SerializedName("photos")
    ArrayList<Photo> photoList;

    public long getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(long current_page) {
        this.current_page = current_page;
    }

    public long getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(long total_pages) {
        this.total_pages = total_pages;
    }

    public long getTotal_items() {
        return total_items;
    }

    public void setTotal_items(long total_items) {
        this.total_items = total_items;
    }

    public ArrayList<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(ArrayList<Photo> photoList) {
        this.photoList = photoList;
    }
}
