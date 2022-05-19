package com.example.logfile.parser.deserializer;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.example.logfile.parser.bean.ApplicationServerEventData;
import com.example.logfile.parser.bean.EventData;
import com.example.logfile.parser.bean.ServerLogData;
import com.fasterxml.jackson.databind.ObjectMapper;

class LogFileDeserializerTest {

    private final String SERVER_LOG_ENTRY_RECORD = "{\"id\":\"scsmbstgra\", \"state\":\"STARTED\", \"type\":\"APPLICATION_LOG\", \"host\":\"12345\", \"timestamp\":1491377495212}";
    private final String LOG_ENTRY_RECORD = "{\"id\":\"scsmbstgrb\", \"state\":\"STARTED\", \"timestamp\":1491377495213}";

    @Test
    void testDesrializerForServerLogEntryBaseClass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        EventData instance = mapper.readValue(SERVER_LOG_ENTRY_RECORD, EventData.class);

        assertEquals(ApplicationServerEventData.class, instance.getClass());
    }


    @Test
    void testDesrializerForLogEntryClass() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        EventData instance = mapper.readValue(LOG_ENTRY_RECORD, EventData.class);

        assertEquals(ServerLogData.class, instance.getClass());
    }
}
