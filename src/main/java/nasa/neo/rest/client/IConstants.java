package nasa.neo.rest.client;

public interface IConstants
{
	
	public String PROXYUSE ="nasa.neo.network.useproxy"; 
	public String PROXYHOSTNAME = "nasa.neo.network.https.proxyhost";
	public String PROXYHOSTPORT	= "nasa.neo.network.https.proxyport";
	public String PROXYUSERNAME="nasa.neo.network.https.proxyuser";
	public String PROXYUSERPASSWORD="nasa.neo.network.https.password";
	public String JAVASYSPROXIES ="java.net.useSystemProxies"; 
	public String JAVAPROXYSET = "proxySet";
	public String JAVAPROXYHOSTNAME	= "https.proxyHost";
	public String JAVAPROXYHOSTPORT="https.proxyPort";
	public String JAVAPROXYUSERPNAME="https.proxyUser";
	public String JAVAPROXYUSERPASSWD="https.proxyPassword";
	public String TRUE="true";		
	
	public String URL ="nasa.neo.rest.api.url";
	public String PARENTNODE ="nasa.neo.rest.parent.node";
	public String CHILDNODECOUNT ="nasa.neo.rest.child.operation.count";
	public String CHILDNODENAME ="nasa.neo.rest.child.{n}.name";
	public String CHILDNODEPATH ="nasa.neo.rest.child.{n}.path";
	public String CHILDNODEOPERATION ="nasa.neo.rest.child.{n}.operation";
	public String CHILDNODEDISMSG ="nasa.neo.rest.child.{n}.displaymsg";
	public String ELEMENTCOUNT = "nasa.neo.rest.elementcount.path";
	public String ELEMENTCOUNTDISPLAYMSG = "nasa.neo.rest.elementcount.displaymsg";
	public String CHILDREPSTR="{n}";
	public String EMPTYSTR="";
	public String DATEREPSTR="(date)";
	public String DATEFORMAT="yyyy-MM-dd";
	public String PATHSPLITTER="/";
	public String REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	public String ELEMENTCOUNTVALIDATIONMSG ="{0}. The element count path ({1}) should be non empty and valid e.g. element_count. \n";
	public String PARENTVALIDATIONMSG ="{0}. The parent node path ({1}) should be non empty and valid e.g. near_earth_objects/(date)/neo_reference_id. \n";
	public String URLVALIDATIONMSG ="{0}. The URL ({1}) should be non empty and valid URL format e.g. https://api.nasa.gov/neo/rest/v1/feed/today?detailed=true&api_key=DEMO_KEY. \n";
	public String CONFFILEMISSING ="Please specify config file. Sample usage java -cp neorestapi-0.0.1-SNAPSHOT.jar nasa.neo.rest.client.EvaluateJSONOperations config.properties";
	public String OPERSMALLEST = 	"smallest";
	public String OPERLARGEST = 	"largest";
	public String OPERATIONLIST ="smallest,largest";
	public String BASICVALSTR = "Basic Validations failed :\n";
	public String JSONPATHERROR = " \n ERROR: Child = {0}  \n JSON Path = {1} \n Problematic Path = {2}. Please fix before proceeding.";
	
	public String DISPLAYCHILDRESULT = "Child Name = {0} :: {1} = {2}  ::  Operation = {3} :: Path = {4} :: Value = {5} \n";
	public String PRINTJSONOBJ = "PrintJsonObject = \n {0} \n";
	
	
	public String CHILDVALIDATIONMSG ="{0}. The child object should contain nasa.neo.rest.child.{1} all parameters like name,path,operation,displaymsg. Valid values for Operation are "+OPERATIONLIST+". \n";
	public String CHILDCOUNTVALIDATIONMSG ="{0}. The child count ({1}) should be non empty and valid e.g. 2. \n";
	
	public String HTTPERRORCODE400 = "HttpResponseCode: 400 Internal Server error. Please check the input URL and date parameters and formatting.";
	public String HTTPERRORCODE401 = "HttpResponseCode: 401 Unauthorized to use API.";
	public String HTTPERRORCODE403 = "HttpResponseCode: 403 Forbidden to use API (check API key).";
	public String HTTPERRORCODE404 = "HttpResponseCode: 404 Requested resouce not found check URL.";
	
	public String CONST1 ="==================================================================================================================================================";
	public String CONST2 ="===============================================DISPLAYING RESULTS=================================================================================";
    
}
