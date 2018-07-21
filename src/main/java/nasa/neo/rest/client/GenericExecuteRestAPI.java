package nasa.neo.rest.client;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** This is abstract class and can be extended to implement GET/POST/DELETE etc REST functions
 * @author GGARG
 *
 */
public abstract class  GenericExecuteRestAPI
{
	/**
	 *  Default Constructor
	 */
	public GenericExecuteRestAPI()
	{
		
	}
	/**
	 *  Logger defined for logging 
	 */
	static final Logger logger = Logger.getLogger(GenericExecuteRestAPI.class);
	
	/** This method is used to call the REST API and then convert the String object into JSON
	 * @param url - URL to be called 
	 * @return - This JSON object is then returned to client for further processing 
	 * @throws Exception - This exception is thrown in case if some unforeseen error comes 
	 */
	public JsonObject execute(String url) throws Exception
	{
		logger.debug(" execute:: entered :: url="+url);
		String responseStr = callRestAPI(url);
		JsonObject jsonObj = convertResponseToJson(responseStr);
		logger.debug(" execute:: exit :: jsonObj="+jsonObj);
		return jsonObj;
	}
	/** This method is used to call the REST API and returns the response string
	 * @param url - URL to be called  
	 * @return - Response is returned in form of string 
	 * @throws MalformedURLException - This exception is thrown by server in case if URL is not well formed 
	 * @throws Exception - This exception is thrown in case if some unforeseen error comes 
	 */
	public abstract String callRestAPI(String url) throws MalformedURLException,Exception;
	
	/** This method converts a String object into JSON
	 * @param responseString - string to be converted into JSON object
	 * @return - JSONObject is required 
	 */
	public JsonObject convertResponseToJson(String responseString) 
	{
		logger.debug(" convertResponseToJson:: entered :: responseString="+responseString);
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(responseString);
		JsonObject jsonObj = jsonElement.getAsJsonObject();
		logger.debug(" convertResponseToJson:: exit :: jsonObj="+jsonObj);
		return jsonObj;
	}
	
}