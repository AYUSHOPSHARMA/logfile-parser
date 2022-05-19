package com.example.logfile.parser.bean;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonDeserialize(using = JsonDeserializer.None.class)
@ToString(callSuper = true)
public class ApplicationServerEventData extends EventData {
	
	@JsonProperty("type")
	private String type = "application-server";
	@JsonProperty("host")
	private String host;
	
}
