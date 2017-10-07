package com.example.ajay.swipedeck2;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.ajay.swipedeck.SwipeDeck;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.facebook.FacebookSdk;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static android.R.attr.duration;

/**
 *
 * Created by Ajay on 4/9/17
 *
 * The basis of the MainActivity class is from the following GitHub repository:
 * https://github.com/aaronbond/SwipeDeck2
 *
 * I modified the MainActivity's cardSwipedLeft and cardSwipedRight methods to display a Toast
 * message if an article is real or not and whether or not the user got that choice correct.
 */
public class MainActivity extends AppCompatActivity {

    public static final String[] urlStrings = NewsSources.getNewsSources();
    private SwipeDeck cardStack;
    public Context context = this;
    private SwipeDeckAdapter swipeDeckAdapter;
    private ArrayList<Article> articles = new ArrayList<>();
    public static int correctSwipes;

    /**
     * Initializes the RecyclerView and MovieAdapters when the app is first run.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // code from Android documentation
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        swipeDeckAdapter = new SwipeDeckAdapter(articles, this);

        if(cardStack != null){
            cardStack.setAdapter(swipeDeckAdapter);
            correctSwipes = 0;
        }

        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {

            /**
             * The activity that happens when a card is swiped left. We get the article represented
             * by the stableId from the swipeDeckAdapter and use that to determine if it is real or fake.
             *
             * A toast message is outputted that shows if a user is correct or incorrect.
             *
             * @param stableId the position of the card in the SwipeDeck
             */
            Toast fakeCorrectToast = Toast.makeText(context, "Correct! The article is fake", Toast.LENGTH_SHORT);
            Toast fakeIncorrectToast = Toast.makeText(context, "Incorrect! The article is real", Toast.LENGTH_SHORT);
            Toast realCorrectToast = Toast.makeText(context, "Correct! The article is real", Toast.LENGTH_SHORT);
            Toast realIncorrectToast = Toast.makeText(context, "Incorrect! The article is fake", Toast.LENGTH_SHORT);

            @Override
            public void cardSwipedLeft(long stableId) {
                final Article article = swipeDeckAdapter.getItem((int) stableId);
                final String title = article.getStrippedTitle();
                final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Articles/" + title);
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String,Object> map = ((HashMap<String,Object>) dataSnapshot.getValue());
                        long fakeSwipesLong = (long) map.get("fakeSwipes");
                        int fakeSwipes = (int) fakeSwipesLong;
                        fakeSwipes++;
                        Map<String, Object> userUpdates = new HashMap<String, Object>();
                        userUpdates.put("fakeSwipes", fakeSwipes);
                        database.updateChildren(userUpdates);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(article.isReal()){
                    fakeCorrectToast.cancel();
                    realCorrectToast.cancel();
                    realIncorrectToast.cancel();
                    fakeIncorrectToast.show();
                }
                else{
                    fakeIncorrectToast.cancel();
                    realIncorrectToast.cancel();
                    realCorrectToast.cancel();
                    fakeCorrectToast.show();
                    correctSwipes++;
                }
            }

            /**
             * The activity that happens when a card is swiped right. We get the article represented
             * by the stableId from the swipeDeckAdapter and use that to determine if it is real or fake.
             *
             * A toast message is outputted that shows if a user is correct or incorrect.
             *
             * @param stableId the position of the card in the SwipeDeck
             */
            @Override
            public void cardSwipedRight(long stableId) {
                final Article article = swipeDeckAdapter.getItem((int) stableId);
                final String title = article.getStrippedTitle();
                final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Articles/" + title);
                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String,Object> map = ((HashMap<String,Object>) dataSnapshot.getValue());
                        long realSwipesLong = (long) map.get("realSwipes");
                        int realSwipes = (int) realSwipesLong;
                        realSwipes++;
                        Map<String, Object> userUpdates = new HashMap<String, Object>();
                        userUpdates.put("realSwipes", realSwipes);
                        database.updateChildren(userUpdates);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(article.isReal()){
                    fakeCorrectToast.cancel();
                    fakeIncorrectToast.cancel();
                    realIncorrectToast.cancel();
                    realCorrectToast.show();
                    correctSwipes++;
                }
                else{
                    fakeIncorrectToast.cancel();
                    fakeCorrectToast.cancel();
                    realCorrectToast.cancel();
                    realIncorrectToast.show();
                }
            }

            /**
             * In the original implementation of this method from the GitHub repo, a card's dragging
             * ability could be enabled or disabled with a checkbox. I decided to delete the checkbox
             * because I always wanted the cards to drag and did not want a user having that much
             * power in using the app.
             */
            @Override
            public boolean isDragEnabled(long itemId) {
                return true;
            }
        });

        cardStack.setLeftImage(R.id.fake_news_image);
        cardStack.setRightImage(R.id.real_news_image);

        // Constructs array of URL and requests data from array
        try {
            URL[] urlsToParse = new URL[urlStrings.length];
            for (int i = 0; i < urlStrings.length; i++){
                urlsToParse[i] = new URL(urlStrings[i]);
            }
            new ArticlesASyncTask(this).execute(urlsToParse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
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
                                "articles are real or fake. I got " + correctSwipes + " articles " +
                                "correct."))
                        .build();
                shareDialog.show(content);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void writeNewUser(final Article article) {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("Articles");
        final String title = article.getStrippedTitle();
        // code from Firebase website
        database.child(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // TODO: handle the case where the data already exists
                }
                else {
                    // TODO: handle the case where the data does not yet exist
                    database.child(title).child("fakeSwipes").setValue(0);
                    database.child(title).child("realSwipes").setValue(0);
                    database.child(title).child("title").setValue(article.getTitle());
                    database.child(title).child("isReal").setValue(article.isReal());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    /**
     * Parses the GSON objects from the multiple URLs.
     *
     * The following code is from the MovieApp project. It has been modified to take in Article
     * objects instead of Movie objects.
     */
    public class ArticlesASyncTask extends AsyncTask<URL, Void, ArrayList<Article>> {

        Context context;

        public ArticlesASyncTask(Context context){
            this.context = context;
        }

        // Runs in background
        @Override
        protected ArrayList<Article> doInBackground(URL... params) {
            try {
                ArrayList<Article> articles = new ArrayList<>();
                for (URL url: params){
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

                    Gson gson = new Gson();
                    ArticleCollection articleCollection = gson.fromJson(inputStreamReader, ArticleCollection.class);
                    if (articleCollection.getArticles() != null) {
                        Collections.addAll(articles, articleCollection.getArticles());
                    }
                }
                Collections.shuffle(articles); // randomizes the order of the articles that appear.
                return articles;

            } catch (IOException e) {
                Log.d("ArticlesAsyncTask", "Failed to get data from the network", e);
                return null;
            }
        }

        // Runs in the UI threat
        @Override
        protected void onPostExecute(ArrayList<Article> parsedArticles){

            // If there are no articles in the articles array,
            if (articles == null){
                Toast.makeText(context, "Failed to get network data", Toast.LENGTH_LONG).show();
                return;
            }

            // Adds every movie to the articles array
            int num = 1;
            for (Article article : parsedArticles){
                articles.add(article);
                writeNewUser(article);
                Log.d("Article", article.getTitle());
                Log.d("Source", article.getNewsSource());
                //Log.d("URL", article.getUrl());
                Log.d("number", String.valueOf(num));
                num++;
            }
            swipeDeckAdapter.notifyDataSetChanged();
        }
    }
}