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

#### 1. nasa.neo.rest.api.url - This property is used to represent URL to be called
Valid URLs
1. [Date range Feed](https://api.nasa.gov/neo/rest/v1/feed?start_date=2018-08-29&end_date=2018-08-29&api_key=DEMO_KEY)

start_date & end_date are dates in format yyyy-MM-dd e.g. 2018-08-29

*Note: Please use DEMO_KEY or your own key retrieved in step.3 of 'Getting Started'

Example 

nasa.neo.rest.api.url=https://api.nasa.gov/neo/rest/v1/feed?start_date=2018-08-29&end_date=2018-08-29&api_key=DEMO_KEY

#### 2. nasa.neo.rest.elementcount.path - This element identifies the path where total count is present 
As per the current specs "element_count" should be used 
Example nasa.neo.rest.elementcount.path=element_count

#### 3. nasa.neo.rest.elementcount.displaymsg - This property is used for displaying message for the number of objects
Example nasa.neo.rest.elementcount.displaymsg=The total number of elements are {0}

#### 4. nasa.neo.rest.parent.node - This property is used to determine all the unique objects returned from the response
As per the current specs "neo_reference_id" should be used
Example nasa.neo.rest.parent.node=neo_reference_id

#### 5. nasa.neo.rest.child.operation.count - This property defines how many comparison operations needs to be performed e.g. 1,2,3 etc
Example nasa.neo.rest.child.operation.count=2

#### 6. nasa.neo.rest.child.[]. (name,path,operation,displaymsg)
[] - This is numeric value starting from 1 and goes till count defined in nasa.neo.rest.child.operation.count property

This has following tags:     
nasa.neo.rest.child.[].name -> Name of the Child Operation like LARGEST_OBJECT

nasa.neo.rest.child.[].path -> Relative path from "parent node" (Step#4) till leaf node like close_approach_data/miss_distance/kilometers

nasa.neo.rest.child.[].opertion -> Current version supports two operations, 1. smallest 2. largest Only. This can be extended in future

nasa.neo.rest.child.[].displaymsg -> Message to be displayed

Example below in the sample file

#### 7. Following properties are used to define proxy settings, in case if you behind wirewall.
nasa.neo.network.useproxy - Set this property to true if proxy settings are to be used. (Possible values true/false)

Example  nasa.neo.network.useproxy=true

nasa.neo.network.https.proxyhost - Set your proxy host in this property

Example  nasa.neo.network.https.proxyhost=my.company.proxy

nasa.neo.network.https.proxyport - Set the proxy port in this property 

Example nasa.neo.network.https.proxyport=8000

nasa.neo.network.https.proxyuser - Set the proxy user-name in this property

Example nasa.neo.network.https.proxyuser=ggarg

nasa.neo.network.https.password - Set the proxy password in this property

Example nasa.neo.network.https.password=ggarg

```
Sample property file for reference 

nasa.neo.rest.api.url=https://api.nasa.gov/neo/rest/v1/feed?start_date=2018-08-29&end_date=2018-08-29&api_key=DEMO_KEY
nasa.neo.rest.elementcount.path=element_count
nasa.neo.rest.elementcount.displaymsg=The total number of elements are {0}
nasa.neo.rest.parent.node=neo_reference_id
nasa.neo.rest.child.operation.count=3

nasa.neo.rest.child.1.name=CLOSEST_OBJECT_TO_EARTH
nasa.neo.rest.child.1.path=close_approach_data/miss_distance/kilometers
nasa.neo.rest.child.1.operation=smallest
nasa.neo.rest.child.1.displaymsg=Displaying closest object to earth, details as below

nasa.neo.rest.child.2.name=LARGEST_OBJECT
nasa.neo.rest.child.2.path=estimated_diameter/kilometers/estimated_diameter_max
nasa.neo.rest.child.2.operation=largest
nasa.neo.rest.child.2.displaymsg=Displaying largest object, details as below

nasa.neo.rest.child.3.name=ABS_MAG_H
nasa.neo.rest.child.3.path=absolute_magnitude_h
nasa.neo.rest.child.3.operation=largest
nasa.neo.rest.child.3.displaymsg=Displaying largest magnitude details 

nasa.neo.network.useproxy=true
nasa.neo.network.https.proxyhost=my.company.proxy
nasa.neo.network.https.proxyport=8000
nasa.neo.network.https.proxyuser=ggarg
nasa.neo.network.https.password=ggarg

```

## Sample Output file 
```
0 [main] DEBUG nasa.neo.rest.client.LoadConfigurationData  -  loadConfData:: entry
43 [main] INFO org.springframework.beans.factory.config.PropertiesFactoryBean  - Loading properties file from file [C:\Users\ggarg\Downloads\neo-rest-api-master (1)\neo-rest-api-master\target\..\config.properties]
60 [main] DEBUG nasa.neo.rest.client.LoadConfigurationData  -  loadConfData:: exit ::fileLoadSuccessfullytrue {nasa.neo.rest.child.1.name=CLOSEST_OBJECT_TO_EARTH, nasa.neo.rest.parent.node=neo_reference_id, nasa.neo.rest.elementcount.displaymsg=The total number of elements are {0}, nasa.neo.rest.child.2.operation=largest, nasa.neo.rest.api.url=https://api.nasa.gov/neo/rest/v1/feed?start_date=2018-08-29&end_date=2018-08-29&api_key=DEMO_KEY, nasa.neo.rest.child.2.path=estimated_diameter/kilometers/estimated_diameter_max, nasa.neo.rest.child.2.name=LARGEST_OBJECT, nasa.neo.rest.child.1.operation=smallest, nasa.neo.rest.child.3.displaymsg=Displaying largest magnitude details , nasa.neo.rest.elementcount.path=element_count, nasa.neo.rest.child.3.path=absolute_magnitude_h, nasa.neo.rest.child.operation.count=3, nasa.neo.rest.child.2.displaymsg=Displaying largest object, details as below, nasa.neo.rest.child.3.name=ABS_MAG_H, nasa.neo.rest.child.1.displaymsg=Displaying closest object to earth, details as below, nasa.neo.rest.child.3.operation=largest, nasa.neo.rest.child.1.path=close_approach_data/miss_distance/kilometers}
5852 [main] INFO nasa.neo.rest.client.UtilFunction  - ==================================================================================================================================================
5862 [main] INFO nasa.neo.rest.client.UtilFunction  - ===============================================DISPLAYING RESULTS=================================================================================
5876 [main] INFO nasa.neo.rest.client.UtilFunction  - ==================================================================================================================================================
5901 [main] INFO nasa.neo.rest.client.UtilFunction  - The total number of elements are 8
5904 [main] INFO nasa.neo.rest.client.UtilFunction  - Child Name=CLOSEST_OBJECT_TO_EARTH :: neo_reference_id="3014184" ::  Operation=smallest :: Path=close_approach_data/miss_distance/kilometers :: Value=1620736.0

5931 [main] INFO nasa.neo.rest.client.UtilFunction  - PrintJsonObject={
  "links": {
    "self": "https://api.nasa.gov/neo/rest/v1/neo/3014184?api_key=DEMO_KEY"
  },
  "neo_reference_id": "3014184",
  "name": "(1998 SD9)",
  "nasa_jpl_url": "http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=3014184",
  "absolute_magnitude_h": 24.2,
  "estimated_diameter": {
    "kilometers": {
      "estimated_diameter_min": 0.0384197891,
      "estimated_diameter_max": 0.0859092601
    },
    "meters": {
      "estimated_diameter_min": 38.4197891064,
      "estimated_diameter_max": 85.9092601232
    },
    "miles": {
      "estimated_diameter_min": 0.0238729428,
      "estimated_diameter_max": 0.0533815229
    },
    "feet": {
      "estimated_diameter_min": 126.0491808919,
      "estimated_diameter_max": 281.8545369825
    }
  },
  "is_potentially_hazardous_asteroid": false,
  "close_approach_data": [
    {
      "close_approach_date": "2018-08-29",
      "epoch_date_close_approach": 1535526000000,
      "relative_velocity": {
        "kilometers_per_second": "10.6995899636",
        "kilometers_per_hour": "38518.5238688059",
        "miles_per_hour": "23933.9127894811"
      },
      "miss_distance": {
        "astronomical": "0.0108339507",
        "lunar": "4.2144069672",
        "kilometers": "1620736",
        "miles": "1007078.625"
      },
      "orbiting_body": "Earth"
    }
  ]
}

6066 [main] INFO nasa.neo.rest.client.UtilFunction  - Child Name=LARGEST_OBJECT :: neo_reference_id="2436771" ::  Operation=largest :: Path=estimated_diameter/kilometers/estimated_diameter_max :: Value=0.9419763057

6123 [main] INFO nasa.neo.rest.client.UtilFunction  - PrintJsonObject={
  "links": {
    "self": "https://api.nasa.gov/neo/rest/v1/neo/2436771?api_key=DEMO_KEY"
  },
  "neo_reference_id": "2436771",
  "name": "436771 (2012 JG11)",
  "nasa_jpl_url": "http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=2436771",
  "absolute_magnitude_h": 19.0,
  "estimated_diameter": {
    "kilometers": {
      "estimated_diameter_min": 0.4212646106,
      "estimated_diameter_max": 0.9419763057
    },
    "meters": {
      "estimated_diameter_min": 421.2646105562,
      "estimated_diameter_max": 941.9763057186
    },
    "miles": {
      "estimated_diameter_min": 0.2617616123,
      "estimated_diameter_max": 0.5853167591
    },
    "feet": {
      "estimated_diameter_min": 1382.1017848971,
      "estimated_diameter_max": 3090.4735428537
    }
  },
  "is_potentially_hazardous_asteroid": false,
  "close_approach_data": [
    {
      "close_approach_date": "2018-08-29",
      "epoch_date_close_approach": 1535526000000,
      "relative_velocity": {
        "kilometers_per_second": "17.7958480162",
        "kilometers_per_hour": "64065.052858392",
        "miles_per_hour": "39807.5324274825"
      },
      "miss_distance": {
        "astronomical": "0.1124241655",
        "lunar": "43.733001709",
        "kilometers": "16818416",
        "miles": "10450479"
      },
      "orbiting_body": "Earth"
    }
  ]
}

6257 [main] INFO nasa.neo.rest.client.UtilFunction  - Child Name=ABS_MAG_H :: neo_reference_id="3825001" ::  Operation=largest :: Path=absolute_magnitude_h :: Value=25.117

6314 [main] INFO nasa.neo.rest.client.UtilFunction  - PrintJsonObject={
  "links": {
    "self": "https://api.nasa.gov/neo/rest/v1/neo/3825001?api_key=DEMO_KEY"
  },
  "neo_reference_id": "3825001",
  "name": "(2018 KP1)",
  "nasa_jpl_url": "http://ssd.jpl.nasa.gov/sbdb.cgi?sstr=3825001",
  "absolute_magnitude_h": 25.117,
  "estimated_diameter": {
    "kilometers": {
      "estimated_diameter_min": 0.0251857551,
      "estimated_diameter_max": 0.0563170605
    },
    "meters": {
      "estimated_diameter_min": 25.1857551309,
      "estimated_diameter_max": 56.3170605375
    },
    "miles": {
      "estimated_diameter_min": 0.0156496979,
      "estimated_diameter_max": 0.0349937882
    },
    "feet": {
      "estimated_diameter_min": 82.6304328638,
      "estimated_diameter_max": 184.7672648937
    }
  },
  "is_potentially_hazardous_asteroid": false,
  "close_approach_data": [
    {
      "close_approach_date": "2018-08-29",
      "epoch_date_close_approach": 1535526000000,
      "relative_velocity": {
        "kilometers_per_second": "3.3684412623",
        "kilometers_per_hour": "12126.3885444169",
        "miles_per_hour": "7534.866259724"
      },
      "miss_distance": {
        "astronomical": "0.1039373125",
        "lunar": "40.4316139221",
        "kilometers": "15548801",
        "miles": "9661577"
      },
      "orbiting_body": "Earth"
    }
  ]
}
```

### Major Class Used and their description
#### 1. Runner -> This class is used to run the main program and call other required calls
#### 2. UtilFunction -> This class contains all the utility functions to support other classes
#### 3. IConstants -> This interface is used to define all the constants and error messages
#### 4. LoadConfigurationData -> This class is used to load and validate configuration data based on the config file
#### 5. ExecuteGetRestAPI & GenericExecuteRestAPI -> These classes are used to call Rest API URLs and return JSON object
#### 6. OperObj -> This class stores information about differnt objects present in the property file and then also stores the data after processing 



## Authors

* **Gauav Garg** - *Initial work* 

