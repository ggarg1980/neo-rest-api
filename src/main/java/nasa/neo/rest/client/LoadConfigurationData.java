package nasa.neo.rest.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver; 

public class LoadConfigurationData 
{
	static final Logger logger = Logger.getLogger(LoadConfigurationData.class);
	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		LoadConfigurationData.prop = prop;
	}

	public static OperObj getParentObj() {
		return parentObj;
	}

	public static void setParentObj(OperObj parentObj) {
		LoadConfigurationData.parentObj = parentObj;
	}

	public static List<OperObj> getChildObjList() {
		return childObjList;
	}

	public static void setChildObjList(List<OperObj> childObjList) {
		LoadConfigurationData.childObjList = childObjList;
	}
	
	private static Properties prop;
	
	private static OperObj parentObj = new OperObj();
	
	private static OperObj countElement = new OperObj();
	
	public static OperObj getCountElement() {
		return countElement;
	}

	public static void setCountElement(OperObj countElement) {
		LoadConfigurationData.countElement = countElement;
	}

	private static List<OperObj> childObjList = new ArrayList<OperObj>();
	
	private static StringBuilder errorMsg= new StringBuilder(IConstants.BASICVALSTR);
	
	public static StringBuilder getErrorMsg() {
		return errorMsg;
	}

	public static void setErrorMsg(StringBuilder errorMsg) {
		LoadConfigurationData.errorMsg = errorMsg;
	}

	public static boolean loadConfData(String propertyFile) throws Exception 
	{
		boolean fileLoadSuccessfully = false;
		try
		{
			logger.debug(" loadConfData:: entry ");
			ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver(
					new FileSystemResourceLoader());
	
			PropertiesFactoryBean propertyBean = new PropertiesFactoryBean();
			propertyBean.setLocation(resourceResolver.getResource(propertyFile));
			propertyBean.afterPropertiesSet();
			prop = propertyBean.getObject();
			
			if(UtilFunction.propertyValidations(prop,errorMsg))
			{
				UtilFunction.loadData(prop, childObjList, parentObj,countElement);
				fileLoadSuccessfully = true;
			}	
			logger.debug(" loadConfData:: exit ::fileLoadSuccessfully"+fileLoadSuccessfully+" "+prop);
		}
		catch(java.io.FileNotFoundException e)
		{
			logger.error("loadConfData::ERROR:"+e.getMessage());
			throw e;
		}
		catch(Exception e)
		{
			logger.error("loadConfData::ERROR:"+e.getMessage());
			throw e;
		}
		return fileLoadSuccessfully;
	} 

	
//	public static void main(String args[]) throws Exception
//	{
//		
//		//evaluteParentNode("near_earth_objects/2018-06-29/neo_reference_id");
//		try
//		{
//			boolean flag = loadConfData("config.properties");
//			//BasicConfigurator.configure();
//			System.out.println(flag);
//			System.out.println(errorMsg);
//		}
//		catch(Exception e)
//		{
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//		//System.out.println(" "+errorMsg);
//	}
//	
	
}
