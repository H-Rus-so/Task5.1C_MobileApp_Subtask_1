package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Arrays;
import java.util.List;

// MainActivity is the main screen of the app where we show the top stories and all news items.
// It uses two RecyclerViews - one scrolling horizontally for top stories, and another grid for the full news list.
public class MainActivity extends AppCompatActivity {

    // These are the RecyclerViews we have in the layout (activity_main.xml).
    private RecyclerView rvTopStories;
    private RecyclerView rvNews;
    // These are the arrow buttons to manually scroll through top stories left and right.
    private ImageButton btnScrollLeft, btnScrollRight;

    // This is where we will store the full list of news items for the app.
    private List<NewsItem> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This connects our Java file to the XML layout (activity_main.xml).
        setContentView(R.layout.activity_main);

        // Find all the views we need by their ID from the XML layout.
        rvTopStories   = findViewById(R.id.rvTopStories);
        rvNews         = findViewById(R.id.rvNews);
        btnScrollLeft  = findViewById(R.id.btnScrollLeft);
        btnScrollRight = findViewById(R.id.btnScrollRight);

        // sample Top Stories data
        List<NewsItem> topStories = Arrays.asList(
                new NewsItem("Story A", getString(R.string.storyA), R.drawable.story_a),
                new NewsItem("Story B", getString(R.string.storyB), R.drawable.story_b),
                new NewsItem("Story C", getString(R.string.storyC), R.drawable.story_c),
                new NewsItem("Story D", getString(R.string.storyD), R.drawable.story_d),
                new NewsItem("Story E", getString(R.string.storyE), R.drawable.story_e)
        );

        // Set up the full list of news items that will show in the grid section.
        newsList = Arrays.asList(
                new NewsItem("9NEWS",    getString(R.string.news9),       R.drawable.news9),
                new NewsItem("7NEWS",    getString(R.string.news7),       R.drawable.news7),
                new NewsItem("ABC NEWS", getString(R.string.abcnews),     R.drawable.abcnews),
                new NewsItem("THE AGE",  getString(R.string.agenews),     R.drawable.agenews),
                new NewsItem("SKY NEWS", getString(R.string.skynews),     R.drawable.skynews)
        );

        // Set up the RecyclerView for Top Stories so it scrolls horizontally across the screen.
        rvTopStories.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        // Create the adapter for top stories and set what happens when someone taps on a story.
        TopStoriesAdapter topAdapter = new TopStoriesAdapter(
                topStories,
                new TopStoriesAdapter.OnClick() {
                    @Override
                    public void onItem(NewsItem item) {
                        // When someone taps a top story, show the details on the screen.
                        showDetail(item);
                    }
                }
        );
        // Attach the adapter to the RecyclerView.
        rvTopStories.setAdapter(topAdapter);

        // Set up what happens when someone clicks the left arrow button.
        btnScrollLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Scroll the top stories RecyclerView to the left.
                rvTopStories.smoothScrollBy(-250, 0);
            }
        });
        // Set up what happens when someone clicks the right arrow button.
        btnScrollRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Scroll the top stories RecyclerView to the right.
                rvTopStories.smoothScrollBy(250, 0);
            }
        });

        // Set up the RecyclerView for the full news list to show in a 2-column grid layout.
        rvNews.setLayoutManager(new GridLayoutManager(this, 2));
        // Create the adapter for the full news list and set what happens when someone taps on a news item.
        NewsAdapter newsAdapter = new NewsAdapter(
                newsList,
                new NewsAdapter.OnClick() {
                    @Override
                    public void onItem(NewsItem item) {
                        // When someone taps a news item, show its details on the screen.
                        showDetail(item);
                    }
                }
        );
        // Attach the adapter to the news RecyclerView.
        rvNews.setAdapter(newsAdapter);
    }

    // This method lets other parts of the app (like a fragment) get access to the full list of news items.
    public List<NewsItem> getNewsList() {
        return newsList;
    }

    // This method replaces the current fragment with a DetailFragment to show more information about the news item clicked.
    private void showDetail(NewsItem item) {
        DetailFragment fragment = DetailFragment.newInstance(
                item.title,
                item.description,
                item.imageResId
        );
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)  // This means if the user presses back, they go back to the previous screen.
                .commit();
    }
}
