package nasa.neo.rest.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

class Runner
{
	static final Logger logger = Logger.getLogger(Runner.class);

	public static void main(String args[])
	{
		try 
		{
			BasicConfigurator.configure();
			if(args.length==0)
			{
				logger.error(IConstants.CONFFILEMISSING);
				return ;
			}

			boolean fileLoad = LoadConfigurationData.loadConfData(args[0]);
			if(!fileLoad)
			{
				logger.error(LoadConfigurationData.getErrorMsg());
				return;
			}
			Properties prop = LoadConfigurationData.getProp();
			OperObj	parentObj =  LoadConfigurationData.getParentObj();
			OperObj	elecount =  LoadConfigurationData.getCountElement();
			List<OperObj> childObjList  = LoadConfigurationData.getChildObjList();
			JsonObject jsonObject = new ExecuteGetRestAPI(prop.getProperty(IConstants.URL), prop).execute();
			Map<String,JsonObject> mapListCount = new HashMap<String,JsonObject>();
			
			
			Map<String,JsonObject> uniqueNodes = new HashMap<String,JsonObject>();
			
		    EvaluateJSONOperations eval = new EvaluateJSONOperations();
		    eval.findJSONPathObject(jsonObject, elecount.getPath(),mapListCount);
		    eval.findJSONPathObject(jsonObject, parentObj.getPath(),uniqueNodes);
			
		   for (OperObj childNode : childObjList) 
			   for (Map.Entry<String,JsonObject> entry : uniqueNodes.entrySet()) 
				   eval.processJsonObjectOperation(entry.getKey(),entry.getValue(), childNode.getPathArr(),childNode.getLeafNode(), childNode.getIndex(),childNode);
		   
		   UtilFunction.displayOutput(elecount,parentObj,mapListCount,uniqueNodes,childObjList);
		   
		   
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
}
