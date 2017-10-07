package com.example.ajay.swipedeck2;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by Ajay on 4/15/17.
 */

public class GetImageURLAsyncTask extends AsyncTask<Void, Void, String> {

    private final Context context;
    private final ImageView imageView;
    private final String url;
    private Article article;
    private String source;
    private final int INDEXOFBUZZFEEDUSAIMAGE = 1;

    public GetImageURLAsyncTask(Context context, ImageView imageView, Article article) {
        this.context = context;
        this.imageView = imageView;
        this.url = article.getUrl();
        this.article = article;
        this.source = article.getNewsSource();
    }

    @Override
    protected String doInBackground(Void... params) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element imageElement;
        if (source.equals("Infowars")) {
            imageElement = document.select("img[class*=landscape thumbnail full]").first();
        }
        else if (source.equals("American News")) {
            imageElement = document.select("img[class*=attachment-modern_big_thumb]").first();
        }
        else if (source.equals("Conservative Daily Post")) {
            imageElement = document.select("img[class*=wp-image]").first();
        }
        else if (source.equals("BuzzFeedUSA")) {
            imageElement = document.select("img").get(INDEXOFBUZZFEEDUSAIMAGE);
        }
        else{
            imageElement = document.select("img").first();
            if (imageElement.absUrl("src").equals("https://conservativedailypost-guvbvzsunddro8yrw.netdna-ssl.com/wp-content/uploads/2016/09/header_logo_ab122c6df011cd4ab16e56dee347ae17.png")){
                imageElement = document.select("img[class*=wp-image]").first();
            }
            else if (imageElement.absUrl("src").contains("infowars.com")){
                imageElement = document.select("img[class*=landscape thumbnail full]").first();
            }
            else if (imageElement.absUrl("src").equals("http://americannews.com/wp-content/uploads/2016/10/amnewslogo.jpg")){
                imageElement = document.select("img[class*=attachment-modern_big_thumb]").first();
            }
            else if (imageElement.absUrl("src").equals("http://buzzfeedusa.com/wp-content/uploads/2016/10/buzzfeedusa11.jpg")){
                imageElement = document.select("img").get(INDEXOFBUZZFEEDUSAIMAGE);
            }
        }
        if (imageElement != null) {
            return imageElement.absUrl("src");  //absolute URL on src
        }
        else{
            return "http://ampthemag.com/wp-content/uploads/2016/05/nbc-fires-donald-trump-after-he-calls-mexicans-rapists-and-drug-runners.jpg";
        }
    }

    @Override
    protected void onPostExecute(String imageURL) {
        Picasso.with(context).load(imageURL).into(imageView);
    }
}
