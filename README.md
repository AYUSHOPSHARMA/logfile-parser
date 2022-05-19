# logfile-parser
## Problem Statement
Our custom-build server logs different events to a file named logfile.txt. Every event has 2 entries in the file one entry when the event was started and another when the event was finished. The entries in the file have no specific order (a finish event could occur before a start event for a given id)
Every line in the file is a JSON object containing the following event data:
  - id - the unique event identifier
  - state - whether the event was started or finished (can have values "STARTED" or "FINISHED"
  - timestamp - the timestamp of the event in milliseconds

Application Server logs also have the following additional attributes:
  - type - type of log
  - host - hostname

The program should:
  - Take the path to logfile.txt as an input argument
  - Parse the contents of logfile.txt
  - Flag any long events that take longer than 4ms
  - Write the found event details to file-based HSQLDB (http://hsqldb.org/) in the working folder
  - The application should create a new table if necessary and store the following values: 
  - Event id
  - Event duration
  - Type and Host if applicable
  - Alert (true if the event took longer than 4ms, otherwise false)

## Solution
### Steps to follow 

- Download the repository or clone the GIT repo
- run command **mvn clean install**
    - It will clean and install the project
-  by default if we donot pass the path of log file it will take the logfile placed inside resource folder, If we pass the program argument as path it will consider that file path.
-  run command **spring-boot:run -Dspring-boot.run.arguments=<PATH_OF_YOUR_LOG_FILE>**  it will run the spring boot applicaton and parse your log file based on the logic defined

### TEST Application
- run command **mvn test**
### Code Coverage Reports Using Maven and JaCoCo Plugin
#### Pre-requisites:
- Maven 3.0 or higher
- Java 1.8 or higher

By running the JUnit tests using **mvn test** will automatically set the JaCoCo agent in motion. It will create a report in binary format in the target directory, with path **target/jacoco.exec**.

After running Command **mvn test**, 
- Please run the command **mvn jacoco:report**.

Once your run above command it will create a jacoco report under the path logfile-parser-main **\target\site\jacoco\index.html **

#### Code Coverage Report

<img width="955" alt="image" src="https://user-images.githubusercontent.com/27842538/169293090-b9af3941-6b97-46b7-8f3e-049e386c2817.png">




- 

