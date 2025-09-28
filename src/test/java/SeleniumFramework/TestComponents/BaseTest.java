package SeleniumFramework.TestComponents;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import SeleniumFramework.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;


public class BaseTest {
	
	public WebDriver driver;
	public LandingPage landingPage;
	public WebDriver initializeDriver() throws IOException
	{
		
		//Properties class
		Properties prop= new Properties();
		/*So that Properties class can load the global properties file, we need to provide the path of the file. But load function
		 takes FileInputStream as argument, so we will create a object for this class and then load the file. FileInputStream 
		 requires the path of the file as argument, so we will provide the path of global properties file */
		
		//FileInputStream fis=new FileInputStream("C:\\Users\\admin\\Desktop\\automation2\\RestAssured\\JavaSamples\\FrameworkDemo\\src\\main\\java\\SeleniumFramework\\resources\\Global.properties");
		/*here, filepath is long. "C:\\Users\\admin\\Desktop\\automation2\\RestAssured\\JavaSamples\\FrameworkDemo" is local
		system path. We can make it dynamic so that it will pick according to the user and remaining part 
		"\\src\\main\\java\\SeleniumFramework\\resources\\Global.properties" is common. So, we can write the above mentioned code 
		line as below */
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"//src//main//java//SeleniumFramework//resources//Global.properties");
		/*“user.dir” will pick the path from the parent folder “FrameworkDemo” which is:
	         C:\Users\admin\Desktop\automation2\RestAssured\JavaSamples\FrameworkDemo */
		prop.load(fis); //load the global properties file
		
		//		String browserName=prop.getProperty("browser");
		/*We will comment out the above line as we need to add condition that if maven properties is provided in the terminal, then
		 it should pick that one and not the value given in global properties file.  */
		
		String browserName= System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		/*here System.getProperty("browser") refers to the "browser" provided in the terminal. Here, it says, if it not equal to 
		 null, then get System.getProperty("browser") else if it is null, then get the value from prop.getProperty("browser"), i.e.
		 from the global propertes file. 		  */
		
		System.out.println(browserName);
		
	//	if(browserName.equalsIgnoreCase("Chrome"))
		if(browserName.contains("chrome")) //so that it will go in this block irresepevctive it is headless or not
				{
					ChromeOptions options= new ChromeOptions();
					WebDriverManager.chromedriver().setup();
					if(browserName.contains("headless"))
					{
						options.addArguments("headless");
					}
					 driver= new ChromeDriver(options);
					 //if it doesnot go into headless code block,value of options will be non headless browser
					 driver.manage().window().setSize(new Dimension(1440,900)); //full screen standard dimension
					 
					
				}
		else if (browserName.equalsIgnoreCase("Firefox"))
		{
			//firefox
			System.out.println("inside firefox condition");
			System.setProperty("webdriver.gecko.driver", "/Users/admin/Desktop/automation2/Selenium/geckodriver.exe");
				 driver= new FirefoxDriver();  
		}
		else if (browserName.equalsIgnoreCase("Edge"))
		{
			//Edge
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
		}
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException 
	{
		driver=initializeDriver();
		landingPage= new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.close();
	}
	
	public List<HashMap<String, String>> getJsonDataToMAp(String filePath) throws IOException
	{
		//Step1 : read json to string
		String jsonContent= FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		//provide the path of the json file  as argument for readFileToString()
		//when we hover over FileUtils, if the required libraries are not displayed, then we need to Fileutil dependency in pom file
		//System.getProperty("user.dir") -> is to get the local system path dynamically as it will be different for different users
		//readFileToString(File file) is deprecated, we have to use FileUtils.readFileToString(file file, Charset CharsetName)
		//e.g.FileUtils.readFileToString(file, StandardCharsets.UTF_8), FileUtils.readFileToString(file, "ISO-8859-1")
		
		
				//Step2 : String to HashMap
		
		//we need Jackson Databind to convert String to HashMap, we need to add dependency for Jackson Databind in pom file
		ObjectMapper mapper= new ObjectMapper();
		List<HashMap<String, String>> data= mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){});
		//readValue() is in ObjectMapper(), so we created object mapper for ObjectMapper()
		/*readValue() has 2 arguments-> jsonContent in String format and for second argument we need to tell how we need to convert
		 it, TypeReference<List<HashMap<String, String>>>
		TypeReference<List<HashMap<String, String>>> :-> here, we need 2 hashmaps(2 indexes), thus <String, String> in the form of
		 HashMap, thus HashMap<String, String> and then finally in the form of List, thus <List<HashMap<String, String>> and so 
		 list with 2 hashmaps are returned to data. So data is a list with 2 arguments which are the 2 hashmaps created*/
		return data;
		
		
	}
	
	public String getScreenshot(String testcaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts= (TakesScreenshot)driver;     //to capture screenshot
		File source= ts.getScreenshotAs(OutputType.FILE);
		File file= new File(System.getProperty("user.dir")+ "//reports//"+ testcaseName+ ".png");
		FileUtils.copyFile(source, file);
		return (System.getProperty("user.dir")+ "//reports//"+ testcaseName+ ".png");
		/*TakesScreenshot typecasts and says the driver to go in screenshot mode. getScreenshotAs(OutputType.FILE) tells in which 
		  format we want output, here we gave FILE fileUtils.copyFile() will copy the file. Here we need to give the source and 
		  the destination of the File.
		  And finally the path of the screenshot file is returned back */
	}
	
}