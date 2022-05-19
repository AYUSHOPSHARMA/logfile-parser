package com.example.logfile.parser;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.example.logfile.parser.entity.EventDetail;
import com.example.logfile.parser.repository.EventDetailRepository;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class LogParserApplicationIntegrationTest {
    @Autowired
    private EventDetailRepository eventDetailRepository;

    @Test
    void testSuccessRunValidateInMemoryDatabase() throws Exception {

        List<EventDetail> eventDetailList = (List<EventDetail>) eventDetailRepository.findAll();

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
        
        Assertions.assertThat(eventDetailList).contains(detailA, detailB, detailC);

    }

}
