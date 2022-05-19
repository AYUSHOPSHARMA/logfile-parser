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
 */
@Configuration
public class LogParseConfiguration {

	@Bean 
	public ObjectMapper createObjectMapper() {
    	return new ObjectMapper(); 
    }
}
