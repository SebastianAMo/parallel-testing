package core;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;


public class DriverFactory {
    private static final Logger logger = LogManager.getLogger(DriverFactory.class);

    public static WebDriver createDriver(String browser,
                                         String browserVersion,
                                         String os,
                                         String osVersion,
                                         String buildName,
                                         String projectName,
                                         String mode) throws MalformedURLException {
        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("browserName", browser);
        caps.setCapability("browserVersion", browserVersion);

        String userName = System.getenv("BROWSERSTACK_USERNAME");
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        logger.info("BROWSERSTACK_USERNAME: {}", userName);
        logger.info("BROWSERSTACK_ACCESS_KEY: {}", accessKey);

        Map<String, Object> bsOpts = new HashMap<>();
        bsOpts.put("userName", userName);
        bsOpts.put("accessKey", accessKey);
        bsOpts.put("buildName", buildName);
        bsOpts.put("projectName", projectName);
        bsOpts.put("local", false);
        bsOpts.put("os", os);
        bsOpts.put("osVersion", osVersion);

        caps.setCapability("bstack:options", bsOpts);

        if ("chrome".equalsIgnoreCase(browser)) {
            Map<String,Object> chromePrefs = new HashMap<>();
            chromePrefs.put("download.default_directory",
                    Paths.get(System.getProperty("user.home"), "Downloads").toString());
            caps.setCapability("goog:chromeOptions", Map.of("args",
                    new String[]{ mode.equals("headless")?"--headless":"--start-maximized" },
                    "prefs", chromePrefs));
        } else {
            if ("headless".equalsIgnoreCase(mode)) {
                caps.setCapability("moz:firefoxOptions", Map.of("args", new String[]{"-headless"}));
            }
        }

        String gridUrl = "https://"+ userName + ":" + accessKey + "@hub-cloud.browserstack.com/wd/hub";

            URL url = URI.create(gridUrl).toURL();
        return new RemoteWebDriver(url, caps);
    }
}
