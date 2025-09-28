package SeleniumFramework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import SeleniumFramework.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent{
		WebDriver driver;
		
		public CheckoutPage(WebDriver driver)
		{
			super(driver);
			this.driver=driver;		//assign the value of driver from main class
			PageFactory.initElements(driver, this);  //to initialise the pagefactory
		}
		
		@FindBy(css=".action__submit")
		private WebElement submit;
		
		@FindBy(css="[placeholder='Select Country']")
		WebElement country;
		
		@FindBy(xpath="(//button[contains(@class,'ta-item')])[2]")
		WebElement selectCountry;
		
		By results= By.cssSelector(".ta-results");
		
		public void selectCountry(String countryName)
		{
			Actions a = new Actions(driver);
			/* to write equivalent step to below code using pagefactory created webelement country:
			 * a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform(); */
			a.sendKeys(country, countryName).build().perform();
			waitForElementToAppear(By.cssSelector(".ta-results"));
			selectCountry.click();			
		}
		
		public ConfirmationPage submitOrder()
		{
			
			windowScroll();
			waitForWebElementToAppear(submit);
			submit.click();
			return new ConfirmationPage(driver);
		}
}
