package com.example.logfile.parser.service;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.logfile.parser.bean.ApplicationServerEventData;
import com.example.logfile.parser.bean.EventData;
import com.example.logfile.parser.bean.ServerLogData;
import com.example.logfile.parser.bean.State;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class FileReaderServiceTest {
	
	private final String useDir = System.getProperty("user.dir");
	
    private final String logFilePath = useDir+"src/test/resources/logfile.txt";

    private final List<String> TEST_DATA_VALID = Arrays.asList("{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495212}\n" +
            "{\"id\":\"scsmbstgrb\", \"state\":\"STARTED\", \"timestamp\":1491377495213}\n" +
            "{\"id\":\"scsmbstgrc\", \"state\":\"FINISHED\", \"timestamp\":1491377495218}\n" +
            "{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217}\n" +
            "{\"id\":\"scsmbstgrc\", \"state\":\"STARTED\", \"timestamp\":1491377495210}\n" +
            "{\"id\":\"scsmbstgrb\", \"state\":\"FINISHED\", \"timestamp\":1491377495216}");


    private final List<String> TEST_DATA_INVALID = Arrays.asList("{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"time\":1491377495212}\n" +
            "{\"id\":\"scsmbstgrb\", \"state\":\"STARTED\", \"timestamp\":1491377495213}\n" +
            "{\"id\":\"scsmbstgrc\", \"state\":\"FINISHED\", \"timestamp\":1491377495218}\n" +
            "{\"id\":\"scsmbstgra\", \"state\":\"FINISHED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495217}\n" +
            "{\"id\":\"scsmbstgrc\", \"state\":\"STARTED\", \"timestamp\":1491377495210}\n" +
            "{\"id\":\"scsmbstgrb\", \"state\":\"FINISHED\", \"timestamp\":1491377495216");

    @Spy
    ObjectMapper mapper;
    @Mock
    Path mockedFile;
    @InjectMocks
    FileReaderService fileReaderService;
    @TempDir
    File testLogFileDir;


    @Test
    void testGetEventObjectsFromFileSuccess() throws IOException {
        File tempLogFile = new File(testLogFileDir, "logfile.txt");

        Files.write(tempLogFile.toPath(), TEST_DATA_VALID);

        List<EventData> baseLogEvents = new ArrayList<>();

        ApplicationServerEventData ApplicationServerEventData = new ApplicationServerEventData();
        ApplicationServerEventData.setId("scsmbstgra");
        ApplicationServerEventData.setType("APPLICATION_LOG");
        ApplicationServerEventData.setTimestamp(1491377495212l);
        ApplicationServerEventData.setState(State.STARTED);
        ApplicationServerEventData.setHost("12345");

        baseLogEvents.add(ApplicationServerEventData);

        ApplicationServerEventData = new ApplicationServerEventData();
        ApplicationServerEventData.setId("scsmbstgra");
        ApplicationServerEventData.setType("APPLICATION_LOG");
        ApplicationServerEventData.setTimestamp(1491377495217L);
        ApplicationServerEventData.setState(State.FINISHED);
        ApplicationServerEventData.setHost("12345");

        baseLogEvents.add(ApplicationServerEventData);

        ServerLogData serverLog = new ServerLogData();
        serverLog.setId("scsmbstgrb");
        serverLog.setTimestamp(1491377495213l);
        serverLog.setState(State.STARTED);

        baseLogEvents.add(serverLog);

        serverLog = new ServerLogData();
        serverLog.setId("scsmbstgrb");
        serverLog.setTimestamp(1491377495216L);
        serverLog.setState(State.FINISHED);

        baseLogEvents.add(serverLog);

        serverLog = new ServerLogData();
        serverLog.setId("scsmbstgrc");
        serverLog.setTimestamp(1491377495218L);
        serverLog.setState(State.FINISHED);

        baseLogEvents.add(serverLog);

        serverLog = new ServerLogData();
        serverLog.setId("scsmbstgrc");
        serverLog.setTimestamp(1491377495210L);
        serverLog.setState(State.STARTED);

        baseLogEvents.add(serverLog);
        List<EventData> baseLogEventsActual = fileReaderService.getEventObjectsFromFile(tempLogFile.getPath());
        Assertions.assertThat(baseLogEventsActual.get(0).getId()).isEqualTo(baseLogEvents.get(0).getId());


    }

    
    @Test
	void testGetEventObjectsFromFileWithInvalidJsonData() throws IOException {
		File tempLogFile = new File(testLogFileDir, "log-ivalid.txt");
		Files.write(tempLogFile.toPath(), TEST_DATA_INVALID);
		IOException thrown = org.junit.jupiter.api.Assertions.assertThrows(IOException.class, () -> {
			fileReaderService.getEventObjectsFromFile(tempLogFile.getPath());
		});
		org.junit.jupiter.api.Assertions
				.assertTrue(thrown.getMessage().contains("Exception when trying to read file"));
	}
    
    @Test
    void testgetEventObjectsFileNotFoundException() throws IOException {
          org.junit.jupiter.api.Assertions.assertThrows(IOException.class, () -> {
        	  fileReaderService.getEventObjectsFromFile("dummy.txt");
        });


    }
}
