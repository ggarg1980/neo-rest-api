# NEO-REST-API

“Near Earth Objects” using the NASA RESTful Web Service https://api.nasa.gov/api.html#NeoWS . 
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
#### 1. nasa.neo.rest.api.url - This property is used to respresent URL to called
Valid URLs
https://api.nasa.gov/neo/rest/v1/feed/today?detailed=true&api_key=DEMO_KEY
https://api.nasa.gov/neo/rest/v1/feed?start_date=2018-08-29&end_date=2018-08-29&api_key=DEMO_KEY
start_date & end_date are dates in format yyyy-MM-dd e.g. 2018-08-29
*Note: Please use DEMO_KEY or your own key retrieved in step.3 of getting started 

Example 
nasa.neo.rest.api.url=https://api.nasa.gov/neo/rest/v1/feed?start_date=2018-08-29&end_date=2018-08-29&api_key=DEMO_KEY

#### 2. nasa.neo.rest.elementcount.path - This element identifies the path where total count is present 
As per the current specs it present under "element_count" path 
Example nasa.neo.rest.elementcount.path=element_count

#### 3. nasa.neo.rest.elementcount.displaymsg - This property is used for displaying message for the number of objects
Example nasa.neo.rest.elementcount.displaymsg=The total number of elements are {0}

#### 4. nasa.neo.rest.parent.node - This is very important prope

nasa.neo.rest.child.operation.count=4

nasa.neo.rest.child.1.name=child1
nasa.neo.rest.child.1.path=/close_approach_data/miss_distance/kilometers
nasa.neo.rest.child.1.operation=largest
nasa.neo.rest.child.1.displaymsg=Displaying smallest miss distance -

nasa.neo.rest.child.2.name=child2
nasa.neo.rest.child.2.path=estimated_diameter/kilometers/estimated_diameter_max
nasa.neo.rest.child.2.operation=largest
nasa.neo.rest.child.2.displaymsg=Displaying smallest miss distance -


nasa.neo.rest.child.3.name=child3
nasa.neo.rest.child.3.path=absolute_magnitude_h
nasa.neo.rest.child.3.operation=largest
nasa.neo.rest.child.3.displaymsg=Displaying smallest miss distance -


nasa.neo.rest.child.4.name=child4
nasa.neo.rest.child.4.path=neo_reference_id
nasa.neo.rest.child.4.operation=smallest
nasa.neo.rest.child.4.displaymsg=Displaying smallest miss distance -





step by step series of examples that tell you how to get a development env running
dfdsf
https://github.com/ggarg1980/neo-rest-api/
Say what the step will be

```
Give the example
```

And repeat

```
until finished
```

End with an example of getting some data out of the system or using it for a little demo

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
