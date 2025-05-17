package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseTest {
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    @BeforeMethod
    @Parameters({
            "browser",
            "browserVersion",
            "os",
            "osVersion",
            "BROWSERSTACK_BUILD_NAME",
            "BROWSERSTACK_PROJECT_NAME",
            "SELENIUM_GRID_URL",
            "mode"
    })
    public void setUp(String browser,
                      String browserVersion,
                      String os,
                      String osVersion,
                      String buildName,
                      String projectName,
                      String gridUrl,
                      @Optional("normal") String mode) throws Exception {

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability("browserName", browser);
        caps.setCapability("browserVersion", browserVersion);

        String userName = System.getenv("BROWSERSTACK_USERNAME");
        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");

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

        WebDriver driver = new RemoteWebDriver(new URL(gridUrl), caps);
        threadLocalDriver.set(driver);
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver d = getDriver();
        if (d != null) {
            try { d.quit(); } catch (Exception ignored) {}
            threadLocalDriver.remove();
        }
    }
}
