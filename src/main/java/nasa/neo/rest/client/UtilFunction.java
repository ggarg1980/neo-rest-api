package nasa.neo.rest.client;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class UtilFunction
{
	static final Logger logger = Logger.getLogger(UtilFunction.class);
	public static Integer counter = new Integer(1);
	public static boolean isValidOperation(String str)
	{
		boolean isValid = false;
		if(isValidString(str)&&IConstants.OPERATIONLIST.contains(str))
		{
			isValid = true;
		}
		return isValid;
	}
	
	public static boolean isValidString(String str)
	{
		boolean isValid = false;
		if(str!=null && str.length()>0)
		{
			isValid = true;
		}
		return isValid;
	}
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
	
	
	public static boolean validateChild(Properties prop,StringBuilder errMsg) throws IOException
	{
		boolean validationFailed = false;
		String str = prop.getProperty(IConstants.CHILDNODECOUNT);
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
			
			for(int i=1;i<=count;i++)
			{
				childNodeName=prop.getProperty(IConstants.CHILDNODENAME.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i));
				childNodePath=prop.getProperty(IConstants.CHILDNODEPATH.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i));
				childOpertion=prop.getProperty(IConstants.CHILDNODEOPERATION.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i));
				childNodeMsg=prop.getProperty(IConstants.CHILDNODEDISMSG.replace(IConstants.CHILDREPSTR, IConstants.EMPTYSTR+i));
				if(!isValidString(childNodeName) || !isValidString(childNodePath) || !isValidOperation(childOpertion) || !isValidString(childNodeMsg))
				{
					errMsg.append(MessageFormat.format(IConstants.CHILDVALIDATIONMSG,counter++, i));
					validationFailed = true;
				}
			}
		}
		return validationFailed;
	}
	public static boolean validateParent(Properties prop,StringBuilder errMsg) throws IOException
	{
		boolean validationFailed = false;
		String str = prop.getProperty(IConstants.PARENTNODE);
		if(!isValidString(str))
		{
			errMsg.append(MessageFormat.format(IConstants.PARENTVALIDATIONMSG,counter++, IConstants.PARENTNODE));
			validationFailed = true;
		}
		return validationFailed;
	}

	public static boolean validateCountElement(Properties prop,StringBuilder errMsg) throws IOException
	{
		boolean validationFailed = false;
		String str = prop.getProperty(IConstants.ELEMENTCOUNT);
		if(!isValidString(str))
		{
			errMsg.append(MessageFormat.format(IConstants.ELEMENTCOUNTVALIDATIONMSG,counter++, IConstants.ELEMENTCOUNT));
			validationFailed = true;
		}
		return validationFailed;
	}

	
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

    private static boolean IsMatch(String s, String pattern) 
    {
        try 
        {
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) 
        {
        	return false;
        }       
    } 
    public static void loadData(Properties prop, List<OperObj> childObjList,OperObj parentObj,OperObj countElement) throws IOException
    {
    	createParentObject(prop,parentObj);
    	creatCountElement(prop,countElement);
    	createChildObjectList(prop,childObjList);
    }
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
			childObjList.add(childObj);
		}
	} 
	
	public static void displayOutput(OperObj elecount,OperObj parentObj,Map<String,JsonObject> mapListCount,Map<String,JsonObject> uniqueNodes,List<OperObj> childObjList)
	{
		
	    Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		   
	    logger.info(IConstants.CONST1);
	    logger.info(IConstants.CONST2);
	    logger.info(IConstants.CONST1);
	   for (Map.Entry<String,JsonObject> entry : mapListCount.entrySet()) 
	   {
		   logger.info(MessageFormat.format(elecount.getDisplayMsg(),entry.getKey()));
	   }
	    
	   for (OperObj childNode : childObjList) 
	   {
		   logger.info(childNode.getDisplayMsg());
		   logger.info(MessageFormat.format(IConstants.DISPLAYCHILDRESULT,childNode.getName(), parentObj.getLeafNode(),childNode.getResultKey(),childNode.getOperation(), childNode.getPath(),childNode.getNumValue()));
		   logger.info(MessageFormat.format(IConstants.PRINTJSONOBJ,gson.toJson(uniqueNodes.get(childNode.getResultKey()))));
	   }
	}
	
	public static void createParentObject(Properties prop,OperObj parentObj) throws IOException 
	{
		//String updatedStr = evaluteParentNode(prop.getProperty(IConstants.PARENTNODE));
		parentObj.setPath(prop.getProperty(IConstants.PARENTNODE));
	} 

	public static void creatCountElement(Properties prop,OperObj countElement) throws IOException 
	{
		countElement.setPath(prop.getProperty(IConstants.ELEMENTCOUNT));
		countElement.setDisplayMsg(prop.getProperty(IConstants.ELEMENTCOUNTDISPLAYMSG));
	} 
	
	public static String evaluteParentNode(String parentNode) throws IOException 
	{
		Date date = new Date();  
		SimpleDateFormat formatter = new SimpleDateFormat(IConstants.DATEFORMAT);  
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String strDate = formatter.format(date);  
		String updatedStr = parentNode.replace(IConstants.DATEREPSTR, strDate);
		return updatedStr;
	} 
    
	public static void main(String args[]) throws IOException
	{
		System.out.println(" -> "+evaluteParentNode("near_earth_objects/(date)/neo_reference_id"));
	}
	

}
