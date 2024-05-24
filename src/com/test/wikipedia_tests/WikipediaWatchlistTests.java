package com.test.wikipedia_tests;

import com.test.page_objects.WikipediaHomePage;
import com.test.page_objects.WikipediaLoginPage;
import com.test.page_objects.WikipediaUserHomePage;
import com.test.page_objects.WikipediaWatchlistPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/* 1. Launch Chrome browser and open wikipedia link
 *  2. Login into wikipedia
 * 3. Search for an article and add to watchlist
 * 4. Search and add a second article to the watchlist
 * 5. Go to watchlist page and remove one article
 * 6. Assert the second article is still present, assert the article title */

public class WikipediaWatchlistTests {
    static WebDriver driver;
    static WikipediaHomePage wikipediaHomePage;
    static WikipediaUserHomePage wikipediaUserHomePage;
    static WikipediaLoginPage wikipediaLoginPage;
    static WikipediaWatchlistPage wikipediaWatchlistPage;

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void testWikipediaLogin_assertUserHomePage() {
        wikipediaHomePage = new WikipediaHomePage(driver);
        wikipediaLoginPage = wikipediaHomePage.navigateToLoginPage();
        //Hardcoded the username and password for simplicity , not an ideal way of doing it
        wikipediaUserHomePage = wikipediaLoginPage.loginUser("Sirisha Pabbiti", "ABCabc123");
    }

    @Test(dependsOnMethods = {"testWikipediaLogin_assertUserHomePage"})
    public void testWikipediaAddToWatchList_assertArticleAdded() {
        wikipediaUserHomePage = new WikipediaUserHomePage(driver, "Sirisha Pabbiti");
        // search and add article 1 to the watch list
        wikipediaUserHomePage.searchArticle("Selenium");
        wikipediaUserHomePage.addArticleToWatchlist();
        // search and add article 2 to the watch list
        wikipediaUserHomePage.searchArticle("TestNG");
        wikipediaUserHomePage.addArticleToWatchlist();
        //navigate to watch list page to assert article added
        wikipediaWatchlistPage = wikipediaUserHomePage.navigateToWatchlistPage();
        wikipediaWatchlistPage.assertWatchListContains("Selenium");
    }

    @Test(dependsOnMethods = {"testWikipediaLogin_assertUserHomePage", "testWikipediaAddToWatchList_assertArticleAdded"})
    public void testRemoveOneArticleFromWatchList_assertSecondArticleIsVisible() {
        //remove article 1 from the watchlist
        wikipediaWatchlistPage.removeArticleFromWatchList("Selenium");
        //assert article 2 is still present in the watchlist
        wikipediaWatchlistPage.assertWatchListContains("TestNG");
    }

    @Test(dependsOnMethods = {"testWikipediaLogin_assertUserHomePage", "testWikipediaAddToWatchList_assertArticleAdded", "testRemoveOneArticleFromWatchList_assertSecondArticleIsVisible"})
    public void openArticleFromWatchList_assertPageTitleMatchesArticleName() {
        // Open article from watch list
        wikipediaWatchlistPage.openArticle("TestNG");
        //calling clear watch list to clear up the list for next executions
        wikipediaWatchlistPage.clearWatchList();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}

