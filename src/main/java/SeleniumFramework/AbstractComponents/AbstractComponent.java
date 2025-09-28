package SeleniumFramework.AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import SeleniumFramework.pageobjects.CartPage;
import SeleniumFramework.pageobjects.OrderPage;

public class AbstractComponent {
	
	//common reusable codes
	
	WebDriver driver;
	public AbstractComponent(WebDriver driver) {
		this.driver=driver;
	}
	
	@FindBy(css="[routerlink*='cart']")  //to use for code to click cart icon 
	WebElement cartHeader;
	
	@FindBy(css="[routerlink*='myorders']")  //to use for code to click order icon 
	WebElement orderHeader;

	//code for mentioning explicit wait for visibility
	public void waitForElementToAppear(By findBy)
	/*driver.findElement(By.id("login")).click(), here the entire line is webelement but if we refer only 'By.id("login")',
	  we refer it as "by" locator. So we passed "By" as argument in this method */
	{
	WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
	wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	
	}
	
	public void waitForWebElementToAppear(WebElement findBy)
	{
	WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
	wait.until(ExpectedConditions.visibilityOf(findBy));
	
	}
	
	//code for mentioning explicit wait for invisibility
		public void waitForElementToDisappear(WebElement element)
		{
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(element));
		}
		
		public CartPage gotoCartPage()  //cart icon is present in all pages, so considering it as a resuable code
		{
			cartHeader.click();
			CartPage cartPage = new CartPage(driver);
			return cartPage;
		}
		
		public OrderPage gotoOrderPage()  //cart icon is present in all pages, so considering it as a resuable code
		{
			orderHeader.click();
			OrderPage orderPage = new OrderPage(driver);
			return orderPage;
		}
		
		//scrolling window
		public void windowScroll()
		{
			JavascriptExecutor js= (JavascriptExecutor)driver;
			js.executeScript("window.scroll(0,1000)"); 
			
		}
	
}
