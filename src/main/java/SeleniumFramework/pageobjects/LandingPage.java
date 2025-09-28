package SeleniumFramework.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import SeleniumFramework.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{
	
	WebDriver driver;
	
	public LandingPage(WebDriver driver)
	{
		super(driver); //if constructor is present in parent, all child class must serve it. So we have to declare super here
		this.driver=driver; //assign the value of driver from main class
		PageFactory.initElements(driver, this); //to initialise the pagefactory
	}
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	//PageFactory
	@FindBy(id="userEmail")  //this is equvalent to WebElements userEmail=driver.FindElement(By.id("userEmail"));
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement passwordElement;
	
	@FindBy(id="login")
	WebElement submit;
	
	@FindBy(css="[class*='flyInOut']")  //using regular expression
	WebElement errorMessage;
	
	public ProductCatalogue loginApplication(String email, String password)
	{
		userEmail.sendKeys(email);
		passwordElement.sendKeys(password);
		submit.click();
		ProductCatalogue productCatalogue= new ProductCatalogue(driver);
		return productCatalogue;
		
	}	
	
	public String getErrorMessage()
	{
		
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();// we need to return this value so that it can be used in the test case
		
		
	}
	

	
}
