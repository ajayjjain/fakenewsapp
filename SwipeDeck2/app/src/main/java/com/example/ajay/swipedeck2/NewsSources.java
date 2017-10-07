package com.example.ajay.swipedeck2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ajay on 4/11/17.
 */

public class NewsSources {

    /**
     * Currently, we are using four news sources for the Fake News App. Two of these are real
     * news sources (CNN and Reuters) and two of these are fake (Breitbart and Infowars).
     * More news sources can be added as the app will support more than four news sources.
     * This code gets JSONs from three of the four news sources (Breitbart, CNN, and Reuters) with
     * NewsApi. I would recommend getting legitimate news articles from sources that are supported
     * by NewsApi if you choose to add more articles. Example news sources supported by NewsApi
     * include Associated Press, BBC News, and The New York Times.
     *
     * In order to get the JSON from Infowars, I am taking the RSS feed provided to the public
     * by Infowars and inputting it into rss2json.com, which converts RSS feeds to JSON objects.
     * This process can be used for legitimate and fake news sources that are not supported by
     * NewsApi.
     */
    private static final String RSSJSONPREFIX = "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2F";
    private static final String RSSJSONSUFFIX = "%2Ffeed%2F&api_key=" + ApiKey.RSS_TO_JSON_KEY;
    private static final String NEWSAPIPREFIX = "https://newsapi.org/v1/articles?source=";
    private static final String NEWSAPISUFFIX = "&sortBy=top&apiKey=" + ApiKey.NEWS_API_KEY;

    private static final String AMERICANNEWS =
            RSSJSONPREFIX + "americannews.com" + RSSJSONSUFFIX;
    private static final String ABCNEWSAUSTRALIA =
            NEWSAPIPREFIX + "abc-news-au" + NEWSAPISUFFIX;
    private static final String ALJAZEERA =
            NEWSAPIPREFIX + "al-jazeera-english" + NEWSAPISUFFIX;
    private static final String ASSOCIATEDPRESS =
            NEWSAPIPREFIX + "associated-press" + NEWSAPISUFFIX;
    private static final String ACTIVISTPOST =
            "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Ffeeds.feedburner.com%2FActivistPost";
    private static final String ALTERNATIVEMEDIASYNDICATE =
            RSSJSONPREFIX + "alternativemediasyndicate.com" + RSSJSONSUFFIX;
    private static final String ANONEWS =
            RSSJSONPREFIX + "www.anonews.co" + RSSJSONSUFFIX;
    private static final String BBC =
            NEWSAPIPREFIX + "bbc-news" + NEWSAPISUFFIX;
    private static final String BLOOMBERG =
            NEWSAPIPREFIX + "bloomberg" + NEWSAPISUFFIX;
    private static final String BIPARTISANREPORT =
            RSSJSONPREFIX + "bipartisanreport.com" + RSSJSONSUFFIX;
    private static final String BREITBART =
            NEWSAPIPREFIX + "breitbart-news" + NEWSAPISUFFIX;
    private static final String BUZZFEEDUSA =
            RSSJSONPREFIX + "buzzfeedusa.com" + RSSJSONSUFFIX;
    private static final String CALLTHECOPS =
            RSSJSONPREFIX + "www.callthecops.net" + RSSJSONSUFFIX;
    private static final String CANADAFREEPRESS =
            //RSSJSONPREFIX + "canadafreepress.com" + RSSJSONSUFFIX;
            "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fcanadafreepress.com%2FCFP-RSS&api_key=4xomm9wclgh6esfsrtsuoas436leggxm3k26jqnb";
    private static final String CBSNEWSCO =
            "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fcbsnews.com.co%2Ffeed%2F";
    private static final String CHRONICLE =
            "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fchronicle.su%2Ffeed%2F";
    private static final String CLASHDAILY =
            RSSJSONPREFIX + "clashdaily.com" + RSSJSONSUFFIX;
    private static final String CNSNEWS =
            "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fwww.cnsnews.com%2Ffeeds%2Fall&api_key=4xomm9wclgh6esfsrtsuoas436leggxm3k26jqnb";
    private static final String CONSERVATIVEFIGHTERS =
            "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fconservativefighters.com%2Ffeed%2F";
    private static final String CONSERVATIVEDAILYPOST =
            RSSJSONPREFIX + "conservativedailypost.com" + RSSJSONSUFFIX;
    private static final String CNBC =
            NEWSAPIPREFIX + "cnbc" + NEWSAPISUFFIX;
    private static final String CNN =
            NEWSAPIPREFIX + "cnn" + NEWSAPISUFFIX;
    private static final String DAILYHEADLINES =
            RSSJSONPREFIX + "dailyheadlines.com" + RSSJSONSUFFIX;
    private static final String DAILYUSAUPDATE =
            RSSJSONPREFIX + "dailyusaupdate.com" + RSSJSONSUFFIX;
    private static final String DAVIDDUKE =
            RSSJSONPREFIX + "davidduke.com" + RSSJSONSUFFIX;
    private static final String DAVIDWOLFE =
            RSSJSONPREFIX + "davidwolfe.com" + RSSJSONSUFFIX;
    private static final String DCCLOTHESLINE =
            RSSJSONPREFIX + "www.dcclothesline.com" + RSSJSONSUFFIX;
    private static final String DINEAL =
            RSSJSONPREFIX + "dineal.com" + RSSJSONSUFFIX;
    private static final String DOWNTREND =
            RSSJSONPREFIX + "downtrend.com" + RSSJSONSUFFIX;
    private static final String EAGLERISING =
            RSSJSONPREFIX + "eaglerising.com" + RSSJSONSUFFIX;
    private static final String EMPIRENEWS =
            RSSJSONPREFIX + "empirenews.net" + RSSJSONSUFFIX;
    private static final String ENDOFTHEAMERICANDREAM =
            RSSJSONPREFIX + "endoftheamericandream.com" + RSSJSONSUFFIX;
    private static final String EUTIMES =
            RSSJSONPREFIX + "eutimes.net" + RSSJSONSUFFIX;
    private static final String FLASHNEWSCORNER =
            RSSJSONPREFIX + "flashnewscorner.com" + RSSJSONSUFFIX;
    private static final String FORTUNE =
            NEWSAPIPREFIX + "fortune" + NEWSAPISUFFIX;
    private static final String GOOGLENEWS =
            NEWSAPIPREFIX + "google-news" + NEWSAPISUFFIX;
    private static final String HUFFINGTONPOST =
            NEWSAPIPREFIX + "the-huffington-post" + NEWSAPISUFFIX;
    private static final String INFOWARS =
            RSSJSONPREFIX + "www.infowars.com" + RSSJSONSUFFIX;
    private static final String NEWSWEEK =
            NEWSAPIPREFIX + "newsweek" + NEWSAPISUFFIX;
    private static final String NEWYORKTIMES =
            NEWSAPIPREFIX + "the-new-york-times" + NEWSAPISUFFIX;
    private static final String REUTERS =
            NEWSAPIPREFIX + "reuters" + NEWSAPISUFFIX;
    private static final String RECODE =
            NEWSAPIPREFIX + "recode" + NEWSAPISUFFIX;
    private static final String TIME =
            NEWSAPIPREFIX + "time" + NEWSAPISUFFIX;
    private static final String USATODAY =
            NEWSAPIPREFIX + "usa-today" + NEWSAPISUFFIX;
    private static final String WASHINGTONPOST =
            NEWSAPIPREFIX + "the-washington-post" + NEWSAPISUFFIX;

    public static String[] getNewsSources(){
        String[] newsSources = {AMERICANNEWS, ABCNEWSAUSTRALIA, ALJAZEERA, ASSOCIATEDPRESS,
                ALTERNATIVEMEDIASYNDICATE, ANONEWS, BBC, BIPARTISANREPORT, BLOOMBERG, BREITBART, BUZZFEEDUSA,
                CANADAFREEPRESS, CBSNEWSCO, CHRONICLE, CNBC, CNN, CNSNEWS, CLASHDAILY,
                CONSERVATIVEDAILYPOST, CONSERVATIVEFIGHTERS,
                DAILYHEADLINES, DAILYUSAUPDATE, DAVIDDUKE, DAVIDWOLFE,
                DCCLOTHESLINE, DINEAL, DOWNTREND, EAGLERISING, EMPIRENEWS, ENDOFTHEAMERICANDREAM,
                EUTIMES, FORTUNE, FLASHNEWSCORNER, HUFFINGTONPOST, NEWSWEEK, NEWYORKTIMES,
                RECODE, TIME, USATODAY,
                WASHINGTONPOST};
        return newsSources;
    }
}
