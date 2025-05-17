package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import pages.YouTubeHomePage;
import pages.SearchResultsPage;
import pages.VideoPage;

import java.util.List;


public class YouTubeTests extends BaseTest {
    private static final Logger logger = LogManager.getLogger(YouTubeTests.class);

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][] {
                { "Selenium Grid tutorial" },
                { "TestNG parallel tests" }
        };
    }

    @Test(dataProvider = "searchQueries", description = "Verify that searching for a video returns results")
    public void testSearchFunctionality(String query) {
        logger.info("Searching for a video returns results");
        YouTubeHomePage home = new YouTubeHomePage(getDriver());
        List<WebElement> resultsLink =  home.searchFor(query).resultItems();

        logger.info("Search results found {}", resultsLink.size());
        Assert.assertNotNull(resultsLink);
    }

    @Test(dataProvider = "searchQueries", description = "Verify that a video can be played and paused")
    public void testVideoPlaybackControls(String query) throws InterruptedException {

    }

    @Test(description = "Verify that video quality can be changed")
    public void testChangeVideoQuality() throws InterruptedException {
    }
}
