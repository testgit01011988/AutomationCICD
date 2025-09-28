package SeleniumFramework.tests;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SeleniumFramework.TestComponents.BaseTest;
import SeleniumFramework.pageobjects.CartPage;
import SeleniumFramework.pageobjects.CheckoutPage;
import SeleniumFramework.pageobjects.ConfirmationPage;
import SeleniumFramework.pageobjects.LandingPage;
import SeleniumFramework.pageobjects.OrderPage;
import SeleniumFramework.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderJsonToHashMap extends BaseTest {
	String productName;
	JavascriptExecutor js= (JavascriptExecutor)driver;
	
	@Test(dataProvider="getData", groups= {"Purchase"})
	//public void submitOrder(String email, String password, String productName) throws IOException  
		//Above line is required for DataProvider with input in form of array
	
	public void submitOrder(HashMap<String, String> input) throws IOException
	{
		
	
	//	ProductCatalogue productCatalogue= landingPage.loginApplication(email, password);
		//Above line is required for DataProvider with input in form of array
		
		ProductCatalogue productCatalogue= landingPage.loginApplication(input.get("email"), input.get("password"));
		
		List<WebElement> products= productCatalogue.getProductList();
		//productCatalogue.addProductToCart(productName); this line is required for DataProvider with input in form of array
		productCatalogue.addProductToCart(input.get("product"));
		productName= input.get("product");
		CartPage cartPage= productCatalogue.gotoCartPage();
		Boolean match= cartPage.verifyProductDisplay(input.get("product"));
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
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		List<HashMap<String,String>> data = 
				getJsonDataToMAp(System.getProperty("user.dir")+"//src//test//java//SeleniumFramework//data//PurchaseOrder.json");			
		return new Object[][] {{data.get(0)}, {data.get(1)}};
		
	}
	
	}

	
	


