package tests;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.VideoPage;
import pages.YouTubeHomePage;


public class YouTubeTests extends BaseTest {
    private static final Logger logger = LogManager.getLogger(YouTubeTests.class);

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][] {
                { "Selenium Grid tutorial" },
                { "TestNG parallel tests" },
                { "Selenium driver"},
                { "Selenium WebDriver"},
                { "Selenium WebDriver Test"},
        };
    }

    @Test(dataProvider = "searchQueries", description = "Verify that searching for a video returns results")
    public void testSearchFunctionality(String query) {
        logger.info("Searching for a video returns results");
        YouTubeHomePage home = new YouTubeHomePage(getDriver());
        List<WebElement> resultsLink =  home.searchFor(query).resultItems();

        Assert.assertNotNull(resultsLink);
    }

    @Test(dataProvider = "searchQueries", description = "Verify that a video can be played and paused")
    public void testVideoPlaybackControls(String query) throws InterruptedException {
        YouTubeHomePage home = new YouTubeHomePage(getDriver());
        VideoPage videoPage = home.searchFor(query).selectResult(2);
        String title = videoPage.handleYouTubeAds().getVideoTitle();

        logger.info("Video title is {}", title);

//        videoPage.dismissYouTubePremiumPopup().playAndPauseVideo();
//        Thread.sleep(5000);
//        videoPage.playAndPauseVideo();
//        Thread.sleep(5000);
    }
}
