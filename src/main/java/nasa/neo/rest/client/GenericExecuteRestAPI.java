package nasa.neo.rest.client;

import java.net.MalformedURLException;
import java.util.Properties;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author GGARG
 *
 */
public abstract class  GenericExecuteRestAPI
{
	public GenericExecuteRestAPI()
	{
		super();
	}
	protected String url;
	protected  String operation;
	protected JsonObject jsonTree;
	Properties prop;
	public GenericExecuteRestAPI(String url, String operation, Properties prop)
	{
		this.url=url;
		this.operation=operation;
		this.prop=prop;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public JsonObject getJsonTree() {
		return jsonTree;
	}
	public void setJsonTree(JsonObject jsonTree) {
		this.jsonTree = jsonTree;
	}
	public JsonObject execute() throws MalformedURLException, Exception
	{
		String responseStr = callRestAPI();
		jsonTree = convertResponseToJson(responseStr);
		return jsonTree;
	}
	public abstract String callRestAPI() throws MalformedURLException,Exception;
	
	public JsonObject convertResponseToJson(String responseString) 
	{
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(responseString);
		jsonTree = jsonElement.getAsJsonObject();
		return jsonTree;
	}
	
}