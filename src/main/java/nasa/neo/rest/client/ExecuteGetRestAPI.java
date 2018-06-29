package nasa.neo.rest.client;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ExecuteGetRestAPI extends GenericExecuteRestAPI {

	public ExecuteGetRestAPI() 
	{
		super();
	}

	public ExecuteGetRestAPI(String url) 
	{
		super(url,"GET");
	}

	@Override
	public String callRestAPI() throws MalformedURLException,Exception
	{
		StringBuilder strbuilder = new StringBuilder();
		URL restUrl = new URL(url);
		HttpURLConnection conn = (HttpURLConnection)restUrl.openConnection();
		conn.setRequestMethod(operation);
		conn.connect();
		int responsecode = conn.getResponseCode();
		System.out.println("Response code is: " +responsecode);
		if(responsecode != 200)
		{
			
			Scanner sc = new Scanner(conn.getErrorStream());
			while(sc.hasNext())
			{
				strbuilder.append(sc.nextLine());
			}
			throw new RuntimeException("HttpResponseCode: " +responsecode+ strbuilder.toString());
		}
		else
		{
			Scanner sc = new Scanner(restUrl.openStream());
			while(sc.hasNext())
			{
				strbuilder.append(sc.nextLine());
			}
			System.out.println("\nJSON Response in String format"); 
			System.out.println(strbuilder);
			//Close the stream when reading the data has been finished
			sc.close();
		}
		conn.disconnect();
		return strbuilder.toString();
	}

}
