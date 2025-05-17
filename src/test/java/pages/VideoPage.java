package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VideoPage extends BasePage {
    private final By playButton     = By.cssSelector("button.ytp-play-button");
    private final By settingsButton = By.cssSelector("button.ytp-button.ytp-settings-button");
    private final By titleLocator   = By.cssSelector("h1.title yt-formatted-string");
    private final String qualityXpathPattern =
            "//span[contains(text(),'%s')]";

    public VideoPage(WebDriver driver) {
        super(driver);
    }

    public String getVideoTitle() {
        return driver.findElement(titleLocator).getText();
    }

    public void play() {
        driver.findElement(playButton).click();
    }

    public void pause() {
        driver.findElement(playButton).click();
    }

    public void changeQuality(String qualityLabel) {
        driver.findElement(settingsButton).click();
        driver.findElement(By.xpath(
                        String.format(qualityXpathPattern, qualityLabel)))
                .click();
    }
}
