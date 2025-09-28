package SeleniumFramework.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import SeleniumFramework.TestComponents.BaseTest;
import SeleniumFramework.pageobjects.CartPage;
import SeleniumFramework.pageobjects.CheckoutPage;
import SeleniumFramework.pageobjects.ConfirmationPage;
import SeleniumFramework.pageobjects.LandingPage;
import SeleniumFramework.pageobjects.OrderPage;
import SeleniumFramework.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest2 extends BaseTest {
	String productName="ZARA COAT 3";
	JavascriptExecutor js= (JavascriptExecutor)driver;
	@Test
	public void submitOrder() throws IOException
	{
		//in this program we are restructuring the SubmitOrderTest.java code 
	
		
	//	LandingPage landingPage= launchApplication(); no need of this step as it will be called automatically
		ProductCatalogue productCatalogue= landingPage.loginApplication("ashtest1234@gmail.com", "Ash@1234");	
		//landingPage object used here gets its value from Parent class BaseTest.java
		
		List<WebElement> products= productCatalogue.getProductList();
						//it will select the entire list of products available in the page. 
		productCatalogue.addProductToCart(productName);
		CartPage cartPage= productCatalogue.gotoCartPage();
		Boolean match= cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage= cartPage.gotoCheckout();
		checkoutPage.selectCountry("india");
		 checkoutPage.submitOrder();
		 ConfirmationPage confirmationPage=new ConfirmationPage(driver);
		String confirmMessage= confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		//in the html source it is in lower case, so we can use equalsIgnoreCase 
	
		//	driver.close(); this step is not required as we added @AfterMethod in BaseTest.java mentioning driver.close()
		
		
		//Assertions should be in main test class
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void orderHistoryTest()
	{
		//"ZARA COAT 3"
		ProductCatalogue productCatalogue= landingPage.loginApplication("ashtest1234@gmail.com", "Ash@1234");	
		OrderPage orderPage=productCatalogue.gotoOrderPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
		
	}
	}

	
	


