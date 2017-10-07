package com.example.ajay.swipedeck2;

import android.content.Context;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ajay on 5/2/17.
 */

public class ExampleTest {
    Article apArticle = new Article("Associated Press",
            "http://bigstory.ap.org/article/d1b744ed2e2443129fadb62e96f59faa/trump-us-needs-september-shutdown-fix-senate-mess");
    Article bbcArticle = new Article("BBC", "http://www.bbc.co.uk/news/uk-politics-39784170");
    Article cnnArticle = new Article("CNN", "http://www.cnn.com/2017/05/02/us/michael-slager-federal-plea/index.html");
    Article huffingtonPostArticle = new Article("Huffington Post",
            "http://www.huffingtonpost.com/entry/paris-agreement_us_5908f524e4b05c397683d404");
    Article newYorkTimesArticle = new Article("New York Times",
            "https://www.nytimes.com/2017/05/02/arts/television/jimmy-kimmel-baby-son-wife.html");
    Article reutersArticle = new Article("Reuters",
            "http://www.reuters.com/article/us-northkorea-nuclear-un-idUSKBN17Y2AG");

    Article activistPostArticle = new Article("Activist Post",
            "http://www.activistpost.com/2017/05/congress-agrees-give-jeff-sessions-0-wage-war-medical-marijuana.html");
    Article americanNewsArticle = new Article("American News",
            "http://americannews.com/dianne-feinstein-just-payed-price-big-time-helping-illegals-mistake-lifetime/");
    Article breitbartArticle = new Article("Breitbart",
            "http://www.breitbart.com/video/2017/05/02/limbaugh-pence-point-voting-republican-democrats-going-continue-win/");
    Article buzzfeedusaArticle = new Article("BuzzFeedUSA",
            "http://buzzfeedusa.com/guy-messed-wrong-guys-wife-boom-check-happens-just-seconds/");
    Article conservativeDailyPostArticle = new Article("Conservative Daily Post",
            "https://conservativedailypost.com/slashed-target-ceo-salary-axed-company-stocks-plummet-12-straight-months/");
    Article infowarsArticle = new Article("Infowars",
            "http://www.infowars.com/trump-putin-discuss-syria-cooperation-in-phone-call/");

    @Test
    public void assertRealNewsSources(){
        assertTrue(apArticle.isReal());
        assertTrue(bbcArticle.isReal());
        assertTrue(cnnArticle.isReal());
        assertTrue(huffingtonPostArticle.isReal());
        assertTrue(newYorkTimesArticle.isReal());
        assertTrue(reutersArticle.isReal());
    }

    @Test
    public void assertFakeNewsSources(){
        assertFalse(activistPostArticle.isReal());
        assertFalse(americanNewsArticle.isReal());
        assertFalse(breitbartArticle.isReal());
        assertFalse(buzzfeedusaArticle.isReal());
        assertFalse(conservativeDailyPostArticle.isReal());
        assertFalse(infowarsArticle.isReal());
    }

    @Test
    public void assertSetAndGetSwipeMethods(){
        cnnArticle.setLeftSwipes(5);
        assertEquals(cnnArticle.getLeftSwipes(), 5);
        cnnArticle.setRightSwipes(173897);
        assertEquals(cnnArticle.getRightSwipes(), 173897);
    }

    @Test
    public void assertStrippedTitle(){
        Article strippedTitleArticle = new Article("$10[0#0 debt.$#[]", "https://google.com");
        assertEquals(strippedTitleArticle.getStrippedTitle(), "1000 debt");
    }

    @Test
    public void assertIsNotFromRSSFeed(){
        assertFalse(apArticle.isFromRSSFeed());
        assertFalse(breitbartArticle.isFromRSSFeed());
        assertFalse(bbcArticle.isFromRSSFeed());
        assertFalse(cnnArticle.isFromRSSFeed());
        assertFalse(huffingtonPostArticle.isFromRSSFeed());
        assertFalse(newYorkTimesArticle.isFromRSSFeed());
        assertFalse(reutersArticle.isFromRSSFeed());
    }

}


