
package utilityScripts;

import java.time.Duration;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;

public class BrowserSetup {

	public enum Browser {FIREFOX, CHROME, EDGE, IEXPLORE, SAFARI} ; 
	Browser browser;	
	public static WebDriver wd, webDriver;
	
	public BrowserSetup(Browser browser, Boolean NoBrowser, Boolean Incognito) throws Exception{
		this.browser=browser;
		
		UIMap.loadUIMapProp();
		UIMap.readProperties();
//		
		try {
			switch(browser) {
			case FIREFOX: 
				System.setProperty("webdriver.gecko.driver", UIMap.firefoxPath);
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
				firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//				if (NoBrowser==true) firefoxOptions.setHeadless(true);
				if (NoBrowser==true) firefoxOptions.addArguments("--headless");
				if (Incognito==true) firefoxOptions.addArguments("-private");
				webDriver= new FirefoxDriver(firefoxOptions); break;
			case CHROME:
				System.setProperty("webdriver.chrome.driver", UIMap.chromePath);
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
				chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
				if (NoBrowser==true) chromeOptions.addArguments("--headless");
				if (Incognito==true) chromeOptions.addArguments("--incognito");
				webDriver= new ChromeDriver(chromeOptions);break;
			case EDGE:
				System.setProperty("webdriver.edge.driver", UIMap.edgePath);
				EdgeOptions edgeOptions = new EdgeOptions();
				edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				if (NoBrowser==true) edgeOptions.addArguments("--headless");
				if (Incognito==true) edgeOptions.addArguments("--incognito");
				webDriver= new EdgeDriver(edgeOptions);break;
			default:
				throw new Exception("Browser type unsupported or Driver for the browser " + browser + " does not exist");
			}
		}catch(Exception e) {
			throw new RuntimeException("Browser type unsupported or Driver for the browser " + browser + " does not exist or browser not found");
		}	
		WebDriverListener browserListener= new BrowserListener();
		wd= new EventFiringDecorator<WebDriver>(browserListener).decorate(webDriver);

	}
	
	public String getBrowser() {
		return browser.name();
	}
	
	public static  WebDriver getDriver() {
		return wd;
	}
	public WebDriver invokeBrowser(String url)  {
		wd.manage().window().maximize();
		wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));	
		wd.get(url);
		return wd;
	}
	public void setPageLoadTimeout(Integer timeInSeconds) {
		wd.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeInSeconds));
	}
	public void closeBrowser() {
		wd.quit();
	}
}