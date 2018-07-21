package nasa.neo.rest.client;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

/** This testing class is used to test Utility Functions defined in UtilFunction
 * @author GGARG
 *
 */
public class UtilFunctionTest 
{
	static Properties props ;
	static StringBuilder errMsg = new StringBuilder(); 
	
	/** Setup the data before test execution. All the correct data is loaded in setUp method 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUp() throws Exception
	{
		String[] datasetvalidinputs= {"config.properties","2018-08-28","2018-08-29"};
		LoadConfigurationData loadConfData = new LoadConfigurationData();
		loadConfData.loadConfData(datasetvalidinputs);
		props = loadConfData.getProp();
	}

	/** 
	 * Tests are done with different data sets to validate isValidOperation is working correctly or not
	 */
	@Test
	public void testIsValidOperation() 
	{
		//Data Set 1 -> Valid Input
		assertTrue("Failure - Condition evaluated wrongly - smallest is valid Operation",UtilFunction.isValidOperation("smallest"));
		//Data Set 2 -> Valid Input
		assertTrue("Failure - Condition evaluated wrongly - largest is valid Operation",UtilFunction.isValidOperation("largest"));
		//Data Set 3 -> Invalid Input 
		assertFalse("Failure - Condition evaluated wrongly - biggest isn't valid Operation",UtilFunction.isValidOperation("biggest"));
		//Data Set 4 -> Invalid null Input 
		assertFalse("Failure - Condition evaluated wrongly - null isn't valid Operation", UtilFunction.isValidOperation(null));
		//Data Set 5 -> Invalid empty Input
		assertFalse("Failure - Condition evaluated wrongly - empty str isn't valid Operation", UtilFunction.isValidOperation(""));
	}
	/** 
	 * Tests are done with different data sets to validate isValidString is working correctly or not
	 */
	@Test
	public void testIsValidString() 
	{
		//Data Set 1 -> Invalid null Input 
		assertFalse("Failure - Condition evaluated wrongly - null should be evalauted false", UtilFunction.isValidString(null));
		//Data Set 2 -> Invalid empty Input
		assertFalse("Failure - Condition evaluated wrongly - empty should be evalauted false",UtilFunction.isValidString(""));
		//Data Set 3 -> valid string input
		assertTrue("Failure - Condition evaluated wrongly - validStr should be evalauted true", UtilFunction.isValidString("validStr"));
	}

	/** 
	 * Tests are done with different data sets to validate isValidNumber is working correctly or not
	 */
	@Test
	public void testIsValidNumber() 
	{
		//Data Set 1 -> Invalid null Input 
		assertFalse("Failure - Condition evaluated wrongly - null should be evalauted false",UtilFunction.isValidNumber(null));
		//Data Set 2 -> Invalid non-numeric Input
		assertFalse("Failure - Condition evaluated wrongly - non-numeric should be evalauted false",UtilFunction.isValidNumber("non-numeric"));
		//Data Set 3 -> Valid numeric Input
		assertTrue("Failure - Condition evaluated wrongly - integer should be evalauted true", UtilFunction.isValidNumber("56"));
	}
	
	/** Utility Method to validate if mandatory object properties are not present 
	 * This method first removes the given key from properties and then checks if validation fails and error is returned.
	 * @param key
	 * @param oper - 
	 * 			1 represent child method validation. 
	 * 			2 represent parent method validation. 
	 * 			3 for validating element count.
	 * 			4 for validating URL. 
	 * @throws IOException
	 */
	public void validateMissingProp(String key,int oper) throws IOException
	{
		errMsg.setLength(0);
		String value = props.getProperty(key); 
		props.remove(key);
		boolean validationFailed = false;
		switch(oper)
		{
			case 1:
				validationFailed = UtilFunction.validateChild(props,errMsg);
				break;
			case 2:
				validationFailed = UtilFunction.validateParent(props,errMsg);
				break;
			case 3:
				validationFailed = UtilFunction.validateCountElement(props,errMsg);
				break;
			case 4:
				validationFailed = UtilFunction.validateURL(props,errMsg);
				break;
		}	
		assertTrue(errMsg.toString(), validationFailed);
		assertTrue(errMsg.toString().length()>0);
		props.setProperty(key, value);
		errMsg.setLength(0);
	}
	
	/**
	 * Method to validate missing mandatory properties from child object
	 */
	@Test
	public void testValidateChild_MissingMandatoryProps_Fail() 
	{
		try 
		{	
			validateMissingProp(IConstants.CHILDNODECOUNT,1); //Data Set 1 -> Remove child count and do validation
			validateMissingProp(IConstants.CHILDNODENAME.replace(IConstants.CHILDREPSTR, "1"),1); //Data Set 2 -> Remove child name and do validation
			validateMissingProp(IConstants.CHILDNODEPATH.replace(IConstants.CHILDREPSTR, "1"),1); //Data Set 3 -> Remove child path and do validation
			validateMissingProp(IConstants.CHILDNODEOPERATION.replace(IConstants.CHILDREPSTR, "1"),1); //Data Set 4 -> Remove child operation and do validation
			validateMissingProp(IConstants.CHILDNODEDISMSG.replace(IConstants.CHILDREPSTR, "1"),1); //Data Set 5 -> Remove child generic message and do validation
			validateMissingProp(IConstants.CHILDNODEDUNITSMSG.replace(IConstants.CHILDREPSTR, "1"),1); //Data Set 6 -> Remove child unit message and do validation
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			assertTrue("Unexpected exception",false);
		}
	}
	
	/**
	 * Method to validate missing mandatory properties from child object
	 */
	@Test
	public void testValidateParentCountElementURL_MissingMandatoryProps_Fail() 
	{
		try 
		{
			validateMissingProp(IConstants.PARENTNODE,2); //Data Set 1 -> Remove parent node and do validation
			validateMissingProp(IConstants.PARENTDISPLAYNAME,2); //Data Set 2 -> Remove parent node display name and do validation
			validateMissingProp(IConstants.ELEMENTCOUNT,3); //Data Set 3 -> Remove element count and do validation
			validateMissingProp(IConstants.ELEMENTCOUNTDISPLAYMSG,3); //Data Set 4 -> Remove element count display message and do validation
			validateMissingProp(IConstants.URL,4);//Data Set 5 -> Remove URL and do validation
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			assertTrue("Unexpected exception",false);
		}
	}
	/** Utility Method to validate if mandatory object properties are present and no validation failure should happen 
	 * @param oper - 
	 * 			1 represent child method validation. 
	 * 			2 represent parent method validation. 
	 * 			3 for validating element count.
	 * 			4 for validating URL. 
	 * @throws IOException
	 */
	public void validateNoMissingProp(int oper) throws IOException
	{
		errMsg.setLength(0);
		boolean validationFailed = false;
		switch(oper)
		{
			case 1:
				validationFailed = UtilFunction.validateChild(props,errMsg);
				break;
			case 2:
				validationFailed = UtilFunction.validateParent(props,errMsg);
				break;
			case 3:
				validationFailed = UtilFunction.validateCountElement(props,errMsg);
				break;
			case 4:
				validationFailed = UtilFunction.validateURL(props,errMsg);
				break;
		}	
		assertFalse("There should be no error msg="+errMsg.toString()+". Validation flag ="+validationFailed, validationFailed);
		assertTrue(errMsg.toString().length()==0);
		errMsg.setLength(0);
	}
	
	/**
	 * Validates - Child, Parent, ElementCount, URL -> All the mandatory parameters are present, no error should be recorded
	 */
	@Test
	public void testValidateChildParentCountElementURL_NoMissingMandatoryProp_Pass() 
	{
		try 
		{
			validateNoMissingProp(1); //Data set 1 - Test for child
			validateNoMissingProp(2); //Data set 2 - Test for parent
			validateNoMissingProp(3); //Data set 3 - Test for count element
			validateNoMissingProp(4); //Data set 4 - Test for URL
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			assertTrue("Unexpected exception",false);
		}
	}
	
	/** Utility Method to validate with different data sets 
	 * @param key - Key for which value needs to be updated 
	 * @param value - new value for which test needs to be performed 
	 * @param oper - 
	 * 			1 represent child method validation. 
	 * 			2 represent parent method validation. 
	 * 			3 for validating element count.
	 * 			4 for validating URL. 
	 * @throws IOException
	 */
	public void validateWithDifferntDataSets(String key,String value,int oper) throws IOException
	{
		errMsg.setLength(0);
		String oldValue = props.getProperty(key); 
		props.setProperty(key, value);
		boolean validationFailed = false;
		switch(oper)
		{
			case 1:
				validationFailed = UtilFunction.validateChild(props,errMsg);
				break;
			case 2:
				validationFailed = UtilFunction.validateParent(props,errMsg);
				break;
			case 3:
				validationFailed = UtilFunction.validateCountElement(props,errMsg);
				break;
			case 4:
				validationFailed = UtilFunction.validateURL(props,errMsg);
				break;
		}	
		assertTrue(errMsg.toString(), validationFailed);
		assertTrue(errMsg.toString().length()>0);
		props.setProperty(key, oldValue);
		errMsg.setLength(0);
	}

	

	@Test
	public void testValidateChildWithDifferntDataSets() 
	{
		try 
		{
			validateWithDifferntDataSets("nasa.neo.rest.child.operation.count","XX",1);
			validateWithDifferntDataSets("nasa.neo.rest.child.operation.count","",1);
		} 
		catch (IOException e)
		{
			e.printStackTrace();
			assertTrue("Unexpected exception",false);
		}
	}

	

	
	/** This method is used to validate whether all the objects are populated correctly or not. There can be possibility that by mistake wrong parameters might be set for an object. 
	 *  This method will do all such validations
	 * @throws IOException
	 */
	@Test
	public void testLoadData() throws IOException 
	{
		OperObj parentObj = new OperObj();   
		OperObj countElement = new OperObj();
		List<OperObj> childObjList = new ArrayList<OperObj>();

		UtilFunction.loadData(props, childObjList, parentObj, countElement);
		assertEquals("Failure : Path property of count element is not matching ", "element_count" ,countElement.getPath());
		assertEquals("Failure : DisplayMsg property of count element is not matching ", "The total number of Near Earth Objects (NEO) are {0}." ,countElement.getDisplayMsg());
		assertEquals("Failure : Name property of parent element is not matching ", "neo_reference_id" ,parentObj.getPath());
		assertEquals("Failure : No of children miss-match between evaluated function and property file ", 3 , childObjList.size());
		
		assertEquals("Failure : Name property of child-1 is not matching  ", "CLOSEST_OBJECT_TO_EARTH" , childObjList.get(0).getName());
		assertEquals("Failure : Path property of child-1 is not matching  ", "close_approach_data/miss_distance/kilometers" , childObjList.get(0).getPath());
		assertEquals("Failure : Operation property of child-1 is not matching  ", "smallest" , childObjList.get(0).getOperation());
		assertEquals("Failure : Display Msg property of child-1 is not matching  ", "Closest NEO to earth, details as below :" , childObjList.get(0).getDisplayMsg());
		assertEquals("Failure : Leaf node of child-1 is not set correctly  ", "kilometers" , childObjList.get(0).getLeafNode());
		String[] expectedPathArr = {"close_approach_data","miss_distance","kilometers"};
		assertArrayEquals("Failure : Path Arr  of child-1 is not set correctly  ", expectedPathArr , childObjList.get(0).getPathArr());
		
		String path ="/"+childObjList.get(0).getPath();
		childObjList.get(0).setPath(path);
		assertEquals("Failure : Path property of child-1 is not matching  ", "close_approach_data/miss_distance/kilometers" , childObjList.get(0).getPath());
		
		
	}

	/** This method is used parent method which do validations of all the child methods 
	 *  This method will do all such validations
	 * @throws IOException
	 */
	@Test
	public void testPropertyValidations() throws IOException 
	{
		assertTrue("Failure : All the properties are present so this function should be successful ", UtilFunction.propertyValidations(props, errMsg));
		validatePropertyMethod("nasa.neo.rest.child.operation.count");
		validatePropertyMethod(IConstants.PARENTNODE);
		validatePropertyMethod(IConstants.ELEMENTCOUNT);
		validatePropertyMethod(IConstants.URL);
	}
	
	/** This method is used to test propertyValidations - it removes the input key and then check whether error is generated or not
	 * @param key
	 * @throws IOException
	 */
	public void validatePropertyMethod(String key) throws IOException
	{
		errMsg.setLength(0);
		String value = props.getProperty(key); 
		props.remove(key);
		boolean validationSucess = UtilFunction.propertyValidations(props, errMsg);
		assertFalse(errMsg.toString(), validationSucess);
		assertTrue(errMsg.toString().length()>0);
		props.setProperty(key, value);
		errMsg.setLength(0);
	}
}