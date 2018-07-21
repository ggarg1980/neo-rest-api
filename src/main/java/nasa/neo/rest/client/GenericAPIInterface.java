package nasa.neo.rest.client;

import com.google.gson.JsonObject;

/** This interface is defined to call generic APIs 
 * @author GGARG
 *
 */
public interface GenericAPIInterface {
	
	/** Interface method, takes input as URL to be called and return JSON object based on the response from the API
	 * @param url - URL to be called
	 * @return - JSON object to be returned based on the input string
	 * @throws Exception - Throw exception in case of unforeseen issues 
	 */
	public JsonObject execute(String url) throws  Exception;

}
