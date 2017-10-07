package com.example.ajay.swipedeck2;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

import android.util.Log;

import org.apache.tools.ant.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ajay on 3/25/17.
 */

public class Article implements Parcelable{
    private String title; // The JSONs that we are using all use title for the article title.
    private String url; // The JSONs from NewsApi use url to represent the URL to the article.

    // The Infowars JSON uses link as the tag for the article URL instead of URL
    // As such, another String variable link is needed here.
    private String link;
    private String urlToImage;
    private String description;
    private static final String ABCAUSTRALIA = "ABC Australia";
    private static final String AMERICANNEWS = "American News";
    private static final String ALJAZEERA = "Al Jazeera";
    private static final String ASSOCIATEDPRESS = "Associated Press";
    private static final String ACTIVISTPOST = "Activist Post";
    private static final String ANONEWS = "ANONEWS";
    private static final String ALTERNATIVEMEDIASYNDICATE = "Alternative Media Syndicate";
    private static final String BBC = "BBC";
    private static final String BLOOMBERG = "Bloomberg";
    private static final String BREITBART = "Breitbart";
    private static final String BUZZFEEDUSA = "BuzzFeedUSA";
    private static final String BIPARTISANREPORT = "Bipartisan Report";
    private static final String CALLTHECOPS = "Call The Cops";
    private static final String CANADAFREEPRESS = "Canada Free Press";
    private static final String CBSNEWSCO = "CBSNEWSCO";
    private static final String CHRONICLE = "CHRONICLE";
    private static final String CLASHDAILY = "Clash Daily";
    private static final String CNBC = "CNBC";
    private static final String CNN = "CNN";
    private static final String CNSNEWS = "CNS News";
    private static final String CONSERVATIVEFIGHTERS = "Conservative Fighters";
    private static final String CONSERVATIVEDAILYPOST = "Conservative Daily Post";
    private static final String DAILYHEADLINES = "Daily Headlines";
    private static final String DAILYUSAUPDATE = "Daily USA Update";
    private static final String DAVIDDUKE = "David Duke";
    private static final String DAVIDWOLFE = "David Wolfe";
    private static final String DCCLOTHESLINE = "DC Clothes Line";
    private static final String DINEAL = "Dineal";
    private static final String DOWNTREND = "Downtrend";
    private static final String EAGLERISING = "Eagle Rising";
    private static final String EMPIRENEWS = "Empire News";
    private static final String ENDOFTHEAMERICANDREAM = "End of the American Dream";
    private static final String EUTIMES = "EU Times";
    private static final String FLASHNEWSCORNER = "Flash News Corner";
    private static final String FORTUNE = "Fortune";
    private static final String GOOGLENEWS = "Google";
    private static final String HUFFINGTONPOST = "Huffington Post";
    private static final String INFOWARS = "Infowars";
    private static final String NEWSWEEK = "Newsweek";
    private static final String NEWYORKTIMES = "New York Times";
    private static final String RECODE = "Recode";
    private static final String REUTERS = "Reuters";
    private static final String TIME = "Time";
    private static final String USATODAY = "USA Today";
    private static final String WASHINGTONPOST = "Washington Post";
    private static List<String> realNewsSources = new ArrayList<>(Arrays.asList(ABCAUSTRALIA,
            ALJAZEERA, ASSOCIATEDPRESS, BBC, BLOOMBERG, CNN, CNBC, GOOGLENEWS, FORTUNE,
            HUFFINGTONPOST, NEWSWEEK, NEWYORKTIMES, REUTERS, RECODE, TIME, USATODAY, WASHINGTONPOST));
    private static List<String> newsSourcesFromRSS = new ArrayList<>(Arrays.asList(ACTIVISTPOST,
            ALTERNATIVEMEDIASYNDICATE, AMERICANNEWS, ANONEWS, BIPARTISANREPORT,
            BUZZFEEDUSA, CALLTHECOPS, CANADAFREEPRESS,
            CBSNEWSCO, CHRONICLE, CLASHDAILY, CNSNEWS, CONSERVATIVEFIGHTERS,
            CONSERVATIVEDAILYPOST, DAILYHEADLINES, DAILYUSAUPDATE, DAVIDDUKE, DAVIDWOLFE,
            DCCLOTHESLINE, DINEAL, DOWNTREND, EAGLERISING, EMPIRENEWS, ENDOFTHEAMERICANDREAM,
            EUTIMES, FLASHNEWSCORNER, INFOWARS));

    private int leftSwipes;
    private int rightSwipes;
    private boolean isRSS;
    private String source = getNewsSource();

    public void setImageLink(String urlToImage){
        this.urlToImage = urlToImage;
    }

    public int getLeftSwipes(){
        return leftSwipes;
    }

    public int getRightSwipes(){
        return rightSwipes;
    }

    public void setLeftSwipes(int leftSwipes) {
        this.leftSwipes = leftSwipes;
    }

    public void setRightSwipes(int rightSwipes) {
        this.rightSwipes = rightSwipes;
    }

    public void setIsRSS(boolean isRSS){
        this.isRSS = isRSS;
    }

    /**
     * Because Firebase cannot accept Strings with certain characters in a path, the title must
     * be stripped of certain characters in order to be used in the database with the schema
     * that is currently used.
     * @return the stripped title
     */
    public String getStrippedTitle(){
        String strippedTitle = this.getTitle();
        strippedTitle = strippedTitle.replace(".", "");
        strippedTitle = strippedTitle.replace("$", "");
        strippedTitle = strippedTitle.replace("#", "");
        strippedTitle = strippedTitle.replace("[", "");
        strippedTitle = strippedTitle.replace("]", "");
        return strippedTitle;
    }

    /**
     *
     * @return if a news sources comes from RSS2JSON
     */
    public boolean isFromRSSFeed(){
        if (newsSourcesFromRSS.contains(this.getNewsSource())){
            return true;
        }
        return false;
    }

    /**
     *
     */
    public boolean isFromRSSFeedParcelable(){
        if (this.isRSS){
            return true;
        }
        return false;
    }

    /**
     * Returns if an article is real or fake based on the news source that it comes from.
     * @return if the article is real or fake.
     */
    public boolean isReal(){
        if (realNewsSources.contains(this.getNewsSource())){
            return true;
        }
        return false;
    }

    /**
     * Gets the News Source for an article by looking at the URL that it comes from. CNN articles
     * will have a www.cnn.com domain, Breitbart articles will have a www.breitbart.com domain name,
     * etc.
     * @return The news source for an article
     */
    public String getNewsSource(){
        if (url != null) {
            if (url.contains("abc.net.au")) {
                return ABCAUSTRALIA;
            }
            if (url.contains("aljazeera.com")){
                return ALJAZEERA;
            }
            if (url.contains("apnews.com")) {
                return ASSOCIATEDPRESS;
            }
            if (url.contains("www.bbc.co.uk")) {
                return BBC;
            }
            if (url.contains("bloomberg.com")) {
                return BLOOMBERG;
            }
            if (url.contains("www.breitbart.com")) {
                return BREITBART;
            }
            if (url.contains("www.cnbc.com")) {
                return CNN;
            }
            if (url.contains("www.cnn.com")) {
                return CNN;
            }
            if (url.contains("fortune.com")){
                return FORTUNE;
            }
            if (url.contains("www.huffingtonpost.com")) {
                return HUFFINGTONPOST;
            }
            if (url.contains("www.newsweek.com")){
                return NEWSWEEK;
            }
            if (url.contains("www.nytimes.com")) {
                return NEWYORKTIMES;
            }
            if (url.contains("www.recode.net")){
                return RECODE;
            }
            if (url.contains("www.reuters.com")){
                return REUTERS;
            }
            if (url.contains("www.time.com")){
                return TIME;
            }
            if (url.contains("www.usatoday.com")){
                return USATODAY;
            }
            if (url.contains("www.washingtonpost.com")){
                return WASHINGTONPOST;
            }
        }

        // The only JSON that uses link for its URL to the article is Infowars. Therefore, we can
        // determine that if there is a URL represented by the link variable (link isn't null),
        // then it must be an Infowars article
        else if (link != null){
            if (link.contains("activistpost")) {
                return ACTIVISTPOST;
            }
            if (link.contains("alternativemediasyndicate")){
                return ALTERNATIVEMEDIASYNDICATE;
            }
            if (link.contains("americannews")){
                return AMERICANNEWS;
            }
            if (link.contains("anonews")){
                return ANONEWS;
            }
            if (link.contains("bipartisanreport.com")){
                return BIPARTISANREPORT;
            }
            if (link.contains("buzzfeedusa")){
                return BUZZFEEDUSA;
            }
            if (link.contains("callthecops")){
                return CALLTHECOPS;
            }
            if (link.contains("canadafreepress")){
                return CANADAFREEPRESS;
            }
            if (link.contains("cbsnews")){
                return CBSNEWSCO;
            }
            if (link.contains("chronicle.su")){
                return CHRONICLE;
            }
            if (link.contains("clashdaily")){
                return CLASHDAILY;
            }
            if (link.contains("cnsnews")){
                return CNSNEWS;
            }
            if (link.contains("conservativedailypost.com")){
                return CONSERVATIVEDAILYPOST;
            }
            if (link.contains("conservativefighters")){
                return CONSERVATIVEFIGHTERS;
            }
            if (link.contains("dailyheadlines")){
                return DAILYHEADLINES;
            }
            if (link.contains("dailyusaupdate")){
                return DAILYUSAUPDATE;
            }
            if (link.contains("davidduke.com")){
                return DAVIDDUKE;
            }
            if (link.contains("dcclothesline.com")){
                return DCCLOTHESLINE;
            }
            if (link.contains("davidwolfe.com")){
                return DAVIDWOLFE;
            }
            if (link.contains("dineal")){
                return DINEAL;
            }
            if (link.contains("downtrend.com")){
                return DOWNTREND;
            }
            if (link.contains("eaglerising.com")){
                return EAGLERISING;
            }
            if (link.contains("empirenews.net")){
                return EMPIRENEWS;
            }
            if (link.contains("endoftheamericandream.com")){
                return ENDOFTHEAMERICANDREAM;
            }
            if (link.contains("eutimes.net")){
                return EUTIMES;
            }
            if (link.contains("flashnewscorner")){
                return FLASHNEWSCORNER;
            }

            if (link.contains("www.infowars.com")) {
                return INFOWARS;
            }
        }
        return "";
    }

    /**
     * This method returns the title of an article. Because some articles have prefixes or suffixes
     * that include the news source (e.g. CNN Video or Breitbart Daily), the replace method is used
     * to remove these references.
     * @return the movie's title
     */
    public String getTitle(){
        if (this.getNewsSource().equals(BREITBART)){
            title = title.replace(BREITBART, "");
        }
        else if (this.getNewsSource().equals(CNN)){
            title = title.replace(CNN, "");
        }
        else if (this.getNewsSource().equals(INFOWARS)){
            title = title.replace(INFOWARS, "");
        }
        else if (this.getNewsSource().equals(REUTERS)){
            title = title.replace(REUTERS, "");
        }
        return title.replace(" - ", "");
    }

    /**
     *
     * @return the path to the image on the MovieDB website.
     */
    public String getImageLink(){
        return urlToImage;
    }

    public String getUrl() {
        if (this.newsSourcesFromRSS.contains(this.getNewsSource())) {
            return link;
        }
        else {
            return url;
        }
    }

    /**
     * Some articles had a description with the HTML tag in it, so in order to clean it up, Jsoup is used.
     * @return the description of the article
     */
    public String getDescription() {
        if (description != null){
            String formattedDescription = Jsoup.parse(description).text().split("The post " + title)[0]; // from StackOverflow
            if (getNewsSource().equals("Associated Press")) {
                formattedDescription = formattedDescription.substring(formattedDescription.lastIndexOf("(AP)") + 1);
                formattedDescription = formattedDescription.replace("AP) -", "");
                if (formattedDescription.charAt(0) == '-'){
                    formattedDescription = formattedDescription.substring(2, formattedDescription.length());
                }
                else if (formattedDescription.charAt(0) == 'A'){
                    formattedDescription = formattedDescription.substring(5, formattedDescription.length());
                }
            }
            else if (getNewsSource().equals("Conservative Daily Post")) {
                formattedDescription = formattedDescription.substring(0, formattedDescription.lastIndexOf("."));
            }
            else if (getNewsSource().equals("American News")) {
                formattedDescription = formattedDescription.substring(0, formattedDescription.indexOf(".", 0));

            }

            return formattedDescription.replace("[...]", "");
        }
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getTitle());
        dest.writeString(this.getDescription());
        dest.writeString(this.getImageLink());
        dest.writeByte((byte) (this.isFromRSSFeed() ? 1 : 0));
        //dest.writeString(String.valueOf(this.isFromRSSFeed()));
        //dest.writeByte((byte) (this.isFromRSSFeed() ? 1 : 0));
        dest.writeString(this.getUrl());
        dest.writeInt(this.getLeftSwipes());
        dest.writeInt(this.getRightSwipes());


    }

    public Article(String title, String url){
        this.title = title;
        this.url = url;
    }

    protected Article(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.urlToImage = in.readString();
        this.isRSS = in.readByte() != 0;
        //this.isRSSparcelable = in.readString();
        this.url = in.readString();
        this.link = in.readString();
        this.leftSwipes = in.readInt();
        this.rightSwipes = in.readInt();
        this.source = in.readString();
    }
    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
