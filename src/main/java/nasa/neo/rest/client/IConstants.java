package nasa.neo.rest.client;

/** This is interface file used to define constants
 * @author GGARG
 *
 */
public interface IConstants
{
	 // Following constants are used to read Proxy settings from property file
	public String PROXYUSE ="nasa.neo.network.useproxy"; 
	public String PROXYHOSTNAME = "nasa.neo.network.https.proxyhost";
	public String PROXYHOSTPORT	= "nasa.neo.network.https.proxyport";
	public String PROXYUSERNAME="nasa.neo.network.https.proxyuser";
	public String PROXYUSERPASSWORD="nasa.neo.network.https.password";
	
	 // Following constants are java proxy settings required to set-in for proxy 
	public String JAVASYSPROXIES ="java.net.useSystemProxies";
	public String JAVAPROXYSET = "proxySet";
	public String JAVAPROXYHOSTNAME	= "https.proxyHost";
	public String JAVAPROXYHOSTPORT="https.proxyPort";
	public String JAVAPROXYUSERPNAME="https.proxyUser";
	public String JAVAPROXYUSERPASSWD="https.proxyPassword";

	//Following are constants required to read from property-file 
	public String URL ="nasa.neo.rest.api.url";
	public String LEFTOVERURL ="&start_date={0}&end_date={1}";
	public String PARENTNODE ="nasa.neo.rest.parent.node";
	public String PARENTDISPLAYNAME ="nasa.neo.rest.parent.name";
	public String CHILDNODECOUNT ="nasa.neo.rest.child.operation.count";
	public String CHILDNODENAME ="nasa.neo.rest.child.{n}.name";
	public String CHILDNODEPATH ="nasa.neo.rest.child.{n}.path";
	public String CHILDNODEOPERATION ="nasa.neo.rest.child.{n}.operation";
	public String CHILDNODEDISMSG ="nasa.neo.rest.child.{n}.displaymsg";
	public String CHILDNODEDUNITSMSG ="nasa.neo.rest.child.{n}.displayunitmsg";
	public String ELEMENTCOUNT = "nasa.neo.rest.elementcount.path";
	public String ELEMENTCOUNTDISPLAYMSG = "nasa.neo.rest.elementcount.displaymsg";
	
	//Basic constants defined to remove hardcording
	public String TRUE="true";
	public String CHILDREPSTR="{n}";
	public String EMPTYSTR="";
	public String DATEFORMAT="yyyy-MM-dd";
	public String PATHSPLITTER="/";
	public String GETOPERATION="GET";
	
	 //Regex used to validate URL pattern 
	public String REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	 //Constants defined for Operation 
	public String OPERSMALLEST = 	"smallest";
	public String OPERLARGEST = 	"largest";
	public String OPERATIONLIST ="smallest,largest";
	
	// Error Messages  
	public String ELEMENTCOUNTVALIDATIONMSG ="{0}. The element count path ({1}) should be non empty and valid e.g. element_count. \n";
	public String ELEMENTCOUNTVALIDATIONDISPLAYMSG ="{0}. The display message for element count ({1}) should be non empty and valid e.g. The total number of Near Earth Objects (NEO) are. \n";
	public String PARENTVALIDATIONMSG ="{0}. The parent node path ({1}) should be non empty and valid e.g. near_earth_objects/(date)/neo_reference_id. \n";
	public String PARENTVALIDATIONDISPLAYNAMEMSG ="{0}. The parent display name ({1}) should be non empty and valid e.g. NEO Reference ID. \n";
	public String URLVALIDATIONMSG ="{0}. The URL ({1}) should be non empty and valid URL format e.g. https://api.nasa.gov/neo/rest/v1/feed/today?detailed=true&api_key=DEMO_KEY. \n";
	public String CONFFILEMISSING ="\nPlease specify mandatory parameters. Sample usage 'java -cp neorestapi-0.0.1-SNAPSHOT.jar "
			+ "nasa.neo.rest.client.Runner config.properties <<start_date>>  <<end_date>>' \n"
			+ "Format of start date / end date is yyyy-mm-dd for example 2018-08-12.\n"
			+ "The difference between start date and end date should not be more than 7 days.";
	public String DATEVALERROR = "\nPlease check the date formats (yyyy-mm-dd for example 2018-08-12).\n"
			+ "Please ensure the difference between start date and end date should not be more than 7 days.\n"
			+ "Input dates provided by user are: Start Date = {0} and End Date {1}. ";
	public String DATEVALERRORSTARTENDDATE = "\nEnd date should be greater than Start Date.\n"
			+ "Please ensure the difference between start date and end date should not be more than 7 days.\n"
			+ "Input dates provided by user are: Start Date = {0} and End Date {1}. ";
	public String DATEVALERRORDIFFERROR = "\nPlease ensure the difference between start date and end date should not be more than 7 days\n"
			+ "Input dates provided by user are: Start Date = {0} and End Date {1}. ";
	public String BASICVALSTR = "\nBasic Validations failed :\n";
	public String JSONPATHERROR = " \n ERROR: Child = {0}  \n Path Defined in config file = {1} \n Problematic Path = <<{2}>>. Please fix in config file before proceeding. Program terminated.";
	public String CHILDVALIDATIONMSG ="{0}. The child object should contain nasa.neo.rest.child.{1} all parameters like name,path,operation,displaymsg, displayunitmsg. Valid values for Operation are "+OPERATIONLIST+". \n";
	public String CHILDCOUNTVALIDATIONMSG ="{0}. The child count ({1}) should be non empty and valid e.g. 2. \n";
	public String ELEMENTNOTFOUND = "Element defined against <<{0}>> property not found. Value provided in config file <<{1}>>. Please fix the configuration file. Program terminated.";
	public String ELEMENTNOTLEAF = "Element defined against <<{0}>> property is not leaf node e.g element_count/neo_reference_id etc. Value provided in config file <<{1}>>. Please fix the configuration file. Program terminated.";
	public String CHILDELEMENTNOTLEAF = "\nFor child <<{0}>> path is not correctly set. Path should have leaf node e.g estimated_diameter_min or miles_per_hour etc are valid leaf nodes. \n"
			+ "Value provided in config file <<{1}>>. Please fix the configuration file. Program terminated.";
	public String CHILDELEMENTNOTVALIDNUMBER = "\nFor child <<{0}>> path is not correctly set. Path should have numeric leaf node e.g estimated_diameter_min or miles_per_hour etc are valid leaf nodes. \n"
			+ "Value provided in config file <<{1}>>, Evaluated value from reponse is <<{2}>> comparison can not be performed. \nPlease fix the configuration file. Program terminated.";
	
	//Constants defined for printing result data
	public String DISPLAYCHILDRESULTREF = "{0} = {1}";
	public String DISPLAYCHILDRESULTVALUE = "{0} = {1}";
	public String NEWLINECHAR = "\n";
	public String CONST1 ="========================================================================================";
	
	//HTTP Error constants
	public String HTTPERRORCODE400 = "HttpResponseCode: 400 Internal Server error. Please check the input URL and date parameters and formatting.";
	public String HTTPERRORCODE401 = "HttpResponseCode: 401 Unauthorized to use API.";
	public String HTTPERRORCODE403 = "HttpResponseCode: 403 Forbidden to use API (check API key).";
	public String HTTPERRORCODE404 = "HttpResponseCode: 404 Requested resouce not found check URL.";
	
	//Connection related Errors
	public String NETEXEPMALFORMED = "Malformed URL Exception. Please check the URL.";
	public String NETEXEPCONNECT = "Connection Timeout to external host. Please check proxy settings or URL. Program terminated. ";
	public String NETEXEPUNKNOWN = "Unknown Host Exception  to external host. Please check proxy settings or URL. Program terminated.";
}
