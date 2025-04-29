package com.example.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// DetailFragment is the screen that shows more information about the news item the user taps on.
// It shows the full image, full description, and a list of other related news.
public class DetailFragment extends Fragment {

    // These are keys we will use to pass data into the fragment.
    private static final String ARG_TITLE       = "title";
    private static final String ARG_DESC        = "description";
    private static final String ARG_IMAGE_RESID = "imageResId";

    // These variables will store the actual data we pass into the fragment.
    private String title;
    private String description;
    private int imageResId;

    // This method is used to create a new DetailFragment with all the information it needs.
    public static DetailFragment newInstance(String title, String description, int imageResId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        // We put the title, description, and image resource ID into the bundle.
        args.putString(ARG_TITLE, title);
        args.putString(ARG_DESC, description);
        args.putInt(ARG_IMAGE_RESID, imageResId);
        // Then we attach the bundle to the fragment.
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Here we get the data that was passed to the fragment when it was created.
        if (getArguments() != null) {
            title       = getArguments().getString(ARG_TITLE);
            description = getArguments().getString(ARG_DESC);
            imageResId  = getArguments().getInt(ARG_IMAGE_RESID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // This inflates (loads) the fragment_detail.xml layout so we can work with it.
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        // 1) Find the views inside the layout (ImageView, TextView, and RecyclerView).
        ImageView       detailImage      = view.findViewById(R.id.detailImage);
        TextView        detailDescription= view.findViewById(R.id.detailDescription);
        RecyclerView    rvRelatedNews    = view.findViewById(R.id.rvRelatedNews);

        // 2) Set the image and description for the news item we tapped.
        detailImage.setImageResource(imageResId);
        detailDescription.setText(description);

        // 3) Get the full list of news items from MainActivity.
        List<NewsItem> allNews = ((MainActivity) requireActivity()).getNewsList();

        // 4) Create a list of related news by adding everything except the news item we tapped on.
        List<NewsItem> related = new ArrayList<>();
        for (NewsItem item : allNews) {
            // We check if the title is different from the tapped news item.
            if (!item.title.equals(title)) {
                related.add(item);
            }
        }

        // 5) Set up the RecyclerView to show the related news items vertically (one after another).
        rvRelatedNews.setLayoutManager(new LinearLayoutManager(requireContext()));
        // Create the adapter for related news and set what happens when someone taps a related item.
        RelatedNewsAdapter adapter = new RelatedNewsAdapter(
                related,
                new RelatedNewsAdapter.OnClick() {
                    @Override
                    public void onItem(NewsItem item) {
                        // When someone taps a related item, we replace this fragment
                        // with a new DetailFragment showing the tapped related item's details.
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(
                                        R.id.fragmentContainer,
                                        DetailFragment.newInstance(
                                                item.title,
                                                item.description,
                                                item.imageResId
                                        )
                                )
                                .addToBackStack(null) // This allows the user to press back to return to the previous detail.
                                .commit();
                    }
                }
        );
        // Attach the adapter to the related news RecyclerView.
        rvRelatedNews.setAdapter(adapter);
    }
}
