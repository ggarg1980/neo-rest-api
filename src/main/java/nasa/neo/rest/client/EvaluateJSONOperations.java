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
	 * @param key - This stores the value of the unique_id used to distinguish different objects uniquely e.g. neo_reference_id
	 * @param jsonObject - Json Object used for traversal 
	 * @param arrSplit - Array used for traversal, element by element till leaf node is reached
	 * @param leafNode - Leaf 
	 * @param index - Index used to traversal via arrSplit
	 * @param child - Child against which operation needs to be performed 
	 * @throws Exception
	 */
	public void processJsonObjectOperation(String key, JsonObject jsonObject,String[] arrSplit,String leafNode, Integer index,OperObj child) throws Exception 
	{
		logger.debug("processJsonObjectOperation::entry :: key="+key+" leafNode="+leafNode+"  index="+index+" arrSplit="+arrSplit[index]);
		String error;
	    JsonElement element = jsonObject.get(arrSplit[index]);
	    //Element evaluated to null -> This means path defined in the config file is not correct, throw exception 
	    if(element==null)
	    {
	    	String errorPath = MessageFormat.format(IConstants.JSONPATHERROR,child.getName(), child.getPath(),arrSplit[index]);
	    	throw new Exception(errorPath);
	    }
	    // Check if arr[index] matches leaf node and also check if element is of type Primitive. In case if its not that means its not leaf node and correct evaluation can't be done. throw error
	    if(arrSplit[index].equalsIgnoreCase(leafNode))
	    {
		    if(element.isJsonPrimitive())
		    {
		    	if(UtilFunction.isValidNumber(element.getAsString()))
		    	{
		    		evaluatedValue = element.getAsDouble();
		    		evaluteResult(key,child);
		    	}
		    	else
		    	{
	    	    	error = MessageFormat.format(IConstants.CHILDELEMENTNOTVALIDNUMBER,child.getName(),child.getPath(),element.getAsString());
	    	    	throw new Exception(error);
		    	}
		    }
		    else
		    {
		    	error = MessageFormat.format(IConstants.CHILDELEMENTNOTLEAF,child.getName(),child.getPath());
    	    	throw new Exception(error);
		    }
	    }
	  //Element evaluated JSON Array -> Traversal needs to be done for all the elements in the array. Recursive call to process all the array elements 
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
	    
	    logger.debug("processJsonObjectOperation::exit :: key="+key+" leafNode="+leafNode+"  index="+index+" arrSplit="+arrSplit[index]);
	}
	

	/** This method traverses the JSON object and based on the defined parameter finds the actual value.
	 *  This method is recursive and continues to execute till find the relevant key 
	 * @param jsonObject - Parent Json Object
	 * @param configFileKey - This key is used to provide more details about errorMsg
	 * @param toBeIdentifiedKey - Key to be identified for example parent key like neo_reference_id 
	 * @param mapListCount - Map of the objects identified 
	 * @throws Exception 
	 */
	public void findJSONPathObject(JsonObject jsonObject,String configFileKey, String toBeIdentifiedKey,Map<String,JsonObject> mapListCount) throws Exception 
	{
		logger.debug("findJSONPathObject::entry :: toBeIdentifiedKey="+toBeIdentifiedKey+" configFileKey="+configFileKey+" jsonObject="+jsonObject);
        Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
        //Iterate over all the elements present in Json  
	        while (iterator.hasNext())
	        {
	            Map.Entry<String, JsonElement> entry = iterator.next();
	            String key = entry.getKey();
	            JsonElement element = entry.getValue();
	            //check if element matches the toBeIdentifiedKey then break else continue
	            if(key.equalsIgnoreCase(toBeIdentifiedKey))
	            {
	            	//check if the matching element is primitive or not. In case if its not primitive it means its not a leaf node error needs to be thrown
	        	    if(!element.isJsonPrimitive())
	        	    {
	        	    	String errorPath = MessageFormat.format(IConstants.ELEMENTNOTLEAF,configFileKey,toBeIdentifiedKey);
	        	    	throw new Exception(errorPath);
	        	    }
	            	mapListCount.put(element.getAsString(), jsonObject);
	            	break;
	            }
	            //Object identified is a JSON array - all the elements needs to be traversed, call function recursively 
	    	    if(element.isJsonArray())
	    	    {
	    	       JsonArray jsonArray = element.getAsJsonArray();
	    	       for(int i=0;i<jsonArray.size();i++)
	    	       {
		    	    	   JsonElement tempElement = jsonArray.get(i);
		    	    	   findJSONPathObject(tempElement.getAsJsonObject(),configFileKey,toBeIdentifiedKey,mapListCount);
	    	       }
	    	    }
	    	    //In case if element is JSON object - call the function recursively. As it can be another JsonArr or JsonObject or JsonPrimitive
	    	    if(element.isJsonObject())
	    	    {
	    	    	findJSONPathObject(element.getAsJsonObject(),configFileKey,toBeIdentifiedKey,mapListCount);
	    	    }
	        }
	        logger.debug("findJSONPathObject::exit :: toBeIdentifiedKey="+toBeIdentifiedKey+" configFileKey="+configFileKey+" mapListCount="+mapListCount);
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