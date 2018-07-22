# NEO-REST-API

“Near Earth Objects” using the NASA RESTful Web Service [NEO-REST-API](https://api.nasa.gov/api.html#NeoWS). </br>
Identify which NEO is the largest in size and which is the closest to Earth.  
Output the total number of NEOs, and the details retrieved for both the largest and closest NEOs identified.

## Getting Started
Please install following softwares on your system
1. Install JAVA (v1.8)
2. Install Maven (latest)
3. Register on NASA Open APIs using [NEO-REST-API-REGISTER](https://api.nasa.gov/index.html#apply-for-an-api-key) and get API-Key for calling APIs.

## Download
1. Please download code from  [Download](https://github.com/ggarg1980/neo-rest-api/) URL. Click on "Clone or Download" button and download ZIP file.
2. Unzip the ZIP file.
3. Inside the ZIP file where src folder is present. Open command prompt and run "mvn clean package" command.</br>
``` mvn clean package``` </br>
4. Check BUILD is successful. <b>target</b> folder contains following important artifacts  
```
   i. neorestapi-0.0.1-SNAPSHOT.jar - This jar is used to run NEO APIs.
   ii. neorestapi-0.0.1-SNAPSHOT-javadoc.jar - This jar contains all java-docs for the different class used in this program.
   iii. jacoco-ut\index.html - This html file contains the code coverage results for reference.
```
5. Go inside <b>target</b> folder and run the program using following command.
```
   java -cp neorestapi-0.0.1-SNAPSHOT.jar nasa.neo.rest.client.Runner ../config.properties <<start_date>> <<end_date>> 
   e.g. 
   java -cp neorestapi-0.0.1-SNAPSHOT.jar nasa.neo.rest.client.Runner ../config.properties 2018-08-16 2018-08-18 
```
<b>*Note:</b> Please give proper path of config.properties file is present. Please configure config.properties before running the program. Refer 'Configuring properties file' section below

### Configuring properties file - config.properties 
*NOTE: Before configuring the data in config file, please refer to the API response objects for better understanding (link on Valid URLs for response object).

```
###########  Sample config.properties file #####################################
###########  Please change parameters based on your requirement ################
################################################################################
#	Following are the Proxy settings in case if someone is setting behind the fire-wall
#	nasa.neo.network.useproxy - This property signifies whether proxy settings needs to be enabled (possible value true/false)
#	nasa.neo.network.https.proxyhost - Proxy host name of the organization for example - my.company.proxy.host
#	nasa.neo.network.https.proxyport- Proxy port for https communication for example - 8000
#	nasa.neo.network.https.proxyuser - Proxy user name to be used for communication for example ggarg
#	nasa.neo.network.https.password - Proxy password to be used for communication for example ggarg
################################################################################
nasa.neo.network.useproxy=false
nasa.neo.network.https.proxyhost=my.company.proxy.host
nasa.neo.network.https.proxyport=8000
nasa.neo.network.https.proxyuser=ggarg
nasa.neo.network.https.password=ggarg
################################################################################
#	Following properties are common properties 
#	nasa.neo.rest.api.url - This property represent the NEO REST URL to be called 
#	nasa.neo.rest.elementcount.path - This property shows the objects which are returned in the response. 
#	As per the current specs "element_count" should be used. Node name is required, no need to give absolute path.  
#	nasa.neo.rest.elementcount.displaymsg - This property is used for displaying message for the number of objects.
#	nasa.neo.rest.parent.name - This property is used in display message. 
#	nasa.neo.rest.parent.node - This property is used to determine all the unique objects returned from the response
#	As per the current specs "neo_reference_id" should be used. Node name is required, no need to give absolute path.
################################################################################
nasa.neo.rest.api.url=https://api.nasa.gov/neo/rest/v1/feed?api_key=DEMO_KEY
nasa.neo.rest.elementcount.path=element_count
nasa.neo.rest.elementcount.displaymsg=The total number of Near Earth Objects (NEO) are {0}.
nasa.neo.rest.parent.name=NEO Reference ID
nasa.neo.rest.parent.node=neo_reference_id
################################################################################
#	Following properties are used to define child and child operations that are required to be performed
#	nasa.neo.rest.child.operation.count -  This property defines how many child operations are required 
#	e.g if value is 1 i.e nasa.neo.rest.child.operation.count=1. Following tags will be defined once
#	nasa.neo.rest.child.1.name=CLOSEST_OBJECT_TO_EARTH
#	nasa.neo.rest.child.1.path=close_approach_data/miss_distance/kilometers
#	nasa.neo.rest.child.1.operation=smallest
#	nasa.neo.rest.child.1.displaymsg=Closest NEO to earth, details as below :
#	nasa.neo.rest.child.1.displayunitmsg=Miss Distance (in Kilometers) 
#
#	e.g if value is 2 i.e nasa.neo.rest.child.operation.count=2. Following tags will be defined twice (and so on)
#	nasa.neo.rest.child.1.name=CLOSEST_OBJECT_TO_EARTH
#	nasa.neo.rest.child.1.path=close_approach_data/miss_distance/kilometers
#	nasa.neo.rest.child.1.operation=smallest
#	nasa.neo.rest.child.1.displaymsg=Closest NEO to earth, details as below :
#	nasa.neo.rest.child.1.displayunitmsg=Miss Distance (in Kilometers) 
#	nasa.neo.rest.child.2.name=LARGEST_OBJECT
#	nasa.neo.rest.child.2.path=estimated_diameter/kilometers/estimated_diameter_max
#	nasa.neo.rest.child.2.operation=largest
#	nasa.neo.rest.child.2.displaymsg=Largest NEO in size, details as below :
#	nasa.neo.rest.child.2.displayunitmsg=Diameter (in Kilometers)
#
#	nasa.neo.rest.child.1.name - This property represents the name of the child. Used in displaying errors. 
#	Any name can be provided for e.g CLOSEST_OBJECT_TO_EARTH, LARGEST_OBJECT, ABS_MAG_H etc
#	nasa.neo.rest.child.1.path - Relative path from "parent node" (nasa.neo.rest.parent.node) till leaf node e.g close_approach_data/miss_distance/kilometers
#	nasa.neo.rest.child.1.operation - Operation that needs to be performed (currently only smallest/largest operations are supported)
#	nasa.neo.rest.child.1.displaymsg - User friendly message heading for example 'Closest NEO to earth, details as below :'
#	nasa.neo.rest.child.1.displayunitmsg - User friendly message for displaying units for example  'Diameter (in Kilometers)'
################################################################################
nasa.neo.rest.child.operation.count=3
nasa.neo.rest.child.1.name=CLOSEST_OBJECT_TO_EARTH
nasa.neo.rest.child.1.path=close_approach_data/miss_distance/kilometers
nasa.neo.rest.child.1.operation=smallest
nasa.neo.rest.child.1.displaymsg=Closest NEO to earth, details as below :
nasa.neo.rest.child.1.displayunitmsg=Miss Distance (in Kilometers) 

nasa.neo.rest.child.2.name=LARGEST_OBJECT
nasa.neo.rest.child.2.path=estimated_diameter/kilometers/estimated_diameter_max
nasa.neo.rest.child.2.operation=largest
nasa.neo.rest.child.2.displaymsg=Largest NEO in size, details as below :
nasa.neo.rest.child.2.displayunitmsg=Diameter (in Kilometers)

nasa.neo.rest.child.3.name=ABS_MAG_H
nasa.neo.rest.child.3.path=absolute_magnitude_h
nasa.neo.rest.child.3.operation=largest
nasa.neo.rest.child.3.displaymsg=Largest NEO in magnitude, details as below :
nasa.neo.rest.child.3.displayunitmsg=Absolute value
################################################################################```
```
## Sample Output file 
```
java -cp neorestapi-0.0.1-SNAPSHOT.jar nasa.neo.rest.client.Runner ../config.properties 2018-08-28 2018-08-29
2018-07-22 20:58:19,125 [main] INFO  org.springframework.beans.factory.config.PropertiesFactoryBean - Loading properties file from file [\Users\ggarg\eclipse-workspace\NEO-API\target\..\config.properties]
2018-07-22 20:58:25,133 [main] INFO  nasa.neo.rest.client.UtilFunction -
========================================================================================
The total number of Near Earth Objects (NEO) are 14.
========================================================================================
Closest NEO to earth, details as below :
NEO Reference ID = 3014184
Miss Distance (in Kilometers)  = 1,620,736
========================================================================================
Largest NEO in size, details as below :
NEO Reference ID = 2358744
Diameter (in Kilometers) = 1.033
========================================================================================
Largest NEO in magnitude, details as below :
NEO Reference ID = 3750676
Absolute value = 28.1
========================================================================================
```

### Major Classes involved 
Go to <b>target</b> folder and refer to neorestapi-0.0.1-SNAPSHOT-javadoc.jar for Java-Docs
```
1. ConfigPropertiesObjList
2. EvaluateJSONOperations
3. ExecuteGetRestAPI
4. GenericAPIInterface
5. GenericExecuteRestAPI
6. IConstants
7. LoadConfigurationData
8. OperObj
9. Runner
10.UtilFunction
```

## Authors

* **Gauav Garg** - *Initial work* 

