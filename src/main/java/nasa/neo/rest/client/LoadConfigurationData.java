package nasa.neo.rest.client;

import java.text.MessageFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver; 

/** This class is used to load Data from the configuration file. It reads the properties defined in the file 
    and create Java Properties object, which is further used to create other objects like child, parent etc 
 * @author GGARG
 *
 */
public class LoadConfigurationData extends ConfigPropertiesObjList
{
	/**
	 * Logger Defined for logging  
	 */
	static final Logger logger = Logger.getLogger(LoadConfigurationData.class);
	

	/** This method is used to load the properties from the configuration file.
	 *  In case of any error/issue appropriate exception will be thrown and main program terminates
	 * @param propertyFile - String array 
	 * - array[0] contains path of the config file
	 * - array[1] Start Date format yyyy-MM-dd
	 * - array[2] End Date format yyyy-MM-dd
	 * @return - Returns true in case if file is loaded successfully otherwise false 
	 * @throws Exception - Throws exception in case of unforeseen issue  
	 */
	public boolean loadConfData(String[] propertyFile) throws Exception 
	{
		boolean fileLoadSuccessfully = false;
		try
		{
			logger.debug(" loadConfData:: entry ");
			//Check if the propertyFile Arr is valid or not. In case if not return and no more processing 
			if(propertyFile==null || propertyFile.length<3)
			{
				logger.error(IConstants.CONFFILEMISSING);
				return fileLoadSuccessfully;
			}
			else if(!UtilFunction.validateDate(propertyFile))
			{
				return fileLoadSuccessfully;
			}
			
			ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver(
					new FileSystemResourceLoader());
			
			PropertiesFactoryBean propertyBean = new PropertiesFactoryBean();
			propertyBean.setLocation(resourceResolver.getResource(propertyFile[0]));
			//Start loading configuration file, in case of any issues like file not found exception will be thrown and processing will be halted
			propertyBean.afterPropertiesSet();
			prop = propertyBean.getObject();
			
			//File is properly loaded start doing validations if all the mandatory properties are present or not
			errorMsg= new StringBuilder(IConstants.BASICVALSTR); 
			if(UtilFunction.propertyValidations(prop,errorMsg))
			{
				parentObj = new OperObj();   
				countElement = new OperObj(); 
				childObjList = new ArrayList<OperObj>(); 
				//Start loading data into different objects for further processing 
				UtilFunction.loadData(prop, childObjList, parentObj,countElement);
				//Check and evaluate if proxy settings needs to be loaded
				UtilFunction.evalProxySettings(prop);
				//Append start date and end date entered by user into the main URL
				String url = MessageFormat.format(prop.getProperty(IConstants.URL)+IConstants.LEFTOVERURL,propertyFile[1],propertyFile[2]);
				prop.setProperty(IConstants.URL,url);
				fileLoadSuccessfully = true;
				logger.debug(" loadConfData:: exit ::fileLoadSuccessfully"+fileLoadSuccessfully+" prop="+prop);
			}	
			else
			{
				logger.error(errorMsg);
			}
			
		}
		catch(java.io.FileNotFoundException e)
		{
			logger.error("loadConfData::ERROR:"+e.getMessage());
		}
		catch(Exception e)
		{
			logger.error("loadConfData::ERROR:"+e.getMessage());
			throw e;
		}
		return fileLoadSuccessfully;
	} 
}
