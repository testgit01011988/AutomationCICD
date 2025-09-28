package SeleniumFramework.tests;

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

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandaloneTest {

	public static void main(String[] args) throws InterruptedException {
	
		String productName="ZARA COAT 3";
		WebDriverManager.chromedriver().setup();
		WebDriver driver= new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");
	
		
		//*************code to login the page *************************
		
		driver.findElement(By.id("userEmail")).sendKeys("ashtest1234@gmail.com"); 		 //to enter the usermail ID
			//<input _ngcontent-uye-c43="" type="email" formcontrolname="userEmail" id="userEmail" placeholder="email@example.com" class="form-control ng-pristine ng-invalid ng-touched">
		
		driver.findElement(By.id("userPassword")).sendKeys("Ash@1234");		//to enter the password
			//<input _ngcontent-uye-c43="" type="password" formcontrolname="userPassword" id="userPassword" placeholder="enter your passsword" class="form-control ng-pristine ng-invalid ng-touched">
		
		driver.findElement(By.id("login")).click(); 	//to click on login button
			//<input _ngcontent-aux-c43="" name="login" id="login" type="submit" value="Login" class="btn btn-block login-btn">
		
		//**********to select the specific item and add them to cart(here we will be adding "ZARA COAT 3")**************
		
		//we will be using Stream for this
		
		//applying explicit wait for the page to get loaded
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3"))); //until the products are visible
		
		List<WebElement> products= driver.findElements(By.cssSelector(".mb-3"));
				//it will select the entire list of products available in the page. 
		
		WebElement prod= products.stream().filter(product->product.findElement(By.cssSelector("b")).getText()
				.equals(productName)).findFirst().orElse(null);
			//prod has the details of the section containing the product "Zara Coat 3"
		
	//	System.out.println(prod.getText());
		
		//to click on add to cart
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
			//parent-child relationship is used here, there are 2 buttons in card-body. last-of-type will select the last one
		
		//*************to check if the loading sign animation disappears and "Product Added to Cart" message appears*********
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
				//till the message is visible
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating")))); 
				//till the loading animation becomes invisible
		/*//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating"))); is also valid, but the 
		 * execution was taking time. So we need to start from driver.findElement(By.cssSelector(".ng-animating")), some 
		 * open selenium issues
		 */
		
		//**********to click on the cart icon to check if the product is added and click on checkout*********
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
			//<button _ngcontent-reg-c33="" routerlink="/dashboard/cart" class="btn btn-custom" style="margin-top: -10px;" tabindex="0" css="1"><i _ngcontent-reg-c33="" class="fa fa-shopping-cart"></i>&nbsp; Cart <label _ngcontent-reg-c33="" style="background-color: #96161f; border-radius: 2px; color: white; padding-left: 7px; padding-right: 7px;">3</label></button>
		
		//check if product is added in cart
		List <WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));	//parent child relation
				//get the webelements showcasing name of product in cart page
		Boolean match = 	cartProducts.stream().anyMatch(cartProduct-> cartProduct.getText().equalsIgnoreCase(productName));
				/*anmyMatch() has boolean return type. Here we donot want any values returned,so no need of filter, we can use
				anyMatch */
				Assert.assertTrue(match);  //validation
				
		//click on checkout
		driver.findElement(By.cssSelector(".totalRow button")).click(); //parent child mapping
		
		//*************Enter the country which is mandatory and Click on place order**********
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();  
		//send the text “india” in the text box
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		//wait until results appear with matching texts
		
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		//select the second one of the results appearing
		
		//
		
//	Thread.sleep(5000); 
		JavascriptExecutor js= (JavascriptExecutor)driver; //Casting your driver to javascript executor
		//windows scroll
		js.executeScript("window.scroll(0,500)"); 
	//	wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".action__submit")));
		System.out.println(driver.findElement(By.cssSelector(".action__submit")).getText());
		driver.findElement(By.cssSelector(".action__submit")).click();
	//	driver.findElement(By.cssSelector(".icon-arrow-right-circle")).click();
		//click on place order
		
	

		
		
		//*************check for Thank you for your order message**********
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		//in the html source it is in lower case, so we can use equalsIgnoreCase 
		driver.close();
		
		
	}

	
	}	


