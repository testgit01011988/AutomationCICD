package SeleniumFramework.tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import SeleniumFramework.pageobjects.CartPage;
import SeleniumFramework.pageobjects.CheckoutPage;
import SeleniumFramework.pageobjects.ConfirmationPage;
import SeleniumFramework.pageobjects.LandingPage;
import SeleniumFramework.pageobjects.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest {

	public static void main(String[] args) {
		
		//in this program we are restructuring the StandaloneTest.java code using PageFactory
	
		String productName="ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver= new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		
		LandingPage landingPage= new LandingPage(driver);
		landingPage.goTo();  //open the url 
		ProductCatalogue productCatalogue= landingPage.loginApplication("ashtest1234@gmail.com", "Ash@1234");	
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
		driver.close();
		
		//Assertions should be in main test class
		
	}

	
	}	


