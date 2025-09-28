package SeleniumFramework.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import SeleniumFramework.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent{
	
	WebDriver driver;
	
	public CartPage(WebDriver driver)
	{
		super(driver);
		this.driver=driver;		//assign the value of driver from main class
		PageFactory.initElements(driver, this);  //to initialise the pagefactory
	}
	
	//**********to click on the cart icon to check if the product is added and click on checkout********
	
	@FindBy(css=".cartSection h3")  //to use for code to click cart icon 
	List <WebElement> cartProducts;
	
	public Boolean verifyProductDisplay(String productName)
	{
		Boolean match = cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	@FindBy(css=".totalRow button")  //to click on checkout 
	WebElement checkoutEle;
	
	public CheckoutPage gotoCheckout()
	{
		checkoutEle.click();
		CheckoutPage checkoutPage= new CheckoutPage(driver);
		return checkoutPage;
	}

	
			
	
	

			
}
