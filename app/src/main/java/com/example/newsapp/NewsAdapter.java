package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// NewsAdapter is used to show the full list of news items in the RecyclerView (in the grid layout).
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {

    // This is an interface to handle clicks on a news item.
    public interface OnClick {
        void onItem(NewsItem item);
    }

    // This is the list of all news items we want to show.
    private final List<NewsItem> items;
    // This is the click listener so we can respond when a user taps on a news item.
    private final OnClick listener;

    // This is the constructor - it takes the list of items and the click listener when we create the adapter.
    public NewsAdapter(List<NewsItem> items, OnClick listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        // This method is called when we need to create a new ViewHolder.
        // We inflate (load) the layout file for one single news item (item_news.xml).
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int pos) {
        // This method is called to put data into each item in the list.
        NewsItem item = items.get(pos);
        // Set the image, title, and description for this news item.
        holder.image.setImageResource(item.imageResId);
        holder.title.setText(item.title);
        holder.desc.setText(item.description);
        // Set what happens when someone clicks on this item (call the listener).
        holder.itemView.setOnClickListener(v -> listener.onItem(item));
    }

    @Override
    public int getItemCount() {
        // Return the total number of news items in the list.
        return items.size();
    }

    // VH stands for ViewHolder. It holds references to the views inside one single news item layout.
    static class VH extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, desc;

        VH(View v) {
            super(v);
            // Find the views inside item_news.xml by their IDs.
            image = v.findViewById(R.id.imageNews);
            title = v.findViewById(R.id.titleNews);
            desc  = v.findViewById(R.id.descNews);
        }
    }
}
