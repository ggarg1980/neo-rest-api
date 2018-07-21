package nasa.neo.rest.client;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/** This class is used to perform actions on main JSON object.
 *   processJsonObjectOperation - This method compares the value of old object with the current object and based on comparison, evaluates which object to retain and which one to discard
 *   findJSONPathObject - This method traverses the JSON object and based on the defined parameter finds the actual value.  
 * @author GGARG
 *
 */
class EvaluateJSONOperations
{

	/**
	 * Logger for logging 
	 */
	static final Logger logger = Logger.getLogger(EvaluateJSONOperations.class);
	/**
	 * Min/Max value stored based on the operationType   
	 */
	private  double evaluatedValue = 0;

	/** processJsonObjectOperation - This method compares the value of old object with the current object and based on comparison, evaluates which object to retain and which one to discard
	 * @param key
	 * @param jsonObject
	 * @param arrSplit
	 * @param leafNode
	 * @param index
	 * @param child
	 * @throws Exception
	 */
	public void processJsonObjectOperation(String key, JsonObject jsonObject,String[] arrSplit,String leafNode, Integer index,OperObj child) throws Exception 
	{
	    JsonElement element = jsonObject.get(arrSplit[index]);
	    if(element==null)
	    {
	    	String errorPath = MessageFormat.format(IConstants.JSONPATHERROR,child.getName(), child.getPath(),arrSplit[index]);
	    	logger.error(errorPath);
	    	throw new Exception(errorPath);
	    }
	    
	    if(element.isJsonArray())
	    {
	       JsonArray jsonArray = element.getAsJsonArray();
	       int tempIndex = ++index;
	       for(int i=0;i<jsonArray.size();i++)
	       {
	    	   JsonElement tempElement = jsonArray.get(i);
	    	   processJsonObjectOperation(key,tempElement.getAsJsonObject(),arrSplit,leafNode,tempIndex,child);
	       }
	    }
	    // In case if element is JSON object - call the function recursively. As it can be another JsonArr or JsonObject or JsonPrimitive 
	    if(element.isJsonObject())
	    {
	    	processJsonObjectOperation(key,element.getAsJsonObject(),arrSplit,leafNode,++index,child);
	    }
	    // In case if element is JSON object - call the function recursive
	    if(element.isJsonPrimitive())
	    {
	    	if(arrSplit[index].equalsIgnoreCase(leafNode))
	    	{
	    		evaluatedValue = element.getAsDouble();
	    		evaluteResult(key,child);
	    	}
	    }
	}
	

	/** This method traverses the JSON object and based on the defined parameter finds the actual value.
	 *  This method is recursive and continues to execute till find the relevant key 
	 * @param jsonObject - Parent Json Object
	 * @param toBeIdentifiedKey - Key to be identified for example parent key like neo_reference_id 
	 * @param mapListCount - Map of the objects identified 
	 */
	public void findJSONPathObject(JsonObject jsonObject,String toBeIdentifiedKey,Map<String,JsonObject> mapListCount) 
	{
        Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
        //Iterate  
	        while (iterator.hasNext())
	        {
	            Map.Entry<String, JsonElement> entry = iterator.next();
	            String key = entry.getKey();
	            JsonElement element = entry.getValue();
	            if(key.equalsIgnoreCase(toBeIdentifiedKey))
	            {
	            	mapListCount.put(element.getAsString(), jsonObject);
	            	break;
	            }
	    	    if(element.isJsonArray())
	    	    {
	    	       JsonArray jsonArray = element.getAsJsonArray();
	    	       for(int i=0;i<jsonArray.size();i++)
	    	       {
		    	    	   JsonElement tempElement = jsonArray.get(i);
		    	    	   findJSONPathObject(tempElement.getAsJsonObject(),toBeIdentifiedKey,mapListCount);
	    	       }
	    	    }
	    	    if(element.isJsonObject())
	    	    {
	    	    	findJSONPathObject(element.getAsJsonObject(),toBeIdentifiedKey,mapListCount);
	    	    }
	        }
        }

	
	/** This method evaluates which object needs to be stored based on the operation type
	 * It compares current object with previous object and performs required operation 
	   e.g. if operation is largest and old object is smaller than current object it replaces old object with current object and leave it as is.
	 * @param key - Unique identifier
	 * @param rst - Object which stores result
	 */
	private void evaluteResult(String key, OperObj rst)
	{
		// In case if rst object is empty. It means Code is coming here for the first time for a given child. Set it with current object
		 if(rst.getNumValue().doubleValue()==-1)
		 {
			 rst.setNumValue(evaluatedValue);
			 rst.setResultKey(key);
		 }
		 else
		 {
			 //Check the operation and evaluate accordingly 
			 switch(rst.getOperation())
			 {
			 	case IConstants.OPERSMALLEST:
			 		//In case if the current object is smaller than previous object - Set new smallest object as current object else do nothing
		   			if(rst.getNumValue()>evaluatedValue)
		   			{
		   				rst.setNumValue(evaluatedValue);
		   				rst.setResultKey(key);
		   			}
		   			break;
			 	case IConstants.OPERLARGEST:
			 		//In case if the current object is larger than previous object - Set new largest object as current object else do nothing
		   			if(rst.getNumValue()<evaluatedValue)
		   			{
		   				rst.setNumValue(evaluatedValue);
		   				rst.setResultKey(key);
		   			}
		   			break;
			 }
		 }
	}
}