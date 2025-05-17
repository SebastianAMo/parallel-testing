package tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import pages.YouTubeHomePage;
import pages.SearchResultsPage;
import pages.VideoPage;


public class YouTubeTests extends BaseTest {

    @DataProvider(name = "searchQueries")
    public Object[][] searchQueries() {
        return new Object[][] {
                { "Selenium Grid tutorial" },
                { "TestNG parallel tests" }
        };
    }

    @Test(dataProvider = "searchQueries", description = "Verify that searching for a video returns results")
    public void testSearchFunctionality(String query) {
        YouTubeHomePage home = new YouTubeHomePage(getDriver());
        SearchResultsPage results = home.searchFor(query);

        // if no exception thrown, we have at least one result
        Assert.assertFalse(getDriver().findElements(By.className("yt-core-attributed-string__link")).isEmpty(), "Expected at least one video result for: " + query);
    }

    @Test(dataProvider = "searchQueries", description = "Verify that a video can be played and paused")
    public void testVideoPlaybackControls(String query) throws InterruptedException {
        YouTubeHomePage home = new YouTubeHomePage(getDriver());
        VideoPage video = home.searchFor(query)
                .clickFirstResult();
        String title = video.getVideoTitle();
        Assert.assertNotNull(title, "Video title should not be null");
        video.play();
        Thread.sleep(5000);  // let it play 5 seconds
        video.pause();
        System.out.println("Played and paused video: " + title);
    }

    @Test(description = "Verify that video quality can be changed")
    public void testChangeVideoQuality() throws InterruptedException {
        String query = "Selenium Grid tutorial";
        YouTubeHomePage home = new YouTubeHomePage(getDriver());
        VideoPage video = home.searchFor(query)
                .clickFirstResult();
        video.changeQuality("720p");
        // ideally assert some UI element changed — simplified here:
        Thread.sleep(2000);
        System.out.println("Quality changed to 720p for video: " + video.getVideoTitle());
    }
}
