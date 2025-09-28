package SeleniumFramework.tests;

import java.io.IOException;
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

public class SubmitOrderTestDataProviderArrayAndHashMap extends BaseTest {
	String product;
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
		productCatalogue.addProductToCart(input.get("productName"));
		product= input.get("productName");
		CartPage cartPage= productCatalogue.gotoCartPage();
		Boolean match= cartPage.verifyProductDisplay(input.get("productName"));
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
		System.out.println(product);
		ProductCatalogue productCatalogue= landingPage.loginApplication("test_test1234@gmail.com", "Test@1234");	
		OrderPage orderPage=productCatalogue.gotoOrderPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(product));
		
	}
	
	@DataProvider
	public Object[][] getData()
	{
		
		/* 1) Data Provider using Array
		return new Object[][] {{"ashtest1234@gmail.com", "Ash@1234", "ZARA COAT 3"},
			{"test_test1234@gmail.com","Test@1234", "ADIDAS ORIGINAL"}};
		Suppose we want to pass 3 set of 15 values for one test case. Declaring 15 values in 3 sets in the form of array is not a 
		good practice. Here we can use hashmap  */
		
		// 2) Using HashMap
		HashMap<String, String> map= new HashMap<String, String>();
		map.put("email", "ashtest1234@gmail.com");
		map.put("password", "Ash@1234");
		map.put("productName", "ZARA COAT 3");
		
		HashMap<String, String> map1= new HashMap<String, String>();
		map1.put("email", "test_test1234@gmail.com");
		map1.put("password", "Test@1234");
		map1.put("productName", "ADIDAS ORIGINAL");
		
		return new Object[][] {{map}, {map1}};
		
	}
	
	}

	
	


