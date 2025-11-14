package utilityScripts;

import java.util.Arrays;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class BrowserListener implements WebDriverListener {
	WebDriver wd;
	public void afterGet(WebDriver driver, String url) {
		MessageLogger.info("(BrowserListener)Browser opened : " +((RemoteWebDriver) driver).getCapabilities().getBrowserName() + "-"+((RemoteWebDriver) driver).getCapabilities().getBrowserVersion());
		MessageLogger.info("(BrowserListener)Navigated to : " + url);
	}
	public void afterGetTitle(WebDriver driver, String result) {
		MessageLogger.info("((BrowserListener)Page title : " + result);
	}
	public void afterQuit(WebDriver driver) {
		MessageLogger.info("(BrowserListener)Browser closed : " + ((RemoteWebDriver) driver).getCapabilities().getBrowserName() + "-"+((RemoteWebDriver) driver).getCapabilities().getBrowserVersion());
	}
	public void beforeClick(WebElement element) {
		//wd = BrowserSetup.wd;
		wd=BrowserSetup.getDriver();
		JavascriptExecutor js = (JavascriptExecutor) wd;
		MessageLogger.info("(BrowserListener)clicking:" + element.getAttribute("value"));
		js.executeScript("arguments[0].setAttribute('style', 'background: orange; border: 2px solid blue;');", element);
	}
	public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
		wd = BrowserSetup.wd;
		JavascriptExecutor js = (JavascriptExecutor) wd;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid blue;');", element);
	}
	public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
		MessageLogger.info("(BrowserListener)Entered data " + Arrays.toString(keysToSend) + " into " + element.getAttribute("name"));
	}

}
