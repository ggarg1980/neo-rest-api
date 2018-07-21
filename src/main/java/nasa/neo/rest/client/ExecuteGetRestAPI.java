package nasa.neo.rest.client;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.apache.log4j.Logger;

/** This class is used to call NEO rest API
 * @author GGARG
 *
 */
public class ExecuteGetRestAPI extends GenericExecuteRestAPI implements GenericAPIInterface  {

	/**
	 *  Logger defined for logging 
	 */
	static final Logger logger = Logger.getLogger(ExecuteGetRestAPI.class);
	/**
	 *  Default constructor 
	 */
	public ExecuteGetRestAPI() 
	{
		
	}

	/* (non-Javadoc)
	 * @see nasa.neo.rest.client.GenericExecuteRestAPI#callRestAPI()
	 */
	@Override
	public String callRestAPI(String url) throws Exception
	{
		logger.debug(" callRestAPI:: entered :: url="+url);
		StringBuilder strbuilder = new StringBuilder();
		HttpURLConnection conn = null;
		Scanner sc = null;
		try
		{
			//Create URL object with the URL passed from property file
			URL restUrl = new URL(url);
			conn = (HttpURLConnection)restUrl.openConnection();
			conn.setRequestMethod(IConstants.GETOPERATION);
			//Call the URL
			conn.connect();
			int responsecode = conn.getResponseCode();
			logger.debug(" callRestAPI:: responsecode="+responsecode);
			//Evaluate response code in case if its other than 200 throw appropriate exception 
			switch(responsecode)
			{
				case 400:
					logger.error(IConstants.HTTPERRORCODE400);
					throw new RuntimeException(IConstants.HTTPERRORCODE400);
			
				case 401:
					logger.error(IConstants.HTTPERRORCODE401);
					throw new RuntimeException(IConstants.HTTPERRORCODE401);
				case 403:
					logger.error(IConstants.HTTPERRORCODE403);
					throw new RuntimeException(IConstants.HTTPERRORCODE403);
				case 404:
					logger.error(IConstants.HTTPERRORCODE404);
					throw new RuntimeException(IConstants.HTTPERRORCODE404);
				case 200:
					break;
			}
			//Create response string as response code is 200
			sc = new Scanner(restUrl.openStream());
			while(sc.hasNext())
			{
				strbuilder.append(sc.nextLine());
			}
		}
		catch(java.net.MalformedURLException e)
		{
			logger.error(IConstants.NETEXEPMALFORMED);
			throw new RuntimeException(IConstants.NETEXEPMALFORMED);
		}
		catch(java.net.ConnectException e)
		{
			logger.error(IConstants.NETEXEPCONNECT);
			throw new RuntimeException(IConstants.NETEXEPCONNECT);
		}
		catch(java.net.UnknownHostException e)
		{
			logger.error(IConstants.NETEXEPUNKNOWN);
			throw new RuntimeException(IConstants.NETEXEPUNKNOWN);
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			throw e;
		}
		finally
		{
			if(sc!=null)
				sc.close();	
			if(conn!=null)
				conn.disconnect();
		}
		logger.debug(" callRestAPI:: exit :: strbuilder="+strbuilder);
		return strbuilder.toString();
	}
}
