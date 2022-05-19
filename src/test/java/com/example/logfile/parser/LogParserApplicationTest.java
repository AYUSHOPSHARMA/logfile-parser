package com.example.logfile.parser;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.logfile.parser.entity.EventDetail;
import com.example.logfile.parser.repository.EventDetailRepository;
import com.example.logfile.parser.service.LogFileParserService;

@ExtendWith(MockitoExtension.class)
class LogParserApplicationTest {

    @Mock
    private LogFileParserService logFileParserService;
    @Mock
    private EventDetailRepository eventDetailRepository;
    @Mock
    private ConfigurableApplicationContext context;
    
    @Captor
    private ArgumentCaptor<List<EventDetail>> eventDetailListCaptor;

    @InjectMocks
    LogFileParserApplication logParserApplication;

    @Test
    void testRun() {

    	 EventDetail detailA = EventDetail.builder().id("scsmbstgrmg")
                 .type("APPLICATION_LOG")
                 .host("12345")
                 .eventDuration(5l)
                 .alert(true).build();

         EventDetail detailB = EventDetail.builder().id("scsmbstgrb")
                 .eventDuration(3l)
                 .alert(false).build();

         EventDetail detailC = EventDetail.builder().id("scsmbstgrc")
                 .eventDuration(8l)
                 .alert(true).build();

        List<EventDetail> eventDetailList = Arrays.asList(detailA,detailB,detailC);
        try {
			Mockito.when(logFileParserService.parseLogFileForEventDetails()).thenReturn(eventDetailList);
			 logParserApplication.run();
			 Mockito.verify(eventDetailRepository).saveAll(eventDetailListCaptor.capture());
		     Assertions.assertEquals(eventDetailList, eventDetailListCaptor.getValue());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
       
       
    }


}
