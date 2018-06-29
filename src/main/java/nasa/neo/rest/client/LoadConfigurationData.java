package nasa.neo.rest.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver; 

public class LoadConfigurationData 
{
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
	
	private static OperObj parentObj;
	
	private static List<OperObj> childObjList;
	
	public static void loadConfData(String propertyFile) throws IOException 
	{
		ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver(
				new FileSystemResourceLoader());

		PropertiesFactoryBean propertyBean = new PropertiesFactoryBean();
		propertyBean.setLocation(resourceResolver.getResource(propertyFile));
		propertyBean.afterPropertiesSet();
		prop = propertyBean.getObject();
		System.out.println(" -> "+prop);
		createParentObject(prop);
		createChildObjectList(prop);
	} 

	public static void createChildObjectList(Properties prop) throws IOException 
	{
		childObjList = new ArrayList<OperObj>();
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
	
	public static OperObj createParentObject(Properties prop) throws IOException 
	{
		parentObj = new OperObj();
		String updatedStr = evaluteParentNode(prop.getProperty(IConstants.PARENTNODE));
		parentObj.setPath(updatedStr);
		return parentObj;
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
		evaluteParentNode("near_earth_objects/2018-06-29/neo_reference_id");
	}
	
	
}
