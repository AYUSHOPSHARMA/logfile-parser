package com.example.logfile.parser.service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.logfile.parser.bean.ApplicationServerEventData;
import com.example.logfile.parser.bean.EventData;
import com.example.logfile.parser.bean.ServerLogData;
import com.example.logfile.parser.bean.State;
import com.example.logfile.parser.entity.EventDetail;

@ExtendWith(MockitoExtension.class)
class LogFileParserServiceTest {

	private final String useDir = System.getProperty("user.dir");
	
    private final String logFilePath = useDir+"/src/test/resources/logfile.txt";
    

    
    @Mock
    FileReaderService fileReaderService;
    LogFileParserService logFileParserService;


    @BeforeEach
    public void setup() {
        logFileParserService = new LogFileParserService(logFilePath, fileReaderService);
    }

    @Test
    void testLogParserServiceSuccessScenario() throws IOException {
        List<EventData> baseLogEvents = new ArrayList<>();

        ServerLogData serverLog = new ServerLogData();
        serverLog.setId("id1");
        serverLog.setTimestamp(1234);
        serverLog.setState(State.STARTED);

        baseLogEvents.add(serverLog);

        serverLog = new ServerLogData();
        serverLog.setId("id1");
        serverLog.setTimestamp(1237);
        serverLog.setState(State.FINISHED);

        baseLogEvents.add(serverLog);

        ApplicationServerEventData applicationServerEventData = new ApplicationServerEventData();
        applicationServerEventData.setId("id2");
        applicationServerEventData.setTimestamp(1239);
        applicationServerEventData.setState(State.FINISHED);
        applicationServerEventData.setHost("host");
        applicationServerEventData.setType("APPLICATION_LOG");

        baseLogEvents.add(applicationServerEventData);

        applicationServerEventData = new ApplicationServerEventData();
        applicationServerEventData.setId("id2");
        applicationServerEventData.setTimestamp(1230);
        applicationServerEventData.setState(State.STARTED);
        applicationServerEventData.setHost("host");
        applicationServerEventData.setType("APPLICATION_LOG");
        baseLogEvents.add(applicationServerEventData);
        fileReaderService.getEventObjectsFromFile(logFilePath);
        Mockito.when(fileReaderService.getEventObjectsFromFile(logFilePath)).thenReturn(baseLogEvents);


        EventDetail detailA = EventDetail.builder().id("id1")
                .eventDuration(3l)
                .alert(false).build();


        EventDetail detailB = EventDetail.builder().id("id2")
                .type("APPLICATION_LOG")
                .host("host")
                .eventDuration(9l)
                .alert(true).build();

        List<EventDetail> eventDetailList = logFileParserService.parseLogFileForEventDetails(logFilePath);

        Assertions.assertThat(eventDetailList).contains(detailA, detailB);
    }

    @Test
    void testLogParserServiceFileNotFound() throws IOException {
        Mockito.when(fileReaderService.getEventObjectsFromFile(logFilePath)).thenThrow(new IOException("Exception when trying to read from file at path"));
        org.junit.jupiter.api.Assertions.assertThrows(IOException.class, () -> {
            logFileParserService.parseLogFileForEventDetails();
        });


    }
    @Test
    void testLogParserServiceFileNotFoundException() throws IOException {
          org.junit.jupiter.api.Assertions.assertThrows(IOException.class, () -> {
            logFileParserService.parseLogFileForEventDetails("dummy.txt");
        });


    }
}
