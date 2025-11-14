package script;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utilityScripts.BrowserSetup;
import utilityScripts.BrowserSetup.Browser;
public class TestUserActions {
	WebDriver wd;	
	BrowserSetup b;

	@Parameters({"Browser"})
	@BeforeClass
	public void beforeClass(@Optional("firefox") String browser) throws Exception {
		if (browser.equalsIgnoreCase("chrome"))
			b= new BrowserSetup(Browser.CHROME, false,false);
		if (browser.equalsIgnoreCase("firefox"))
			b= new BrowserSetup(Browser.FIREFOX, false,false);
		if (browser.equalsIgnoreCase("edge"))
			b= new BrowserSetup(Browser.EDGE, false,false);
	}

	@AfterClass
	public void afterClass() {
	b.closeBrowser();
	}

	@Test(priority = 1)
	public void TestLaunchLambdatest() {
		wd= b.invokeBrowser("https://www.lambdatest.com/selenium-playground/");
		b.setPageLoadTimeout(10);
		WebElement captionElement = wd.findElement(By.xpath("//h1"));
		Assert.assertEquals(captionElement.getText(), "Selenium Playground");
	}

	@Test(priority = 2)
	public void TestMainMenu() throws InterruptedException {
		Actions mouse_actions = new Actions(wd);
		WebElement menu = wd.findElement(By.xpath("//button[contains(text(),'Resources')]"));
		mouse_actions.moveToElement(menu).perform();
		System.out.println("Mouse hover: " + menu.getText());
		Thread.sleep(2000);
		WebElement submenu = wd.findElement(By.xpath("//p[text()='Blog']"));
		mouse_actions.moveToElement(submenu).perform();
		System.out.println("Mouse hover: " + submenu.getText());
		Thread.sleep(1000);
		mouse_actions.click(submenu).perform();
		b.setPageLoadTimeout(10);
		WebElement caption = wd.findElement(By.cssSelector("h1.blog-head-titel"));
		System.out.println("caption: " + caption.getText());
//		Assert.assertEquals(caption.getText(), "LambdaTest Blogs");
		Assert.assertTrue(caption.getText().equalsIgnoreCase("LambdaTest Blogs"));
		
	}
	@Test(priority = 3, enabled = true)
	public void TestCommunityMenu() {
		wd.findElement(By.xpath("//a[(text()='Community') and @rel]")).click();
		wd.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(10)); 
		WebElement pageCaption = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='LambdaTest Community']")));
		System.out.println("pageCaption '" + pageCaption.getAttribute("alt") + "' located @" + pageCaption.getLocation());
		Assert.assertTrue(wd.getTitle().startsWith("LambdaTest Community"));
		wd.navigate().back();
	}
	
	@Test(priority = 4, enabled = false)
	public void TestCommunityMenuUsingJS() {
		WebElement linkCommunity=wd.findElement(By.xpath("//a[(text()='Community') and @rel]"));
		JavascriptExecutor js = (JavascriptExecutor)wd;
//		js.executeScript("arguments[0].style.border='3px dotted blue'", linkCommunity);
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid blue;');", linkCommunity);
		js.executeScript("arguments[0].click();", linkCommunity);
		WebDriverWait wait = new WebDriverWait(wd, Duration.ofSeconds(10));
		WebElement imgCaption = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img[alt='LambdaTest Community']")));
		System.out.println("pageCaption '" + imgCaption.getAttribute("alt") + "' located @" + imgCaption.getLocation());
		WebElement pageCaption=wd.findElement(By.xpath("//h1[@style='text-align:center']"));
		String text = (String) js.executeScript("return arguments[0].innerText", pageCaption);
		System.out.println("Page caption using JS:"+text);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		js.executeScript("window.scrollBy(0, 700)");
//		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
//		js.executeScript("arguments[0].scrollIntoView();", wd.findElement(By.xpath("//a[text()='More']")));

		String title = js.executeScript("return document.title;").toString();
		System.out.println("Page title using JS:"+ title);
		Assert.assertTrue(wd.getTitle().equals(title));
	}
}
