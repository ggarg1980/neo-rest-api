package nasa.neo.rest.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

/** This class is the main class which is executed via command line. This class internally calls all other class and finally displays the output on the console.
 * @author GGARG
 *
 */
class Runner extends ConfigPropertiesObjList
{
	
	/**
	 * Logger Defined for logging  
	 */
	static final Logger logger = Logger.getLogger(Runner.class);
	
	/**
	 * This is used to store JsonTree from response
	 */
	private JsonObject jsonObject;
	/**
	 * This map contains details about the element count value
	 */
	private Map<String,JsonObject> mapListCount;
	/**
	 * This map contains details about the unique nodes present in the JSON object for example based on neo_reference_id one can evaluate how many nodes are present in the response
	 */
	private Map<String,JsonObject> uniqueNodes;
	
	/** Main method executing the entire flow
	 * @param args
	 */
	public static void main(String args[]) 
	{
			BasicConfigurator.configure();
			logger.debug("main::entry");
			GenericAPIInterface genericAPIinterface = new ExecuteGetRestAPI();
			Runner runner = new Runner();
			
			if(!runner.dataLoad(args,genericAPIinterface))
				 return;
			runner.executeJSONFunc();
			logger.debug("main::exit");
	}
	/** This method call EvaluateJSONOperations class internally and performs all the operations defined in config file like smallest/largest etc
	 * @return
	 */
	public boolean executeJSONFunc() 
	{
		logger.debug("executeJSONFunc::entry");
		boolean isExecSucess = false;  
		try
		{
			 mapListCount = new HashMap<String,JsonObject>();
			 uniqueNodes = new HashMap<String,JsonObject>();
			 
			 EvaluateJSONOperations eval = new EvaluateJSONOperations();
			
		     eval.findJSONPathObject(jsonObject, countElement.getPath(),mapListCount);
		     eval.findJSONPathObject(jsonObject, parentObj.getPath(),uniqueNodes);
		    
		     logger.debug(" find operations complete ");
		    
		     for (OperObj childNode : childObjList) 
			   for (Map.Entry<String,JsonObject> entry : uniqueNodes.entrySet()) 
				   eval.processJsonObjectOperation(entry.getKey(),entry.getValue(), childNode.getPathArr(),childNode.getLeafNode(), childNode.getIndex(),childNode);
		   
		     UtilFunction.displayOutput(countElement,parentObj,mapListCount,uniqueNodes,childObjList);
		     isExecSucess = true;
		     logger.debug(" executeJSONFunc::exit:: Displayed output. Program complete.");
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		return isExecSucess;
	}
	
	/** This method is used to load the data, in case of issues appropriate errors are shown on console
	 * @param args
	 * @param genericAPIinterface
	 * @return
	 */
	public boolean dataLoad(String[] args,GenericAPIInterface genericAPIinterface)
	{
		logger.debug("dataLoad::entry");
		boolean isLoadSucess = false;  
		try 
		{
			LoadConfigurationData loadConfData = new LoadConfigurationData();
			if(!loadConfData.loadConfData(args))
				 return isLoadSucess;
			logger.debug("dataLoad:: Configuration file loaded successfully ..... "+args[0]);
			setProp(loadConfData.getProp());
			setParentObj(loadConfData.getParentObj());
			setCountElement(loadConfData.getCountElement());
			setChildObjList(loadConfData.getChildObjList());
			setJsonObject(genericAPIinterface.execute(prop.getProperty(IConstants.URL)));
			logger.debug("dataLoad:: API Call is executed successfully ");
			isLoadSucess = true;   
		}
		catch (Exception e)
		{
			logger.error(e.getMessage());
		}
		logger.debug("dataLoad::exit::isLoadSucess="+isLoadSucess);
	    return isLoadSucess;
	}
	/**
	 * @param jsonObject
	 */
	public void setJsonObject(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	/**
	 * @return
	 */
	public Map<String, JsonObject> getMapListCount() {
		return mapListCount;
	}

}
