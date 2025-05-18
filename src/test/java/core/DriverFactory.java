package core;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


public class DriverFactory {



    public static WebDriver createDriver(String browserType, String gridUrl, String mode, String platform) throws MalformedURLException {
        switch (browserType.toLowerCase()) {
            case "chrome" -> {
                return createChromeDriver(gridUrl, mode, platform);
            }
            case "firefox" -> {
                return createFirefoxDriver(gridUrl, mode, platform);
            }
            default -> throw new IllegalArgumentException("Not a valid browser type: " + browserType);
        }
    }

    private static WebDriver createChromeDriver(String gridUrl, String mode, String platform) throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();

        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download", false);
        chromePrefs.put("download.directory_upgrade", true);
        chromePrefs.put("safebrowsing.enabled", true);
        chromePrefs.put("os", platform);
        options.setExperimentalOption("prefs", chromePrefs);


        if ("headless".equalsIgnoreCase(mode)) {
            options.addArguments("--headless", "--disable-gpu");
        }

        options.addArguments("--start-maximized");
        URL url = URI.create(gridUrl).toURL();
        return new RemoteWebDriver(url, options);
    }

    private static WebDriver createFirefoxDriver(String gridUrl, String mode, String platform) throws MalformedURLException {    FirefoxOptions options = new FirefoxOptions();

        if ("headless".equalsIgnoreCase(mode)) {
            options.addArguments("--headless");
        }

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
        caps.setCapability("platformName", platform);

        URL url = URI.create(gridUrl).toURL();
        return new RemoteWebDriver(url, caps);
    }
}
