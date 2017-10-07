package com.example.ajay.swipedeck2;

/**
 * Created by Ajay on 3/25/17.
 */

public class ArticleCollection {
    private Article[] items; // items is used in the Infowars JSON to designate the array of articles
    private Article[] articles; // articles if used in the NewsApi JSONs to designate the array of articles

    public Article[] getArticles(){

        // returns articles if the JSON's articles are represented by the articles tag
        // otherwise returns articles represented by the items tag
        if (articles != null){
            return articles;
        }
        return items;
    }
}
