package com.example.ajay.swipedeck2;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ajay on on 4/9/17
 *
 * The original SwipeDeckAdapter can be found in the MainActivity.java file in the GitHub repo:
 * https://github.com/aaronbond/SwipeDeck2. I have modified it for my purposes to display article
 * objects.
 *
 *
 */
public class SwipeDeckAdapter extends BaseAdapter {

    private List<Article> articles;
    private Activity activity;
    public String imageUrl;


    public SwipeDeckAdapter(List<Article> articles, Activity activity) {
        this.articles = articles;
        this.activity = activity;
    }

    // Returns the number of articles we have.
    @Override
    public int getCount() {
        return articles.size();
    }

    // Returns the article at a given position.
    @Override
    public Article getItem(int position) {
        return articles.get(position);
    }

    // Returns an article ID at a given position
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        View v = convertView;
        final Article article = articles.get(position);
        final String strippedTitle = article.getStrippedTitle();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Articles/" + strippedTitle);


        // Populates the view with article cards
        if (v == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.article_card, parent, false);
        }

        ((TextView) v.findViewById(R.id.headline_text)).setText(article.getTitle());
        final ImageView articleImageView = (ImageView) v.findViewById(R.id.article_image);
        final TextView leftSwipesTextView = (TextView) v.findViewById(R.id.numberOfFakeSwipesTextView);
        final TextView rightSwipesTextView = (TextView) v.findViewById(R.id.numberOfRealSwipesTextView);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = ((HashMap<String, Object>) dataSnapshot.getValue());
                if (map != null) {
                    long fakeSwipesLong = (long) map.get("fakeSwipes");
                    int fakeSwipes = (int) fakeSwipesLong;
                    leftSwipesTextView.setText(String.valueOf(fakeSwipes));
                    article.setLeftSwipes(fakeSwipes);
                    Log.d("fake swipes", String.valueOf(article.getLeftSwipes()));
                    long realSwipesLong = (long) map.get("realSwipes");
                    int realSwipes = (int) realSwipesLong;
                    rightSwipesTextView.setText(String.valueOf(realSwipes));
                    Log.d("real swipes", String.valueOf(article.getRightSwipes()));
                    article.setRightSwipes(realSwipes);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        if (article.isFromRSSFeed()) {
            new GetImageURLAsyncTask(activity, articleImageView, article).execute();
            article.setIsRSS(true);
        }
        else {
            if (article.getImageLink().length() == 0) {
                Picasso.with(activity).load("https://img.wonkette.com/wp-content/uploads/2016/08/nbc-fires-donald-trump-after-he-calls-mexicans-rapists-and-drug-runners.jpg").into(articleImageView);

            }
            else{
                Picasso.with(activity).load(article.getImageLink()).into(articleImageView);
            }
        }

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ArticleDetailActivity.class);
                Log.d("left swipes on click", String.valueOf(article.getLeftSwipes()));
                intent.putExtra("ARTICLE", article);
                v.getContext().startActivity(intent);
            }
        });
        return v;
    }
}