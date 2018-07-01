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
			logger.info(" Configuration file loaded ");
			Properties prop = LoadConfigurationData.getProp();
			 if(IConstants.TRUE.equalsIgnoreCase(prop.getProperty(IConstants.PROXYUSE)))
			 {
		         Properties systemSettings = System.getProperties();
		         System.setProperty(IConstants.JAVASYSPROXIES, IConstants.TRUE);
		         systemSettings.put(IConstants.JAVAPROXYSET, IConstants.TRUE);
		         systemSettings.put(IConstants.JAVAPROXYHOSTNAME, prop.getProperty(IConstants.PROXYHOSTNAME));
		         systemSettings.put(IConstants.JAVAPROXYHOSTPORT, prop.getProperty(IConstants.PROXYHOSTPORT));
		         systemSettings.put(IConstants.JAVAPROXYUSERPNAME, prop.getProperty(IConstants.PROXYUSERNAME));
		         systemSettings.put(IConstants.JAVAPROXYUSERPASSWD, prop.getProperty(IConstants.PROXYUSERPASSWORD));
		         logger.info(" Proxy settings are loaded ");
			 }
			
			OperObj	parentObj =  LoadConfigurationData.getParentObj();
			OperObj	elecount =  LoadConfigurationData.getCountElement();
			List<OperObj> childObjList  = LoadConfigurationData.getChildObjList();
			JsonObject jsonObject = new ExecuteGetRestAPI(prop.getProperty(IConstants.URL), prop).execute();
			logger.info(" API Call is executed  ");
			Map<String,JsonObject> mapListCount = new HashMap<String,JsonObject>();
			Map<String,JsonObject> uniqueNodes = new HashMap<String,JsonObject>();
			
		    EvaluateJSONOperations eval = new EvaluateJSONOperations();
		    eval.findJSONPathObject(jsonObject, elecount.getPath(),mapListCount);
		    eval.findJSONPathObject(jsonObject, parentObj.getPath(),uniqueNodes);
		    logger.info(" find operations complete  ");
		    
		   for (OperObj childNode : childObjList) 
			   for (Map.Entry<String,JsonObject> entry : uniqueNodes.entrySet()) 
				   eval.processJsonObjectOperation(entry.getKey(),entry.getValue(), childNode.getPathArr(),childNode.getLeafNode(), childNode.getIndex(),childNode);
		   
		   logger.info(" evalution operations complete  ");
		   
		   UtilFunction.displayOutput(elecount,parentObj,mapListCount,uniqueNodes,childObjList);
		   
		   logger.info(" Displayed output. Program complete  ");
		}
		catch(java.net.ConnectException e)
		{
			logger.error(" Connection Timeout to external host. Please check proxy settings or URL. Program terminated");
		}
		catch(java.net.UnknownHostException e)
		{
			logger.error("  Unknown Host Exception  to external host. Please check proxy settings or URL. Program terminated");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
}
