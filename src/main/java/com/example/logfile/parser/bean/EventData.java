package com.example.logfile.parser.bean;

import com.example.logfile.parser.deserializer.LogFileDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.ToString;

/**
 * @author ayush.sharma
 *
 * Event data logs bean which have the following attributes:
 * id - the unique event identifier
 * state - whether the event was started or finished (can have values "STARTED" or "FINISHED"
 * timestamp - the timestamp of the event in milliseconds
 */
@JsonDeserialize(using = LogFileDeserializer.class)
@ToString
public abstract class EventData {
	
	/**
	 * @JsonProperty(name), tells Jackson ObjectMapper to map the JSON property name to the annotated Java field's name
	 */
	
	@JsonProperty("id")
    private String id;
    @JsonProperty("state")
    private State state;
    @JsonProperty("timestamp")
    private long timestamp;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
    
}
