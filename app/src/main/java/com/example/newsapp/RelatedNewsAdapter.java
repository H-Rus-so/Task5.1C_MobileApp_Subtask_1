package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// RelatedNewsAdapter is used to show a vertical list of related news items in the DetailFragment.
public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.VH> {

    // This is an interface to handle clicks when a related news item is tapped.
    public interface OnClick {
        void onItem(NewsItem item);
    }

    // This is the list of related news items we want to show.
    private final List<NewsItem> items;
    // This is the click listener that lets us handle taps.
    private final OnClick listener;

    // This is the constructor - it takes the list of related news items and the click listener.
    public RelatedNewsAdapter(List<NewsItem> items, OnClick listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This method creates a new ViewHolder whenever the RecyclerView needs one.
        // use the same layout for each item as the grid (item_news.xml).
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        // This method fills in the data for each news item in the list.
        NewsItem item = items.get(position);
        // Set the image, title, and description for this news item.
        holder.image.setImageResource(item.imageResId);
        holder.title.setText(item.title);
        holder.desc.setText(item.description);
        // Set up what happens when someone clicks on this related news item.
        holder.itemView.setOnClickListener(v -> listener.onItem(item));
    }

    @Override
    public int getItemCount() {
        // Return the total number of related news items.
        return items.size();
    }

    // VH stands for ViewHolder. It holds references to the views inside a single news item layout.
    static class VH extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, desc;

        VH(@NonNull View v) {
            super(v);
            // Find the views inside item_news.xml by their IDs.
            image = v.findViewById(R.id.imageNews);
            title = v.findViewById(R.id.titleNews);
            desc  = v.findViewById(R.id.descNews);
        }
    }
}
