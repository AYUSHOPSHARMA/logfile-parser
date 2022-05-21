package com.example.logfile.parser.bean;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ayush.sharma
 *
 * Application Server logs bean which have the following additional attributes apart from event data attribute:
 * type - type of log
 * host - hostname
 */
@Getter
@Setter
@JsonDeserialize(using = JsonDeserializer.None.class)
@ToString(callSuper = true)
public class ApplicationServerEventData extends EventData {
	/**
	 * @JsonProperty(name), tells Jackson ObjectMapper to map the JSON property name to the annotated Java field's name
	 */
	@JsonProperty("type")
	private String type = "application-server";
	@JsonProperty("host")
	private String host;
	
}
