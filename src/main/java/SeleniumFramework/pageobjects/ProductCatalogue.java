package SeleniumFramework.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;


import SeleniumFramework.AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{
// we will be using the resuable codes mentioned in AbstractComponent, so we are mentioning ProductCatalogue extends AbstractComponent 
	
	//code to get the list of items and add specific product to cart. to check if the loading sign animation disappears and "Product Added to Cart" message appears
WebDriver driver;
	
	public ProductCatalogue(WebDriver driver)
	{
		super(driver); //if constructor is present in parent, all child class must serve it. So we have to declare super here
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	/* to mention below code using pagefactory
	 * List<WebElement> products= driver.findElements(By.cssSelector(".mb-3")); */
	
	@FindBy(css=".mb-3")   
	List<WebElement> products;   //it provides the list of items
	
	By productsBy= By.cssSelector(".mb-3"); //to pass argument for method waitForElementToAppear() method
	By addToCart= By.cssSelector(".card-body button:last-of-type"); //to pass argument for method addProductToCart() method
	By toastMessage=By.cssSelector("#toast-container"); //to pass argument for method waitForElementToAppear() method
	
	@FindBy(css=".ng-animating")   
	WebElement spinner;  //using pagefactory for animation as it is passing driver value
	
	
	
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productsBy);
		return products;	  //after wait period return the list of products/items	
	}
	
	public WebElement getProductByName(String productName)
	{
		WebElement prod= getProductList().stream().filter(product->product.findElement(By.cssSelector("b")).getText()
				.equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String productName)
	{
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCart).click();
		//pagefactory cannot be applied here, as prod is WebElement. It can be applied only on drivers
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
	}
	
	
	
	

}
