package nasa.neo.rest.client;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class EvaluateJSONOperations
{
	static final Logger logger = Logger.getLogger(EvaluateJSONOperations.class);
	private String confFile;
	private  double evaluatedValue =0;

	public static void main(String args[])
	{
		try 
		{
			BasicConfigurator.configure();
			if(args.length==0)
			{
				logger.error(IConstants.CONFFILEMISSING);
			}
			else
			{
				EvaluateJSONOperations eval = new EvaluateJSONOperations(args[0]);
				eval.execute();
			}
		}
		catch (Exception e)
		{
			//e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public EvaluateJSONOperations(String confFile)
	{
		this.confFile = confFile;
	}
	
	public void execute() throws IOException, Exception
	{
		boolean fileLoad = LoadConfigurationData.loadConfData(confFile);
		if(!fileLoad)
		{
			logger.error(LoadConfigurationData.getErrorMsg());
		}
		else
		{
			Properties prop = LoadConfigurationData.getProp();
			OperObj	parentObj =  LoadConfigurationData.getParentObj();
			OperObj	elecount =  LoadConfigurationData.getCountElement();
			List<OperObj> childObjList  = LoadConfigurationData.getChildObjList();
			JsonObject jsonObject = new ExecuteGetRestAPI(prop.getProperty(IConstants.URL), prop).execute();
			Map<String,JsonObject> mapListCount = new HashMap<String,JsonObject>();
			findJSONPathObject(jsonObject, elecount.getPath(),mapListCount);
		    Map<String,JsonObject> uniqueNodes = new HashMap<String,JsonObject>();
		    findJSONPathObject(jsonObject, parentObj.getPath(),uniqueNodes);

		   for (OperObj childNode : childObjList) 
		   {
			   for (Map.Entry<String,JsonObject> entry : uniqueNodes.entrySet()) 
			   {
		            processJsonObjectV2(entry.getKey(),entry.getValue(), childNode.getPathArr(),childNode.getLeafNode(), childNode.getIndex(),childNode);
			   }
			   System.out.println("=========++++++++++++++++++"+childNode.getResultKey());
		   }
		   print(mapListCount, childObjList, uniqueNodes);
		   
		   
		   
//			//processJsonObject(jsonObject, parentObj.getPathArr(),parentObj.getLeafNode(), parentObj.getIndex(),childObjList);
//		    Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//		   
//			for (OperObj rst : childObjList) 
//			{
//				//rst.
//				    String prettyJson = gson.toJson(rst.getJsonObjResult());
//			    System.out.println("prettyJson="+prettyJson);
//				
//			}	    		
		}
	}
	
	public void print(Map<String,JsonObject> mapListCount, List<OperObj> childObjList, Map<String,JsonObject> uniqueNodes)
	{
		
	}
	
	public void processJsonObjectV2(String key, JsonObject jsonObject,String[] arrSplit,String leafNode, Integer index,OperObj child) throws Exception 
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
	    	   processJsonObjectV2(key,tempElement.getAsJsonObject(),arrSplit,leafNode,tempIndex,child);
	       }
	    }
	    if(element.isJsonObject())
	    {
	    	processJsonObjectV2(key,element.getAsJsonObject(),arrSplit,leafNode,++index,child);
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

	
	public void evaluteResult(String key, OperObj rst,JsonObject jsonObject)
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