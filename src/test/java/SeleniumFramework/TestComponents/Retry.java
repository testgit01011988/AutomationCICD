package SeleniumFramework.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer{
	
	int maxTry=1;
	int count=0;
	//we are implementing the methods of interface IRetryAnalyzer
	/*once we add the libraries for IRetryAnalyzer, just hover the mouse over "Retry" and it will give option to add 
	  implmented methods mentioned below  */

	@Override
	public boolean retry(ITestResult result)
		//when a test fails it will goto onTestFailure() in listeners and then it will come to this method
	{
		
		
		/*by default this method returns false, so it won't retry, so when we want to retry, we need to return true.
		 We can add this in the if condition and the retry should be less than the maximum number of retries required  */
		if(count<maxTry)
		{
			count++;
			return true;
			
		}
				return false;  //when it returns false, it stops retry
	}

}
