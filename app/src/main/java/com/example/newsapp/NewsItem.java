package com.example.newsapp;

/**
 * Model class to hold data for one news story.
 */
public class NewsItem {
    // Title shown under the image
    public final String title;
    // Short description of the story
    public final String description;
    // Resource ID of the image to display
    public final int imageResId;

    // Constructor to initialise all fields
    public NewsItem(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }
}
