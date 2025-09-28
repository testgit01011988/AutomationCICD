package SeleniumFramework.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;



import SeleniumFramework.TestComponents.BaseTest;
import SeleniumFramework.TestComponents.Retry;
import SeleniumFramework.pageobjects.CartPage;
import SeleniumFramework.pageobjects.ProductCatalogue;

public class ErrorValidations extends BaseTest{
	
	
	@Test(groups={"ErrorHandling"}, retryAnalyzer= Retry.class)
		public void LoginErrorValidation()
	{
	//String productName="ZARA COAT 3";
	//	LandingPage landingPage= launchApplication(); no need of this step as it will be called automatically
		landingPage.loginApplication("ashtest1234@gmail.com", "Ash@1111");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException
	{

		String productName = "ZARA COAT 3";
		ProductCatalogue productCatalogue = landingPage.loginApplication("ashtest1234@gmail.com", "Ash@1234");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.gotoCartPage();
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
	

	}
}
