/**
 * 
 */
package com.example.logfile.parser.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.logfile.parser.bean.ApplicationServerEventData;
import com.example.logfile.parser.bean.EventData;
import com.example.logfile.parser.bean.State;
import com.example.logfile.parser.entity.EventDetail;
import com.example.logfile.parser.repository.EventDetailRepository;


/**
 * @author ayush.sharma
 *
 */

@Service
public class LogFileParserService {

	private static final Logger logger = LogManager.getLogger(LogFileParserService.class);

	@Value("${app.alert.threshold.milliseconds}")
	private Long alertThreshold= 4L;

	private FileReaderService fileReaderService;

	@Autowired
	private EventDetailRepository eventDetailRepository;

	public LogFileParserService(@Value("${logfile.path}") String logFilePath, FileReaderService fileReaderService) {
		super();
		this.logFilePath = logFilePath;
		this.fileReaderService = Objects.requireNonNull(fileReaderService, "object cannot be null");
	}
	
	@Value("${logfile.path}")
	private String logFilePath;


	 public List<EventDetail> parseLogFileForEventDetails(String... args) throws IOException {
		 if (args != null && args.length > 0 && !args[0].trim().isEmpty()) {
				logFilePath = args[0];
			}
		 File file = new File(logFilePath);
			if(!file.exists()) {
				logger.error("File Exist ? "+file.exists()+" On Path "+logFilePath);
				throw new IOException(String.format("Exception when trying to read from file at path %s", file.getAbsolutePath()));
			}
		 logger.info("File Exist ? "+file.exists()+" On Path "+logFilePath);
		 return parseLogFileEvents();
	 }
	public List<EventDetail> parseLogFileEvents() throws IOException {
		return copyUsingChunks();
	}

	private List<EventDetail> copyUsingChunks() throws IOException {
		List<EventDetail> eventDetails = new ArrayList<>();
		List<EventData> eventList = new ArrayList<>();
		logger.info("logFilePath" + logFilePath);
		eventList = fileReaderService.getEventObjectsFromFile(logFilePath);
		logger.info("Event data objects being persisted {}", Arrays.toString(eventList.toArray()));
		eventDetails = buildEventDetailsFromLogEntries(eventList);
		return eventDetails;
	}


	private List<EventDetail> buildEventDetailsFromLogEntries(List<EventData> eventDatas) {
		Map<String, EventDetail> eventDetailsMap = new HashMap<>();
		for (EventData entry : eventDatas) {
			processLogEntryForEventDetail(eventDetailsMap, entry);
		}
		return new ArrayList<>(eventDetailsMap.values());
	}

	private void processLogEntryForEventDetail(Map<String, EventDetail> eventDetailsMap, EventData entry) {
        if (eventDetailsMap.containsKey(entry.getId())) {
            EventDetail eventDetail;
            if (entry.getState().equals(State.FINISHED)) {
                eventDetail = updateEventDetailWithFinishedEvent(eventDetailsMap, entry);
            } else {
                eventDetail = updateEventDetailWithStartEvent(eventDetailsMap, entry);
            }
            if (eventDetail.getEventDuration() > alertThreshold) {
                eventDetail.setAlert(true);
                logger.warn("Alert threshold of {}ms exceeded for event detail with id {}", alertThreshold, eventDetail.getId());
            }
        } else {
            EventDetail eventDetail = prepareEventDetailForEntry(entry);
            eventDetailsMap.put(entry.getId(), eventDetail);
        }
    }

	private EventDetail prepareEventDetailForEntry(EventData entry) {
		EventDetail.EventDetailBuilder eventDetailBuilder = EventDetail.builder().id(entry.getId())
				.eventDuration(entry.getTimestamp()).alert(false);
		if (entry instanceof ApplicationServerEventData) {
			ApplicationServerEventData applicationServerEventData = (ApplicationServerEventData) entry;
			eventDetailBuilder.host(applicationServerEventData.getHost());
			eventDetailBuilder.type(applicationServerEventData.getType());
		}
		return eventDetailBuilder.build();
	}

    private EventDetail updateEventDetailWithStartEvent(Map<String, EventDetail> eventDetailsMap, EventData entry) {
        EventDetail eventDetail;
        eventDetail = eventDetailsMap.get(entry.getId());
        logger.info("IN START eventDetail.getEventDuration() "+eventDetail.getEventDuration()+ " entry.getTimestamp() "+entry.getTimestamp());
        eventDetail.setEventDuration(eventDetail.getEventDuration() - entry.getTimestamp());
        return eventDetail;
    }

    private EventDetail updateEventDetailWithFinishedEvent(Map<String, EventDetail> eventDetailsMap, EventData entry) {
        EventDetail eventDetail;
        eventDetail = eventDetailsMap.get(entry.getId());
        logger.info("IN FINISH eventDetail.getEventDuration() "+eventDetail.getEventDuration()+ " entry.getTimestamp() "+entry.getTimestamp());
        eventDetail.setEventDuration(entry.getTimestamp() - eventDetail.getEventDuration());
        return eventDetail;
    }

}
