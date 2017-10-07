package com.example.ajay.swipedeck2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ajay.swipedeck.SwipeDeck;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.mikephil.charting.formatter.*;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

/**
 * Created by Ajay on 4/20/17.
 */

public class ArticleDetailActivity extends AppCompatActivity{
    private TextView articleTitleTextView;
    private TextView articleDescriptionTextView;
    private TextView numberOfLeftSwipesTextView;
    private TextView numberOfRightSwipesTextView;
    private ImageView articleImageView;
    private Activity activity;
    private SwipeDeck cardStack;

    private PieChart pieChart;
    private ArrayList<Entry> entries;
    private ArrayList<String> PieEntryLabels;
    private PieDataSet pieDataSet;
    private PieData pieData;

    int fakeSwipes;
    int realSwipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_with_description);

        // code from Android documentation
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);

        articleTitleTextView = (TextView) findViewById(R.id.articleTitleTextView);
        articleDescriptionTextView = (TextView) findViewById(R.id.articleDescriptionTextView);
        articleImageView = (ImageView) findViewById(R.id.articleImageView);
        numberOfLeftSwipesTextView = (TextView) findViewById(R.id.numberOfFakeSwipesTextView);
        numberOfRightSwipesTextView = (TextView) findViewById(R.id.numberOfRealSwipesTextView);

        Intent intent = getIntent();
        Article article = intent.getParcelableExtra("ARTICLE");
        final String strippedTitle = article.getStrippedTitle();
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Articles/" + strippedTitle);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> map = ((HashMap<String, Object>) dataSnapshot.getValue());
                if (map != null) {
                    long fakeSwipesLong = (long) map.get("fakeSwipes");
                    fakeSwipes = (int) fakeSwipesLong;
                    numberOfLeftSwipesTextView.setText(String.valueOf(fakeSwipes));

                    long realSwipesLong = (long) map.get("realSwipes");
                    realSwipes = (int) realSwipesLong;
                    numberOfRightSwipesTextView.setText(String.valueOf(realSwipes));

                    pieChart = (PieChart) findViewById(R.id.chart1);

                    PieEntryLabels = new ArrayList<String>();

                    entries = new ArrayList<>();
                    entries.add(new BarEntry(fakeSwipes, 0));
                    entries.add(new BarEntry(realSwipes, 1));

                    Log.d("fake", String.valueOf(fakeSwipes));
                    Log.d("real", String.valueOf(realSwipes));


                    PieEntryLabels.add("Fake Swipes");
                    PieEntryLabels.add("Real Swipes");

                    pieDataSet = new PieDataSet(entries, "");

                    pieData = new PieData(PieEntryLabels, pieDataSet);

                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    pieChart.setData(pieData);

                    pieChart.animateY(3000);
                    pieChart.getLegend().setEnabled(false);
                    pieChart.setUsePercentValues(true);
                    pieData.setValueFormatter(new PercentFormatter());
                    pieData.setValueTextSize(12f);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        articleTitleTextView.setText(article.getTitle());
        //articleDescriptionTextView.setText(article.getDescription());
        //Log.d("left swipes on article", String.valueOf(article.getLeftSwipes()));
        //numberOfLeftSwipesTextView.setText(String.valueOf(fakeSwipes));
        //numberOfRightSwipesTextView.setText(String.valueOf(realSwipes));

        if (article.isFromRSSFeedParcelable()) {
            new GetImageURLAsyncTask(activity, articleImageView, article).execute();
        }
        else{
            Picasso.with(activity).load(article.getImageLink()).into(articleImageView);
        }
    }

    // code from stackoverflow
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // code from Android documentation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                ShareDialog shareDialog = new ShareDialog(this);
                // User chose the "Settings" item, show the app settings UI...
                // code from Facebook Android doc
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://ajayjain.io"))
                        .setQuote(("Check out Swype, the app that helps you determine if news " +
                                "articles are real or fake. I got " + MainActivity.correctSwipes +
                                " articles correct"))
                        .build();
                shareDialog.show(content);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
