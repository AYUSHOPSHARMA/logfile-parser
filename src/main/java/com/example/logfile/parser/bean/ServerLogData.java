package com.example.logfile.parser.bean;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;

/**
 * @author ayush.sharma
 *
 * Server logs bean inherit all the attribute from Even Data
 */
@JsonDeserialize(using = JsonDeserializer.None.class)
public class ServerLogData extends EventData{


}
