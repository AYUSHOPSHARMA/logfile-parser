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


@SpringBootApplication
public class LogFileParserApplication  implements CommandLineRunner {
	
	private static final Logger logger = LogManager.getLogger(LogFileParserApplication.class);

	@Autowired
	static ConfigurableApplicationContext appContext;
	@Autowired
    private LogFileParserService logFileParserService;
	@Autowired
    private EventDetailRepository eventDetailRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(LogFileParserApplication.class, args);
	}
	
	public static ConfigurableApplicationContext getAppContext() {
		return appContext;
	}

	@Override
	public void run(String... args) throws IOException {
		  List<EventDetail> eventDetailList = logFileParserService.parseLogFileForEventDetails(args);
		  logger.info("Event detail objects being persisted {}", Arrays.toString(eventDetailList.toArray()));
		  eventDetailRepository.saveAll(eventDetailList);
	}
}
