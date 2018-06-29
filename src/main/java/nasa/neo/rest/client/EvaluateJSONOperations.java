package nasa.neo.rest.client;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

class EvaluateJSONOperations
{
	public static void main(String args[])
	{
		EvaluateJSONOperations eval = new EvaluateJSONOperations(args[0]);
		try 
		{
			eval.execute();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public EvaluateJSONOperations(String confFile)
	{
		this.confFile = confFile;
	}
	
	public void execute() throws IOException, Exception
	{
		LoadConfigurationData.loadConfData(confFile);
		Properties prop = LoadConfigurationData.getProp();
		OperObj	parentObj =  LoadConfigurationData.getParentObj();
		List<OperObj> childObjList  = LoadConfigurationData.getChildObjList();
		JsonObject jsonObject = new ExecuteGetRestAPI(prop.getProperty(IConstants.URL)).execute();
		parentLeaf = parentObj.getLeafNode();
		processJsonObject(jsonObject, parentObj.getPathArr(),parentObj.getLeafNode(), parentObj.getIndex(),childObjList);
	    Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
	   
		for (OperObj rst : childObjList) 
		{
			    String prettyJson = gson.toJson(rst.getJsonObjResult());
		    System.out.println("prettyJson="+prettyJson);
			
		}	    		
		
		
	}
	
	private  double evaluatedValue =0;
	private String parentLeaf = ""; 
	private String confFile;
	
	public void processJsonObject(JsonObject jsonObject,String[] arrSplit,String parentLeafNode, Integer index,List<OperObj> childObjList) 
	{
	    JsonElement element = jsonObject.get(arrSplit[index]);
	    if(element.isJsonArray())
	    {
	       JsonArray jsonArray = element.getAsJsonArray();
	       int tempIndex = ++index;
	       for(int i=0;i<jsonArray.size();i++)
	       {
	    	   JsonElement tempElement = jsonArray.get(i);
	    	   processJsonObject(tempElement.getAsJsonObject(),arrSplit,parentLeafNode,tempIndex,childObjList);
	       }
	    }
	    if(element.isJsonObject())
	    {
	    	processJsonObject(element.getAsJsonObject(),arrSplit,parentLeafNode,++index,childObjList);
	    }
	    if(element.isJsonNull())
	    {
	    	//evaluate in case if this comes in future
	    }
	    if(element.isJsonPrimitive())
	    {
	    	if(arrSplit[index].equalsIgnoreCase(parentLeafNode))
	    	{
	    		evaluatedValue = element.getAsDouble();
	    	}
	    	if(arrSplit[index].equalsIgnoreCase(parentLeaf))
	    	{
	    		for (OperObj rst : childObjList) 
	    		{
	    			processJsonObject(jsonObject,rst.getPathArr(),rst.getLeafNode(),0,childObjList);
	    			evaluteResult(rst,jsonObject);
	    		}	    		
	    	}
	    }
	}
	
	
	public void evaluteResult(OperObj rst,JsonObject jsonObject)
	{
		 if(rst.getJsonObjResult()==null)
		 {
			 rst.setJsonObjResult(jsonObject);
			 rst.setNumValue(evaluatedValue);
		 }
		 else
		 {
			 switch(rst.getOperation())
			 {
			 	case "smallest":
		   			if(rst.getNumValue()>evaluatedValue)
		   			{
		   				rst.setJsonObjResult(jsonObject);
		   				rst.setNumValue(evaluatedValue);
		   			}
		   			break;
			 	case "largest":
		   			if(rst.getNumValue()<evaluatedValue)
		   			{
		   				rst.setJsonObjResult(jsonObject);
		   				rst.setNumValue(evaluatedValue);
		   			}
		   			break;
			 	 default:  
			 	     System.out.println("Operation not found ......."); 		   			
			 }
		 }
	}
}