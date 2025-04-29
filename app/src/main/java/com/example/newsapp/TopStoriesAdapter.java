package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// TopStoriesAdapter is used to show the horizontal list of top news stories at the top of the home screen.
public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.VH> {

    // This is an interface to handle clicks when a top story is tapped.
    public interface OnClick {
        void onItem(NewsItem item);
    }

    // This is the list of top story items we want to show.
    private final List<NewsItem> items;
    // This is the click listener so we know when someone taps a top story.
    private final OnClick listener;

    // This is the constructor - it takes the list of top stories and the click listener.
    public TopStoriesAdapter(List<NewsItem> items, OnClick listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        // This method is called when we need to create a new ViewHolder.
        // We inflate (load) the layout file for a single top story item (item_top_story.xml).
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_story, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int pos) {
        // This method fills in the data for each top story item.
        NewsItem item = items.get(pos);
        // Set the image for the top story.
        holder.image.setImageResource(item.imageResId);
        // Set what happens when someone clicks on the top story.
        holder.itemView.setOnClickListener(v -> listener.onItem(item));
    }

    @Override
    public int getItemCount() {
        // Return the total number of top stories in the list.
        return items.size();
    }

    // VH stands for ViewHolder. It holds a reference to the views inside a single top story layout.
    static class VH extends RecyclerView.ViewHolder {
        ImageView image;

        VH(View v) {
            super(v);
            // Find the image view inside item_top_story.xml by its ID.
            image = v.findViewById(R.id.imageTopStory);
        }
    }
}
