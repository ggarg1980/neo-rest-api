package nasa.neo.rest.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;

/** This testing class is used to test Runner
 * @author GGARG
 *
 */
public class RunnerTest 
{

	/** 
	 * Tests are done with different data sets to validate that functions are being executed correctly and not breaking
	 */
	@Test()
	public void testRunner() throws Exception 
	{
		//Initialize required classes
		GenericAPIInterface genericAPIinterface = new MockRestAPI();
		Runner runner = new Runner();

		// Data set -1 -> wrong no of inputs provided
		String[] dataSetIncorrect = {"config.properties"};
		assertFalse("Failure : This should return false as data is not provided correctly", runner.dataLoad(dataSetIncorrect,genericAPIinterface));
		
		// Data set -2 -> All the valid inputs are provided
		String[] dataSetCorrect = {"config.properties","2018-10-08","2018-10-12"};
		assertTrue("Failure : All parameters are correctly provided. This should be executed succesfully", runner.dataLoad(dataSetCorrect,genericAPIinterface));
		
		//Data set - 3 -> Purposefully set wrong path for one of the child
		List<OperObj> childList = runner.getChildObjList();
		String path = childList.get(0).getPath();
		childList.get(0).setPath("estimated_diameter/XXY/estimated_diameter_max");
		assertFalse("Failure : This should fail with wrong path data ", runner.executeJSONFunc());
		
		//Data set - 4 -> All the entries are correct, this should work
		childList.get(0).setPath(path);
		assertTrue("Failure : All parameters are correctly provided. This should be executed succesfully ", runner.executeJSONFunc());
		
		//Evaluating All the test results 
		assertEquals("Failure : Element count should be 14.  ", "14", runner.getMapListCount().entrySet().iterator().next().getKey());
		
		assertEquals("Failure : NEO ID should be 3014184.  ", "3014184", runner.getChildObjList().get(0).getResultKey());
		assertEquals("Failure : Value should be 1620736.0  ", "1620736.0", runner.getChildObjList().get(0).getNumValue().toString());
		
		assertEquals("Failure : NEO ID should be 2358744.  ", "2358744", runner.getChildObjList().get(1).getResultKey().toString());
		assertEquals("Failure : Value should be 1.033.0  ", "1.0328564805", runner.getChildObjList().get(1).getNumValue().toString());
		
		assertEquals("Failure : NEO ID should be 3750676.  ", "3750676", runner.getChildObjList().get(2).getResultKey().toString());
		assertEquals("Failure : Value should be 28.1.  ", "28.1", runner.getChildObjList().get(2).getNumValue().toString());
	}
}