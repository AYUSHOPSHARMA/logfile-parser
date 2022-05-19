package com.example.logfile.parser.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.logfile.parser.bean.EventData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class FileReaderService {
	
	private static final Logger logger = LogManager.getLogger(FileReaderService.class);
	
    private ObjectMapper mapper;

    public FileReaderService(ObjectMapper mapper) {
        this.mapper = Objects.requireNonNull(mapper, "mapper is required and cannot be null");
    }

	public List<EventData> getEventObjectsFromFile(String logFilePath) throws IOException {
		List<EventData> eventList = new ArrayList<>();
		BufferedInputStream bufferedInputStream = null;
		Scanner sc =null;;

		try {
			InputStream inputStream = new FileInputStream(logFilePath);
			 bufferedInputStream = new BufferedInputStream(inputStream);
			 sc = new Scanner(bufferedInputStream, "UTF-8");
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				eventList.add(parseLineToObjectAndAddToList(line));
			} // note that Scanner suppresses exceptions
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(
					String.format("Exception when trying to read file"));
		} catch (IOException e) {
			throw new IOException(
					String.format("Exception when trying to read file"),e);
		} finally {
			if (bufferedInputStream != null) {
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (sc != null) {
				sc.close();
			}
		}
		return eventList;
	}

	EventData parseLineToObjectAndAddToList(String line) throws IOException {
		try {
			EventData event = null;
			event = mapper.readValue(line, EventData.class);
			logger.debug("line {} converted to object {}", line, event);
			return event;
		} catch (JsonProcessingException e) {
			throw new IOException(
					String.format("Exception when trying to read file"));
		}
		
	}


}
