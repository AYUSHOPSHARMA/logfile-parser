package com.example.logfile.parser.bean;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.EqualsAndHashCode;

@JsonDeserialize(using = JsonDeserializer.None.class)
public class ServerLogData extends EventData{


}
