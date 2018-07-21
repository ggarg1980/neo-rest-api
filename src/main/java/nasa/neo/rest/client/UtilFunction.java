package nasa.neo.rest.client;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.google.gson.JsonObject;

/** This class contains utility functions which are being invoked by other Java classes
 * @author GGARG
 * 
 */
public class UtilFunction
{
	/**
	 *  Logger defined for logging 
	 */
	static final Logger logger = Logger.getLogger(UtilFunction.class);

	/**
	 *  Integer counter defined for displaying proper error message numbers during validations. 
	 */
	public static Integer counter = new Integer(1);
	
	/** This method is used to validate if whether required operation is allowed or not. Present valid values are largest and smallest
	 * @param str - Operation to be validated (valid values smallest/largest)
	 * @return - boolean true in case if validation is successful and false in case validation fails
	 */
	public static boolean isValidOperation(String str)
	{
		boolean isValid = false;
		if(isValidString(str)&&IConstants.OPERATIONLIST.contains(str))
		{
			isValid = true;
		}
		return isValid;
	}
	
	/** This method is used to validate whether a string is empty/null or not
	 * @param str - String to be validated (empty and null strings are not valid)
	 * @return - boolean true in case if validation is successful and false in case validation fails
	 */
	public static boolean isValidString(String str)
	{
		boolean isValid = false;
		if(str!=null && str.length()>0)
		{
			isValid = true;
		}
		return isValid;
	}
	
	/** This method validates whether input string is valid number or not
	 * @param str - To check whether parameter is valid number or not
	 * @return - boolean true in case if validation is successful and false in case validation fails
	 */
	public static boolean isValidNumber(String str)
	{
		boolean isValid = true;
		try
		{
		   Integer.parseInt(str);
		}
		catch(Exception e)
		{
			isValid = false;
		}
		return isValid;
	}

	/** This method validates all the properties which are present in the property file. Based on the validation failure it creates error string.
	 * @param prop - Properties against which validation needs to be performed 
	 * @param errMsg - In case of failure errorMsg string is populated with errors
	 * @return -  boolean true in case if validation is successful and false in case validation fails
	 * @throws IOException - Throws IO exception
	 */
	public static boolean propertyValidations(Properties prop, StringBuilder errMsg) throws IOException
	{
		boolean validationSuccess = true;
		boolean validUrl = validateURL(prop,errMsg);
		boolean validParent = validateParent(prop,errMsg);
		boolean validChild =  validateChild(prop,errMsg);
		boolean validateCountElement = validateCountElement(prop,errMsg);
		
		if(validUrl || validParent || validChild || validateCountElement)
		{
			validationSuccess = false;
		}
		return validationSuccess;
	}
	
	
	/** This method validates all child properties for a given object. Below are mandatory properties which should be present while defining a child object. 
	 	This method iterates over all the child objects and validates them. 
	 	
	   nasa.neo.rest.child.1.name=CLOSEST_OBJECT_TO_EARTH
	   nasa.neo.rest.child.1.path=close_approach_data/miss_distance/kilometers
	   nasa.neo.rest.child.1.operation=smallest
       nasa.neo.rest.child.1.displaymsg=Displaying closest object to earth, details as below

	 * @param prop - Properties against which validation needs to be performed
	 * @param errMsg - In case of failure errorMsg string is populated with errors
	 * @return -  boolean true in case if validation is successful and false in case validation fails
	 * @throws IOException - Throws IO exception
	 */
	public static boolean validateChild(Properties prop,StringBuilder errMsg) throws IOException
	{
		boolean validationFailed = false;
		String str = prop.getProperty(IConstants.CHILDNODECOUNT);
		//Validate if child count property is present and valid.  In case if not return validation failure 
		if(!isValidString(str) || !isValidNumber(str))
		{
			
			errMsg.append(MessageFormat.format(IConstants.CHILDCOUNTVALIDATIONMSG,counter++, IConstants.CHILDNODECOUNT));
			validationFailed = true;
		}
		else
		{
			int count = Integer.parseInt(str);
			String childNodeName;
			String childNodePath;
			String childOpertion;
			String childNodeMsg;
			String childUnitMsg;
			//Load all the child objects one by one and in case of any missing properties return false again
			for(int i=1;i<=count;i++)
			{
				childNodeName=prop.getProperty(IConstants.CHILDNODENAME.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i));
				childNodePath=prop.getProperty(IConstants.CHILDNODEPATH.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i));
				childOpertion=prop.getProperty(IConstants.CHILDNODEOPERATION.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i));
				childNodeMsg=prop.getProperty(IConstants.CHILDNODEDISMSG.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i));
				childUnitMsg=prop.getProperty(IConstants.CHILDNODEDUNITSMSG.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i));
				if(!isValidString(childNodeName) || !isValidString(childNodePath) || !isValidOperation(childOpertion) || !isValidString(childNodeMsg) || !isValidString(childUnitMsg))
				{
					errMsg.append(MessageFormat.format(IConstants.CHILDVALIDATIONMSG,counter++, i));
					validationFailed = true;
				}
			}
		}
		return validationFailed;
	}
	
	/** This method validates parent node, this node should uniquely determine each object in JSON.
	 	Validates following properties 
	    Example of parent node : nasa.neo.rest.parent.node=neo_reference_id
	    						 nasa.neo.rest.parent.name=NEO Reference ID
	 * @param prop - Properties against which validation needs to be performed
	 * @param errMsg - In case of failure errorMsg string is populated with errors
	 * @return -  boolean true in case if validation is successful and false in case validation fails
	 * @throws IOException - Throws IO exception
	 */
	public static boolean validateParent(Properties prop,StringBuilder errMsg) throws IOException
	{
		boolean validationFailed = false;
		String str = prop.getProperty(IConstants.PARENTNODE);
		if(!isValidString(str))
		{
			errMsg.append(MessageFormat.format(IConstants.PARENTVALIDATIONMSG,counter++, IConstants.PARENTNODE));
			validationFailed = true;
		}
		str = prop.getProperty(IConstants.PARENTDISPLAYNAME);
		if(!isValidString(str))
		{
			errMsg.append(MessageFormat.format(IConstants.PARENTVALIDATIONDISPLAYNAMEMSG,counter++, IConstants.PARENTDISPLAYNAME));
			validationFailed = true;
		}
		return validationFailed;
	}

	/** This property validates total count of the elements which are present in JSON request and display message 
	    Example nasa.neo.rest.elementcount.path=element_count 
	    		nasa.neo.rest.elementcount.displaymsg=The total number of Near Earth Objects (NEO) are {0}.
	 * @param prop - Properties against which validation needs to be performed
	 * @param errMsg - In case of failure errorMsg string is populated with errors
	 * @return -  boolean true in case if validation is successful and false in case validation fails
	 * @throws IOException - Throws IO exception
	 */
	public static boolean validateCountElement(Properties prop,StringBuilder errMsg) throws IOException
	{
		boolean validationFailed = false;
		String str = prop.getProperty(IConstants.ELEMENTCOUNT);
		if(!isValidString(str))
		{
			errMsg.append(MessageFormat.format(IConstants.ELEMENTCOUNTVALIDATIONMSG,counter++, IConstants.ELEMENTCOUNT));
			validationFailed = true;
		}
		str = prop.getProperty(IConstants.ELEMENTCOUNTDISPLAYMSG);
		if(!isValidString(str))
		{
			errMsg.append(MessageFormat.format(IConstants.ELEMENTCOUNTVALIDATIONDISPLAYMSG,counter++, IConstants.ELEMENTCOUNTDISPLAYMSG));
			validationFailed = true;
		}
		
		return validationFailed;
	}

	
	/** This method validates whether the URL entered is valid or not, based on REGEX. User is supposed to provide valid NEO URL.
	 * @param prop - Properties against which validation needs to be performed
	 * @param errMsg - In case of failure errorMsg string is populated with errors
	 * @return -  boolean true in case if validation is successful and false in case validation fails
	 * @throws IOException - Throws IO exception
	 */
	public static boolean validateURL(Properties prop,StringBuilder errMsg) throws IOException
	{
		boolean validationFailed = false;
		String str = prop.getProperty(IConstants.URL);
		if(!isValidString(str) || !IsMatch(str,IConstants.REGEX))
		{
			errMsg.append(MessageFormat.format(IConstants.URLVALIDATIONMSG,counter++, IConstants.URL));
			validationFailed = true;
		}
		return validationFailed;
	}

    /** This method validates whether input string matches a given pattern or not. 
     * @param str - String which needs to be matched against pattern
     * @param pattern - Pattern which is used against str for matching 
     * @return - boolean true in case if matching is successful and false in case matching fails
     */
    private static boolean IsMatch(String str, String pattern) 
    {
        try 
        {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(str);
            return matcher.matches();
        } catch (RuntimeException e) 
        {
        	return false;
        }       
    } 
    
	/** Based on the properties defined in the property file, this method populates different Objects like childList and elementCount
	 * @param prop - Properties from which different objects like - parent, child, element count etc are created 
	 * @param childObjList - Used to store all the child objects details
	 * @param parentObj - Used to store parent object details
	 * @param countElement - Used to store count element details 
	 * @throws IOException - Throws IO exception
	 */
	public static void loadData(Properties prop, List<OperObj> childObjList,OperObj parentObj,OperObj countElement) throws IOException
    {
    	createParentObject(prop,parentObj);
    	creatCountElement(prop,countElement);
    	createChildObjectList(prop,childObjList);
    }
	
	/** Based on the properties defined in the property file, this method populates childList 
	 	 Sample Child properties are as below
		 nasa.neo.rest.child.1.name=CLOSEST_OBJECT_TO_EARTH
		 nasa.neo.rest.child.1.path=close_approach_data/miss_distance/kilometers
	     nasa.neo.rest.child.1.operation=smallest
		 nasa.neo.rest.child.1.displaymsg=Displaying closest object to earth, details as below
	 * @param prop - Properties from which different objects like - parent, child, element count etc are created
	 * @param childObjList - Used to store all the child objects details
	 * @throws IOException - Throws IO exception
	 */
	public static void createChildObjectList(Properties prop, List<OperObj> childObjList) throws IOException 
	{
		int count = Integer.parseInt(prop.getProperty(IConstants.CHILDNODECOUNT));
		OperObj childObj;
		for(int i=1;i<=count;i++)
		{
			childObj = new OperObj();
			childObj.setName(prop.getProperty(IConstants.CHILDNODENAME.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i)));
			childObj.setPath(prop.getProperty(IConstants.CHILDNODEPATH.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i)));
			childObj.setOperation(prop.getProperty(IConstants.CHILDNODEOPERATION.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i)));
			childObj.setDisplayMsg(prop.getProperty(IConstants.CHILDNODEDISMSG.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i)));
			childObj.setDisplayUnitMsg(prop.getProperty(IConstants.CHILDNODEDUNITSMSG.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i)));
			childObjList.add(childObj);
		}
	} 
	
	/** This method is used to display formatted output results on the console.
	 * @param elecount - This is used to display details around element count object
	 * @param parentObj - This is used to display details around parent object
	 * @param mapListCount - This contains all the objects 
	 * @param uniqueNodes - This is used to display raw JSON objects
	 * @param childObjList - This is used to display details around child objects
	 */
	public static void displayOutput(OperObj elecount,OperObj parentObj,Map<String,JsonObject> mapListCount,Map<String,JsonObject> uniqueNodes,List<OperObj> childObjList)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(IConstants.NEWLINECHAR);
		sb.append(IConstants.CONST1);
		sb.append(IConstants.NEWLINECHAR);
	   for (Map.Entry<String,JsonObject> entry : mapListCount.entrySet()) 
	   {
		   sb.append(MessageFormat.format(elecount.getDisplayMsg(),entry.getKey()));
		   sb.append(IConstants.NEWLINECHAR);
		   sb.append(IConstants.CONST1);
	   }
	   for (OperObj childNode : childObjList) 
	   {
		   sb.append(IConstants.NEWLINECHAR);
		   sb.append(childNode.getDisplayMsg());
		   sb.append(IConstants.NEWLINECHAR);
		   sb.append(MessageFormat.format(IConstants.DISPLAYCHILDRESULTREF,parentObj.getName(),childNode.getResultKey()));
		   sb.append(IConstants.NEWLINECHAR);
		   sb.append(MessageFormat.format(IConstants.DISPLAYCHILDRESULTVALUE,childNode.getDisplayUnitMsg(),childNode.getNumValue()));
		   sb.append(IConstants.NEWLINECHAR);
		   sb.append(IConstants.CONST1);
	   }
	   logger.info(sb.toString());

	}
	
	/** This method is used to set parent Object - Path at which parent/root is present for example nasa.neo.rest.parent.node=neo_reference_id
	 * @param prop - Properties from which different objects like - parent, child, element count etc are created
	 * @param parentObj - Used to store parent Obj details
	 * @throws IOException -  Throws IO exception
	 */
	public static void createParentObject(Properties prop,OperObj parentObj) throws IOException 
	{
		parentObj.setName(prop.getProperty(IConstants.PARENTDISPLAYNAME));
		parentObj.setPath(prop.getProperty(IConstants.PARENTNODE));
	} 

	/** This method is used to set total element count present in the JSON object for example nasa.neo.rest.elementcount.path=element_count
	 * @param prop - Properties from which different objects like - parent, child, element count etc are created
	 * @param countElement - Used to store count element details
	 * @throws IOException - Throws IO exception
	 */
	public static void creatCountElement(Properties prop,OperObj countElement) throws IOException 
	{
		countElement.setPath(prop.getProperty(IConstants.ELEMENTCOUNT));
		countElement.setDisplayMsg(prop.getProperty(IConstants.ELEMENTCOUNTDISPLAYMSG));
	}
	
	/** This method evaluates whether proxy settings are required to be set or not based on nasa.neo.network.useproxy property 
	 *  Possible values true and false. True represent proxy settings needs to be enabled and false means vice-versa
	 * @param prop - To extract proxy settings from prop
	 */
	public static void evalProxySettings(Properties prop)
	{
		 if(IConstants.TRUE.equalsIgnoreCase(prop.getProperty(IConstants.PROXYUSE)))
		 {
	         Properties systemSettings = System.getProperties();
	         System.setProperty(IConstants.JAVASYSPROXIES, IConstants.TRUE);
	         systemSettings.put(IConstants.JAVAPROXYSET, IConstants.TRUE);
	         systemSettings.put(IConstants.JAVAPROXYHOSTNAME, prop.getProperty(IConstants.PROXYHOSTNAME));
	         systemSettings.put(IConstants.JAVAPROXYHOSTPORT, prop.getProperty(IConstants.PROXYHOSTPORT));
	         systemSettings.put(IConstants.JAVAPROXYUSERPNAME, prop.getProperty(IConstants.PROXYUSERNAME));
	         systemSettings.put(IConstants.JAVAPROXYUSERPASSWD, prop.getProperty(IConstants.PROXYUSERPASSWORD));
		 }
	}

	/** This method validates whether both start_date and end_date are provided by user and its in correct format i.e yyyy-MM-dd.
	 * 	The end_date should be always greater than start_date and difference between the two should not be more than 7 days.
	 * @param propertyFile - inputs provided by user
	 * @return - true is returned in case if all the validations are successfully passed.
	 */
	public static boolean validateDate(String[] propertyFile)
	{
		boolean validationSuccess = false;
		String start_date = propertyFile[1];
		String end_date = propertyFile[2];
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
	    Date startDate;
	    Date endDate;
		try {
			startDate = formatter.parse(start_date);
			endDate = formatter.parse(end_date);
			if(endDate.getTime() - startDate.getTime()<0)
			{
				logger.error(MessageFormat.format(IConstants.DATEVALERRORSTARTENDDATE,propertyFile[1],propertyFile[2]));
				return validationSuccess;
			}
			long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
		    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		    if(diff>7)
		    {
		    	logger.error(MessageFormat.format(IConstants.DATEVALERRORDIFFERROR,propertyFile[1],propertyFile[2]));
		    	return validationSuccess;
		    }
		    
		} catch (ParseException e)
		{
			logger.error(MessageFormat.format(IConstants.DATEVALERROR,propertyFile[1],propertyFile[2]));
			return validationSuccess; 
		}  
		return true;
	}

	
}
