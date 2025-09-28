package SeleniumFramework.data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	public List<HashMap<String, String>> getJsonDataToMAp() throws IOException
	{
		//Step1 : read json to string
		String jsonContent= FileUtils.readFileToString((new File(System.getProperty("user.dir")+"src//test//java//SeleniumFramework//data//PurchaseOrder.json")),StandardCharsets.UTF_8);
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

}
