package nasa.neo.rest.client;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/** This testing class is used to test LoadConfigurationData
 * @author GGARG
 *
 */
public class LoadConfigurationDataTest 
{

	/** 
	 * Tests are done with different data sets to validate that functions are being executed correctly and not breaking
	 */
	@Test()
	public void testLoadConfData() throws Exception 
	{
		LoadConfigurationData loadConfData = new LoadConfigurationData();
		//Data set -1 -> Null input parameters 
		assertFalse("Failure : null file provided - validation should fail ",loadConfData.loadConfData(null));
		
		//Data set -2 -> empty parameters
		String[] datasetEmptyParameters = {"","",""};
		assertFalse("Failure : empty parameters are provided - validation should fail ",loadConfData.loadConfData(datasetEmptyParameters));
		
		//Data set -3 -> wrong date formats
		String[] datasetwrongdates= {"config.properties","kkkkk","kkkk"};
		assertFalse("Failure : empty parameters are provided - validation should fail ",loadConfData.loadConfData(datasetwrongdates));

		//Data set -4 -> start date greater than end date 
		String[] datasetbiggerstartdate= {"config.properties","2018-10-08","2018-10-06"};
		assertFalse("Failure : start date is bigger than end date - validation should fail ",loadConfData.loadConfData(datasetbiggerstartdate));

		//Data set -5 -> difference is more than 7 days 
		String[] datasetdiffdates= {"config.properties","2018-10-08","2018-10-28"};
		assertFalse("Failure : Difference between dates should is more than 7 days - validation should fail ",loadConfData.loadConfData(datasetdiffdates));
		
		//Data set -6 -> Dates are corrects but wrong file name 
		String[] datasetwrongfilename= {"wrong_file_path","2018-10-08","2018-10-12"};
		assertFalse("Failure : wrong file provided - validation should fail ",loadConfData.loadConfData(datasetwrongfilename));

		//Data set -7 -> All parameters are correct 
		String[] datasetvalidinputs= {"config.properties","2018-10-08","2018-10-12"};
		assertTrue("Failure : All correct parameters are provided - validation should pass",loadConfData.loadConfData(datasetvalidinputs));
	}

	
}