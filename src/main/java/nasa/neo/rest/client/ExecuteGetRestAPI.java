package nasa.neo.rest.client;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class ExecuteGetRestAPI extends GenericExecuteRestAPI {

	static final Logger logger = Logger.getLogger(ExecuteGetRestAPI.class);
	public ExecuteGetRestAPI() 
	{
		super();
	}
	//Properties prop;
	public ExecuteGetRestAPI(String url,Properties prop) 
	{
		super(url,"GET",prop);
		
	}

	@Override
	public String callRestAPI() throws MalformedURLException,Exception
	{
		StringBuilder strbuilder = new StringBuilder();
		HttpURLConnection conn = null;
		Scanner sc = null;
		try
		{
			URL restUrl = new URL(url);
			conn = (HttpURLConnection)restUrl.openConnection();
			conn.setRequestMethod(operation);
			conn.connect();
			int responsecode = conn.getResponseCode();
			switch(responsecode)
			{
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
			sc = new Scanner(restUrl.openStream());
			while(sc.hasNext())
			{
				strbuilder.append(sc.nextLine());
			}
			logger.debug("\nJSON Response in String format \n"+strbuilder); 
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
		return strbuilder.toString();
	}

}
