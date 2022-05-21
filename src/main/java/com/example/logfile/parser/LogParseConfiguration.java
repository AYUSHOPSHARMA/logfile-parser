/**
 * 
 */
package com.example.logfile.parser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ayush.sharma
 *
 * Spring Configuration annotation indicates that the class has @Bean definition methods. 
 * So Spring container can process the class and generate Spring Beans to be used in the application.
 */
@Configuration
public class LogParseConfiguration {

	/**
	 * he Jackson ObjectMapper class is the simplest way to parse JSON with Jackson. 
	 * The Jackson ObjectMapper can parse JSON from a string, stream or file, and create a Java object or 
	 * object graph representing the parsed JSON. 
	 * 
	 * Parsing JSON into Java objects is also referred to as to deserialize Java objects from JSON.
	 * @return
	 */
	@Bean 
	public ObjectMapper createObjectMapper() {
    	return new ObjectMapper(); 
    }
}
