package com.example.logfile.parser;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.logfile.parser.entity.EventDetail;
import com.example.logfile.parser.repository.EventDetailRepository;
import com.example.logfile.parser.service.LogFileParserService;

/**
 * Main class with of spring boot application contains main method..
 *
 * @author ayush.sharma
 * 
 * @SpringBootApplication is a convenience annotation that adds all of the following:
 * 	@Configuration: Tags the class as a source of bean definitions for the application context.
 * 	@EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet.
 * 	@ComponentScan: Tells Spring to look for other components, configurations, and services in the com/example package, letting it find the controllers.
 * 
 */

@SpringBootApplication
public class LogFileParserApplication  implements CommandLineRunner {
	
	private static final Logger logger = LogManager.getLogger(LogFileParserApplication.class);

	@Autowired
	static ConfigurableApplicationContext appContext;
	@Autowired
    private LogFileParserService logFileParserService;
	@Autowired
    private EventDetailRepository eventDetailRepository;
	
	/**
	 * The main() method uses Spring Boot’s SpringApplication.run() method to launch Application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(LogFileParserApplication.class, args);
	}
	
	public static ConfigurableApplicationContext getAppContext() {
		return appContext;
	}

	/**
	 * CommandLineRunner is a simple Spring Boot interface with a run method. 
	 * 
	 * Spring Boot will automatically call the run method of all beans 
	 * implementing this interface after the application context has been loaded.
	 */
	@Override
	public void run(String... args) throws IOException {
		  List<EventDetail> eventDetailList = logFileParserService.parseLogFileForEventDetails(args);
		  logger.info("Event detail objects being persisted {}", Arrays.toString(eventDetailList.toArray()));
		  eventDetailRepository.saveAll(eventDetailList);
	}
}
