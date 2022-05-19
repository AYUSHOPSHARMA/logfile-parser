package com.example.logfile.parser.bean;

import com.example.logfile.parser.deserializer.LogFileDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.ToString;

@JsonDeserialize(using = LogFileDeserializer.class)
@ToString
public abstract class EventData {
	
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
