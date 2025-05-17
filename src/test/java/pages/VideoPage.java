package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.YouTubeTests;

import java.time.Duration;

public class VideoPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(VideoPage.class);
    private final By playButton     = By.cssSelector("button.ytp-play-button");
    private final By titleLocator   = By.cssSelector("#title h1 yt-formatted-string");
    private final By adsSkipButton = By.cssSelector("button.ytp-skip-ad-button");
    private final By adsContainer   = By.className("video-ads");
    private final By popupLocator = By.cssSelector("yt-mealbar-promo-renderer");
    private final By noThanksButtonLocator = By.cssSelector("yt-mealbar-promo-renderer #dismiss-button button");


    public VideoPage(WebDriver driver) {
        super(driver);
    }

    public String getVideoTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(titleLocator)).getText();
    }

    public VideoPage handleYouTubeAds() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(adsContainer));
            logger.info("Ad container detected, handling ads...");

            try {
                longWait.until(ExpectedConditions.elementToBeClickable(adsSkipButton))
                        .click();
                logger.info("Skipped ad with Skip button.");
            } catch (Exception e) {
                logger.info("No Skip button found, waiting for ad to finish...");

                longWait.until(ExpectedConditions.invisibilityOfElementLocated(adsContainer));
                logger.info("Ad finished.");
            }
        } catch (Exception e) {
            logger.info("No ads detected.");
        }
        return this;
    }

    public VideoPage playAndPauseVideo() {
        WebElement playButtonElement = wait.until(ExpectedConditions.elementToBeClickable(playButton));

        String ariaLabel = playButtonElement.getAttribute("aria-label");

        boolean isNull = ariaLabel == null;
        if (isNull && ariaLabel.toLowerCase().contains("pausa")) {
            logger.info("Play button pressed.");
        } else {
            logger.info("Pause button pressed.");
        }
        playButtonElement.click();
        return this;
    }

    public VideoPage dismissYouTubePremiumPopup() {
        try {
            fluentWait.until(ExpectedConditions.visibilityOfElementLocated(popupLocator));
            WebElement noThanksButton = fluentWait.until(ExpectedConditions.elementToBeClickable(noThanksButtonLocator));
            noThanksButton.click();
        } catch (Exception ignored) {
        }
        return this;
    }


}