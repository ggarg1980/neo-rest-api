package nasa.neo.rest.client;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class EvaluateJSONOperations
{
	static final Logger logger = Logger.getLogger(EvaluateJSONOperations.class);
	private  double evaluatedValue =0;

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
	    if(element.isJsonObject())
	    {
	    	processJsonObjectOperation(key,element.getAsJsonObject(),arrSplit,leafNode,++index,child);
	    }
	    if(element.isJsonPrimitive())
	    {
	    	if(arrSplit[index].equalsIgnoreCase(leafNode))
	    	{
	    		evaluatedValue = element.getAsDouble();
	    		evaluteResult(key,child,jsonObject);
	    	}
	    }
	}
	

	public void findJSONPathObject(JsonObject jsonObject,String toBeIdentifiedKey,Map<String,JsonObject> mapListCount) 
	{
        Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
	        while (iterator.hasNext())
	        {
	
	            Map.Entry<String, JsonElement> entry = iterator.next();
	            String key = entry.getKey();
	            JsonElement element = entry.getValue();
	            if(key.equalsIgnoreCase(toBeIdentifiedKey))
	            {
	            	mapListCount.put(element.toString(), jsonObject);
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

	
	private void evaluteResult(String key, OperObj rst,JsonObject jsonObject)
	{
		 if(rst.getNumValue().doubleValue()==-1)
		 {
			 rst.setNumValue(evaluatedValue);
			 rst.setResultKey(key);
		 }
		 else
		 {
			 switch(rst.getOperation())
			 {
			 	case IConstants.OPERSMALLEST:
		   			if(rst.getNumValue()>evaluatedValue)
		   			{
		   				rst.setNumValue(evaluatedValue);
		   				rst.setResultKey(key);
		   			}
		   			break;
			 	case IConstants.OPERLARGEST:
		   			if(rst.getNumValue()<evaluatedValue)
		   			{
		   				rst.setNumValue(evaluatedValue);
		   				rst.setResultKey(key);
		   			}
		   			break;
			 	 default:  
			 		logger.error("Operation not found ......."+rst.getOperation()); 		   			
			 }
		 }
	}
}