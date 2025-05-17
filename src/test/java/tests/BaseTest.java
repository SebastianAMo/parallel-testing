package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import core.DriverFactory;

public class BaseTest {
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
            "mode"
    })
    public void setUp(String browser,
                      String browserVersion,
                      String os,
                      String osVersion,
                      String buildName,
                      String projectName,
                      @Optional("normal") String mode) throws Exception {
        WebDriver driver = DriverFactory.createDriver(
                browser,
                browserVersion,
                os,
                osVersion,
                buildName,
                projectName,
                mode
        );
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
