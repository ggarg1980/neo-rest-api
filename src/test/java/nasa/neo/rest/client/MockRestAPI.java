package nasa.neo.rest.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;

/** This class is used to call MOCK rest API
 * @author GGARG
 *
 */
public class MockRestAPI  extends GenericExecuteRestAPI implements GenericAPIInterface  {

	/**
	 *  Logger defined for logging 
	 */
	static final Logger logger = Logger.getLogger(MockRestAPI.class);
	/**
	 *  Default constructor 
	 */
	public MockRestAPI() 
	{
	}
	
	/** This function is used to read Json Object from a file in form of string. This will help to Simulate API call
	 * @param filename
	 * @return
	 */
	public static String readJsonObject(String filename) {
		BufferedReader br = null;
		FileReader fr = null;
		StringBuilder sb = new StringBuilder();
		String sCurrentLine;
		try {

			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	@Override
	public String callRestAPI(String url) throws MalformedURLException, Exception {
		return readJsonObject("./src/test/resource/jsonResponse.txt");
	}
	
}
