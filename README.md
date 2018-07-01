# NEO-REST-API

“Near Earth Objects” using the NASA RESTful Web Service [NEO-REST-API](https://api.nasa.gov/api.html#NeoWS). 
Identify which NEO is the largest in size and which is the closest to Earth.  
Output the total number of NEOs, and the details retrieved for both the largest and closest NEOs identified.

## Getting Started
Please install following softwares on your system
1. Install JAVA (v1.8)
2. Install Maven (largest)
3. Register on NASA Open APIs using https://api.nasa.gov/index.html#apply-for-an-api-key and get API-Key for calling APIs.

## Download
1. Please download code from  https://github.com/ggarg1980/neo-rest-api/ URL. Click on "Clone or Download" button and down ZIP
2. Unzip the ZIP file
3. Inside the ZIP file where src folder is present. Open command prompt and run "mvn clean package" command 
4. Check BUILD is successful and neorestapi-0.0.1-SNAPSHOT.jar is created inside "target" folder
5. Go inside target folder and run the program using following command
   java -cp neorestapi-0.0.1-SNAPSHOT.jar nasa.neo.rest.client.Runner ../config.properties
   *Note: Please give proper path where config.properties file is present. Please configure properties before running the program. Refer Configuring properties section below

### Configuring properties file config.properties 
*NOTE: Before configuring the data please refer to the API response objects for better understanding

#### 1. nasa.neo.rest.api.url - This property is used to respresent URL to called
Valid URLs
https://api.nasa.gov/neo/rest/v1/feed/today?detailed=true&api_key=DEMO_KEY
https://api.nasa.gov/neo/rest/v1/feed?start_date=2018-08-29&end_date=2018-08-29&api_key=DEMO_KEY
start_date & end_date are dates in format yyyy-MM-dd e.g. 2018-08-29
*Note: Please use DEMO_KEY or your own key retrieved in step.3 of getting started 

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

#### 5. nasa.neo.rest.child.operation.count - This property defines how many comparison opertions needs to be performed e.g. 1,2,3 etc
Example nasa.neo.rest.child.operation.count=2

#### 5. nasa.neo.rest.child.{}
 {} - This is numeric value starting from 1 and goes till count defined in nasa.neo.rest.child.operation.count property
 This has following tags     
nasa.neo.rest.child.{}.name -> Name of the Child Operation like LARGEST_OBJECT
nasa.neo.rest.child.{}.path -> Relative path from "parent node" (Step#4) till leaf node like close_approach_data/miss_distance/kilometers
nasa.neo.rest.child.{}.opertion -> two possible values are defined smallest/largest
nasa.neo.rest.child.{}.displaymsg -> Message to be displayed
Example below in the sample file

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

```

## Running the tests

Explain how to run the automated tests for this system

### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ROME](https://rometools.github.io/rome/) - Used to generate RSS Feeds

## Contributing

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us.

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Billie Thompson** - *Initial work* - [PurpleBooth](https://github.com/PurpleBooth)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
